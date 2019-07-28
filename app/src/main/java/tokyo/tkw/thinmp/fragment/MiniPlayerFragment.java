package tokyo.tkw.thinmp.fragment;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.annimon.stream.Optional;

import java.util.List;
import java.util.Objects;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.activity.PlayerActivity;
import tokyo.tkw.thinmp.databinding.FragmentMiniPlayerBinding;
import tokyo.tkw.thinmp.listener.ScreenUpdateListener;
import tokyo.tkw.thinmp.player.MiniPlayer;
import tokyo.tkw.thinmp.player.MusicService;
import tokyo.tkw.thinmp.track.Track;

/**
 * MiniPlayerFragment
 */
public class MiniPlayerFragment extends Fragment {
    private MusicService musicService;
    private MiniPlayer miniPlayer;
    private MiniPlayer.OnMiniPlayerListener miniPlayerListener;
    private MusicService.OnMusicServiceListener musicServiceListener;
    private ServiceConnection connection;
    private boolean bound = false;
    private boolean isDisplay = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        miniPlayerListener = createMiniPlayerListener();
        musicServiceListener = createMusicServiceListener();
        connection = createConnection();
        bindMusicService();
        Objects.requireNonNull(getActivity()).startService(new Intent(getActivity(), MusicService.class));
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentMiniPlayerBinding binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_mini_player,
                container,
                false
        );
        miniPlayer = MiniPlayer.createInstance(binding, miniPlayerListener);

        binding.setMiniPlayer(miniPlayer);

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (bound) {
            update();
            musicService.setListener(musicServiceListener);
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        if (bound) {
            musicService.unsetListener();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (bound) {
            unbindMusicService();
            bound = false;
        }
    }

    /**
     * MiniPlayerのListener
     */
    private MiniPlayer.OnMiniPlayerListener createMiniPlayerListener() {
        return new MiniPlayer.OnMiniPlayerListener() {
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
                musicService.play();
            }

            /**
             * 曲の一時停止
             */
            @Override
            public void onClickPause() {
                musicService.pause();
            }

            /**
             * 次の曲
             */
            @Override
            public void onClickNext() {
                if (musicService.isPlaying()) {
                    musicService.playNext();
                } else {
                    musicService.next();
                }
            }

            /**
             * 曲を取得
             */
            @Override
            public Track onGetTrack() {
                return musicService.getTrack();
            }

            @Override
            public boolean onIsPlaying() {
                return musicService.isPlaying();
            }

            @Override
            public int onGetCurrentPosition() {
                return musicService.getCurrentPosition();
            }

            /**
             * seekTo
             */
            @Override
            public void onSeekTo(int msec) {
                musicService.seekTo(msec);
            }
        };
    }

    /**
     * MusicServiceのListener
     */
    private MusicService.OnMusicServiceListener createMusicServiceListener() {
        return new MusicService.OnMusicServiceListener() {
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

            @Override
            public void onForceFinished() {
                hide();
            }

            @Override
            public void onScreenUpdate() {
                screenUpdate();
            }
        };
    }

    /**
     * ServiceConnection
     */
    private ServiceConnection createConnection() {
        return new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                MusicService.MusicBinder binder = (MusicService.MusicBinder) service;
                musicService = binder.getService();
                musicService.setListener(musicServiceListener);
                update();
                bound = true;
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                bound = false;
            }
        };
    }

    private void show(Track track) {
        miniPlayer.show(track);
        updateView(Objects.requireNonNull(this.getView()).getHeight());
        isDisplay = true;
    }

    private void hide() {
        miniPlayer.hide();
        updateView(0);
        isDisplay = false;
    }

    /**
     * ミニプレイヤー表示時に画面下に余白を確保する
     * 余白を設定するviewにはidにmainを設定しておく
     */
    private void updateView(int bottomMargin) {
        ViewGroup rootView = (ViewGroup) ((ViewGroup) Objects.requireNonNull(getActivity()).
                findViewById(android.R.id.content)).
                getChildAt(0);
        ViewGroup mainView = rootView.findViewById(R.id.main);

        ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) mainView.getLayoutParams();
        mlp.setMargins(
                mlp.leftMargin,
                mlp.topMargin,
                mlp.rightMargin,
                bottomMargin
        );

        mainView.setLayoutParams(mlp);
    }

    /**
     * 再生開始
     *
     * @param trackList
     * @param position
     */
    public void start(List<Track> trackList, int position) {
        musicService.initStart(trackList, position);

        Optional.ofNullable(musicService.getTrack()).ifPresentOrElse(track -> {
            update(trackList.get(position));
        }, () -> {
            screenUpdate();
        });
    }

    /**
     * 曲変更
     */
    public void update() {
        update(musicService.getTrack());
    }

    /**
     * 曲変更
     *
     * @param track
     */
    public void update(Track track) {
        if (musicService.isActive()) {
            if (isDisplay) {
                miniPlayer.update(track);
            } else {
                show(track);
            }
        } else {
            hide();
        }
    }

    /**
     * bindMusicService
     */
    private void bindMusicService() {
        FragmentActivity activity = getActivity();
        Intent intent = new Intent(activity, MusicService.class);
        Objects.requireNonNull(activity).bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    /**
     * unbindMusicService
     */
    private void unbindMusicService() {
        Objects.requireNonNull(getActivity()).unbindService(connection);
    }

    private void screenUpdate() {
        Activity activity = ((Activity) getContext());

        if (activity instanceof ScreenUpdateListener) {
            ((ScreenUpdateListener) activity).screenUpdate();
        }
    }
}
