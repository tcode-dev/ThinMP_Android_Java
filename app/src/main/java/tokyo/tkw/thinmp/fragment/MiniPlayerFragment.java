package tokyo.tkw.thinmp.fragment;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.activity.PlayerActivity;
import tokyo.tkw.thinmp.databinding.FragmentMiniPlayerBinding;
import tokyo.tkw.thinmp.music.Track;
import tokyo.tkw.thinmp.player.MiniPlayer;
import tokyo.tkw.thinmp.player.MusicService;

/**
 * MiniPlayerFragment
 */
public class MiniPlayerFragment extends Fragment {
    private MiniPlayer mMiniPlayer;
    private FragmentMiniPlayerBinding mBinding;
    public MusicService mMusicService;

    /**
     * ServiceConnection
     */
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicService.MusicBinder binder = (MusicService.MusicBinder) service;
            mMusicService = binder.getService();
            mMusicService.setListener(musicServiceListener);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    /**
     * MiniPlayerのListener
     */
    private MiniPlayer.OnMiniPlayerListener mMiniPlayerListener = new MiniPlayer.OnMiniPlayerListener() {
        /**
         * 再生画面へ遷移
         */
        @Override
        public void onClickPlayer() {
            Intent intent = new Intent(getContext(), PlayerActivity.class);
            startActivity(intent);
        }

        /**
         * 曲の再生
         */
        @Override
        public void onClickPlay() {
            mMusicService.play();
        }

        /**
         * 曲の一時停止
         */
        @Override
        public void onClickPause() {
            mMusicService.pause();
        }

        /**
         * 次の曲
         */
        @Override
        public void onClickNext() {
            mMusicService.next();
        }

        /**
         * 曲を取得
         */
        @Override
        public Track onGetTrack() {
            return mMusicService.getTrack();
        }
    };

    /**
     * MusicServiceのListener
     */
    private MusicService.OnMusicServiceListener musicServiceListener = new MusicService.OnMusicServiceListener() {
        @Override
        public void onChangeTrack(Track track) {
            update(track);
        }

        @Override
        public void onStarted() {

        }

        @Override
        public void onFinished() {

        }
    };

    /**
     * doBindService
     */
    public void doBindService() {
        FragmentActivity activity = getActivity();
        Intent intent = new Intent(activity, MusicService.class);
        activity.bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    /**
     * onCreate
     * @param savedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mMusicService == null) {
            doBindService();
        }

        getActivity().startService(new Intent(getActivity(), MusicService.class));
    }

    /**
     * onCreateView
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_mini_player, container, false);
        mMiniPlayer = new MiniPlayer(mBinding, mMiniPlayerListener);

        mBinding.setMiniPlayer(mMiniPlayer);

        return mBinding.getRoot();
    }

    /**
     * onDestroy
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mMusicService != null) {
            getActivity().unbindService(mConnection);
            mConnection = null;
        }
    }

    /**
     * 再生開始
     * @param trackList
     * @param position
     */
    public void start(ArrayList<Track> trackList, int position) {
        mMusicService.setPlayingList(trackList, position);
        mMusicService.start();
        mMiniPlayer.update(mMusicService.getTrack());
    }

    /**
     * 曲変更
     * @param track
     */
    public void update(Track track) {
        mMiniPlayer.update(track);
    }
}
