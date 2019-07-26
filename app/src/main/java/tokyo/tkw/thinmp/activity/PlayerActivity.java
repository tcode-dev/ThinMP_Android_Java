package tokyo.tkw.thinmp.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.databinding.ActivityPlayerBinding;
import tokyo.tkw.thinmp.player.MusicService;
import tokyo.tkw.thinmp.player.Player;
import tokyo.tkw.thinmp.track.Track;

public class PlayerActivity extends BaseActivity {
    private MusicService musicService;
    private Player player;
    private boolean bound = false;
    private MusicService.OnMusicServiceListener musicServiceListener;
    private Player.OnPlayerListener playerListener;
    private ServiceConnection connection;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initWithPermissionCheck();
    }

    @Override
    protected void init() {
        musicServiceListener = createMusicService();
        playerListener = createPlayerListener();
        connection = createServiceConnection();

        bindMusicService();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (bound) {
            setPlayer();
            musicService.setListener(musicServiceListener);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (bound) {
            player.stopDisplayUpdate();
            musicService.unsetListener();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (bound) {
            unbindMusicService();
        }
    }

    private MusicService.OnMusicServiceListener createMusicService() {
        return new MusicService.OnMusicServiceListener() {
            @Override
            public void onChangeTrack(Track track) {
                updatePlayer();
            }

            @Override
            public void onStarted() {
                player.start();
            }

            @Override
            public void onFinished() {
                player.finish();
            }
        };
    }

    private Player.OnPlayerListener createPlayerListener() {
        return new Player.OnPlayerListener() {
            @Override
            public void onPlay() {
                musicService.play();
            }

            @Override
            public void onPause() {
                musicService.pause();
            }

            @Override
            public void onPrev() {
                if (musicService.isPlaying()) {
                    musicService.playPrev();
                } else {
                    musicService.prev();
                }
            }

            @Override
            public void onNext() {
                if (musicService.isPlaying()) {
                    musicService.playNext();
                } else {
                    musicService.next();
                }
            }

            @Override
            public int onRepeat() {
                return musicService.repeat();
            }

            @Override
            public boolean onShuffle() {
                return musicService.shuffle();
            }

            @Override
            public int onGetCurrentPosition() {
                return musicService.getCurrentPosition();
            }

            @Override
            public void onSeekTo(int msec) {
                musicService.seekTo(msec);
            }
        };
    }

    private ServiceConnection createServiceConnection() {
        return new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                MusicService.MusicBinder binder = (MusicService.MusicBinder) service;
                musicService = binder.getService();
                musicService.setListener(musicServiceListener);
                setPlayer();
                bound = true;
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                bound = false;
            }
        };
    }

    private void setPlayer() {
        ActivityPlayerBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_player);
        player = Player.createInstance(binding, playerListener);

        binding.setPlayer(player);
        updatePlayer();

        if (musicService.isPlaying()) {
            player.setSeekBarProgressTask();
        }
    }

    private void updatePlayer() {
        player.update(musicService.getTrack(), musicService.getState());
    }

    private void bindMusicService() {
        Intent intent = new Intent(this, MusicService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    private void unbindMusicService() {
        unbindService(connection);
    }
}
