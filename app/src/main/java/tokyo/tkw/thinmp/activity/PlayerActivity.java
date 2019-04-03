package tokyo.tkw.thinmp.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.databinding.ActivityPlayerBinding;
import tokyo.tkw.thinmp.music.Track;
import tokyo.tkw.thinmp.player.MusicService;
import tokyo.tkw.thinmp.player.Player;

public class PlayerActivity extends AppCompatActivity {
    public MusicService mMusicService;
    private Player mPlayer;
    /**
     * MusicServiceのListener
     */
    private MusicService.OnMusicServiceListener musicServiceListener = new MusicService.OnMusicServiceListener() {
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
    /**
     * Playerのinterface
     */
    private Player.OnPlayerListener mPlayerListener = new Player.OnPlayerListener() {
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
            mMusicService.prev();
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
        public Integer onRepeat() {
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
    /**
     * ServiceConnection
     */
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicService.MusicBinder binder = (MusicService.MusicBinder) service;
            mMusicService = binder.getService();
            mMusicService.setListener(musicServiceListener);

            setPlayer();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    /**
     * onCreate
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        doBindService();
    }

    /**
     * onDestroy
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mMusicService != null) {
            if (mMusicService.isPlaying()) {
                mPlayer.cancelSeekBarProgressTask();
            }
            unbindService(mConnection);
            mMusicService = null;
        }
    }

    /**
     * onResume
     */
    @Override
    protected void onResume() {
        super.onResume();

        doBindService();
    }

    /**
     * setPlayer
     */
    private void setPlayer() {
        ActivityPlayerBinding mBinding = DataBindingUtil.setContentView(this, R.layout.activity_player);
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

    /**
     * doBindService
     */
    public void doBindService() {
        if (mMusicService != null) return;

        Intent intent = new Intent(this, MusicService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }
}
