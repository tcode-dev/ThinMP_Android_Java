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
import tokyo.tkw.thinmp.music.Track;
import tokyo.tkw.thinmp.player.MusicService;
import tokyo.tkw.thinmp.player.Player;

public class PlayerActivity extends BaseActivity {
    private MusicService mMusicService;
    private Player mPlayer;
    private boolean mBound = false;
    private MusicService.OnMusicServiceListener musicServiceListener;
    private Player.OnPlayerListener mPlayerListener;
    private ServiceConnection mConnection;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initWithPermissionCheck();
    }

    @Override
    protected void init() {
        musicServiceListener = createMusicService();
        mPlayerListener = createPlayerListener();
        mConnection = createServiceConnection();

        bindMusicService();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mBound) {
            setPlayer();
            mMusicService.setListener(musicServiceListener);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mBound) {
            mPlayer.stopDisplayUpdate();
            mMusicService.unsetListener();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mBound) {
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
                mPlayer.start();
            }

            @Override
            public void onFinished() {
                mPlayer.finish();
            }
        };
    }

    private Player.OnPlayerListener createPlayerListener() {
        return new Player.OnPlayerListener() {
            @Override
            public void onPlay() {
                mMusicService.play();
            }

            @Override
            public void onPause() {
                mMusicService.pause();
            }

            @Override
            public void onPrev() {
                if (mMusicService.isPlaying()) {
                    mMusicService.playPrev();
                } else {
                    mMusicService.prev();
                }
            }

            @Override
            public void onNext() {
                if (mMusicService.isPlaying()) {
                    mMusicService.playNext();
                } else {
                    mMusicService.next();
                }
            }

            @Override
            public int onRepeat() {
                return mMusicService.repeat();
            }

            @Override
            public boolean onShuffle() {
                return mMusicService.shuffle();
            }

            @Override
            public int onGetCurrentPosition() {
                return mMusicService.getCurrentPosition();
            }

            @Override
            public void onSeekTo(int msec) {
                mMusicService.seekTo(msec);
            }
        };
    }

    private ServiceConnection createServiceConnection() {
        return new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                MusicService.MusicBinder binder = (MusicService.MusicBinder) service;
                mMusicService = binder.getService();
                mMusicService.setListener(musicServiceListener);
                setPlayer();
                mBound = true;
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                mBound = false;
            }
        };
    }

    private void setPlayer() {
        ActivityPlayerBinding mBinding = DataBindingUtil.setContentView(this,
                R.layout.activity_player);
        mPlayer = new Player(mBinding, mPlayerListener);

        mBinding.setPlayer(mPlayer);
        updatePlayer();

        if (mMusicService.isPlaying()) {
            mPlayer.setSeekBarProgressTask();
        }
    }

    private void updatePlayer() {
        mPlayer.update(mMusicService.getTrack(), mMusicService.getState());
    }

    private void bindMusicService() {
        Intent intent = new Intent(this, MusicService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    private void unbindMusicService() {
        unbindService(mConnection);
    }
}
