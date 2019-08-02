package tokyo.tkw.thinmp.player;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.SeekBar;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.fragment.app.FragmentActivity;

import com.annimon.stream.Optional;

import java.util.Timer;
import java.util.TimerTask;

import tokyo.tkw.thinmp.databinding.ActivityPlayerBinding;
import tokyo.tkw.thinmp.favorite.FavoriteArtistRegister;
import tokyo.tkw.thinmp.fragment.PlaylistDialogFragment;
import tokyo.tkw.thinmp.music.Music;
import tokyo.tkw.thinmp.registration.add.FavoriteSongAdder;
import tokyo.tkw.thinmp.registration.delete.FavoriteSongDeleter;
import tokyo.tkw.thinmp.track.Track;
import tokyo.tkw.thinmp.util.GlideUtil;
import tokyo.tkw.thinmp.util.TimeUtil;

/**
 * 再生中の画面
 */
public class Player {
    private static final int DECREMENT_MS = 3000;
    private static final int INCREMENT_MS = 3000;
    private static final long KEY_PRESS_INTERVAL_MS = 100L;
    private static final long KEY_PRESS_DELAY_MS = 0;

    public ObservableField<String> trackName = new ObservableField<>();
    public ObservableField<String> artistName = new ObservableField<>();
    public ObservableField<String> currentTime = new ObservableField<>();
    public ObservableField<String> durationTime = new ObservableField<>();
    public ObservableField<Integer> durationSecond = new ObservableField<>();
    public ObservableField<Integer> currentSecond = new ObservableField<>();
    public ObservableBoolean isPlaying = new ObservableBoolean();
    public ObservableField<Bitmap> albumArt = new ObservableField<>();
    public ObservableField<Bitmap> background = new ObservableField<>();
    public ObservableBoolean isRepeatOff = new ObservableBoolean();
    public ObservableBoolean isRepeatOne = new ObservableBoolean();
    public ObservableBoolean isRepeatAll = new ObservableBoolean();
    public ObservableBoolean hasNext = new ObservableBoolean();
    public ObservableBoolean isShuffle = new ObservableBoolean();
    public ObservableBoolean isFavorite = new ObservableBoolean();
    public ObservableBoolean isFavoriteArtist = new ObservableBoolean();

    private ActivityPlayerBinding binding;
    private Timer seekBarProgressTask;
    private Timer fastBackwardTask;
    private Timer fastForwardTask;
    private OnPlayerListener listener;
    private Track track;
    private int durationMSecond;

    private Player(ActivityPlayerBinding binding, OnPlayerListener listener) {
        this.binding = binding;
        this.listener = listener;
    }

    public static Player createInstance(ActivityPlayerBinding binding, OnPlayerListener listener) {
        return new Player(binding, listener);
    }

    public void update(Track track, MusicState state) {
        // track
        this.track = track;
        // 曲名
        this.trackName.set(track.getName());
        // アーティスト名
        this.artistName.set(track.getArtistName());
        // seekBarProgress
        seekBarProgress(state.getCurrentPosition());
        // 曲の時間
        this.durationTime.set(track.getDurationTime());
        // 曲の秒数
        this.durationSecond.set(track.getDurationSecond());
        // 曲のミリ秒
        this.durationMSecond = track.getDurationSecond() * 1000;
        // 再生中
        this.isPlaying.set(state.isPlaying());
        // 背景画像
        Optional.ofNullable(track.getAlbumArtId()).ifPresent(albumArtId -> {
            GlideUtil.bitmap(track.getAlbumArtId(), this.binding.background);
        });
        // アルバムアート
        GlideUtil.bitmap(track.getAlbumArtId(), this.binding.albumArt);
        // リピート
        setRepeat(state.getRepeat());
        // シャッフル
        this.isShuffle.set(state.isShuffle());
        // お気に入り
        this.isFavorite.set(state.isFavoriteSong());
        // お気に入りアーティスト
        this.isFavoriteArtist.set(state.isFavoriteArtist());
        // seekbar
        this.binding.seekBar.setOnSeekBarChangeListener(createSeekBarChangeListener());
    }

    public void start() {
        this.setSeekBarProgressTask();
    }

    public void finish() {
        this.cancelSeekBarProgressTask();
    }

    /**
     * onClickPlay
     *
     * @param view
     */
    public void onClickPlay(View view) {
        listener.onPlay();
        this.setSeekBarProgressTask();
        this.isPlaying.set(true);
    }

    /**
     * onClickPause
     *
     * @param view
     */
    public void onClickPause(View view) {
        cancelSeekBarProgressTask();
        listener.onPause();
        this.isPlaying.set(false);
    }

    /**
     * onClickPrev
     *
     * @param view
     */
    public void onClickPrev(View view) {
        cancelSeekBarProgressTask();
        listener.onPrev();
    }

    /**
     * onLongClickPrev
     *
     * @param view
     */
    public boolean onLongClickPrev(View view) {
        setFastBackwardTask();
        return true;
    }

    /**
     * onTouchPrev
     *
     * @param view
     * @param event
     * @return
     */
    public boolean onTouchPrev(View view, MotionEvent event) {
        if (fastBackwardTask != null && event.getAction() == MotionEvent.ACTION_UP) {
            cancelFastBackwardTask();
        }

        return false;
    }

    /**
     * onClickNext
     *
     * @param view
     */
    public void onClickNext(View view) {
        cancelSeekBarProgressTask();
        listener.onNext();
    }

    /**
     * onLongClickNext
     *
     * @param view
     */
    public boolean onLongClickNext(View view) {
        setFastForward();
        return true;
    }

    /**
     * onTouchNext
     *
     * @param view
     * @param event
     * @return
     */
    public boolean onTouchNext(View view, MotionEvent event) {
        if (fastForwardTask != null && event.getAction() == MotionEvent.ACTION_UP) {
            cancelFastForwardTask();
        }

        return false;
    }

    /**
     * リピート
     *
     * @param view
     */
    public void onClickRepeat(View view) {
        setRepeat(listener.onRepeat());
    }

    /**
     * シャッフル
     *
     * @param view
     */
    public void onClickShuffle(View view) {
        boolean isShuffle = listener.onShuffle();
        this.isShuffle.set(isShuffle);
    }

    /**
     * お気に入りの曲追加
     *
     * @param view
     */
    public void onClickAddFavoriteSong(View view) {
        FavoriteSongAdder favoriteSongAdder = FavoriteSongAdder.createInstance();
        favoriteSongAdder.add(track.getId());

        isFavorite.set(true);
    }

    /**
     * お気に入りの曲削除
     *
     * @param view
     */
    public void onClickDeleteFavoriteSong(View view) {
        FavoriteSongDeleter favoriteSongDeleter = FavoriteSongDeleter.createInstance();
        favoriteSongDeleter.delete(track.getId());

        isFavorite.set(false);
    }

    /**
     * お気に入りアーティスト追加
     *
     * @param view
     */
    public void onClickAddFavoriteArtist(View view) {
        FavoriteArtistRegister register = FavoriteArtistRegister.createInstance();
        register.add(track.getArtistId());
        isFavoriteArtist.set(true);
    }

    /**
     * お気に入りアーティスト削除
     *
     * @param view
     */
    public void onClickDeleteFavoriteArtist(View view) {
        FavoriteArtistRegister register = FavoriteArtistRegister.createInstance();
        register.delete(track.getArtistId());
        isFavoriteArtist.set(false);
    }

    /**
     * OnClickAddPlaylist
     *
     * @param view
     */
    public void onClickAddPlaylist(View view) {
        Bundle bundle = new Bundle();
        bundle.putString(Music.ID, track.getId());
        bundle.putInt(Music.TYPE, Music.TYPE_TRACK);

        FragmentActivity activity = (FragmentActivity) view.getContext();
        PlaylistDialogFragment playlistDialogFragment = new PlaylistDialogFragment();
        playlistDialogFragment.setArguments(bundle);
        playlistDialogFragment.show(activity.getSupportFragmentManager(), PlaylistDialogFragment.class.getName());
    }

    /**
     * seekBarProgress
     */
    private void seekBarProgress() {
        seekBarProgress(listener.onGetCurrentPosition());
    }

    /**
     * seekBarProgress
     */
    private void seekBarProgress(int currentPosition) {
        int second = TimeUtil.millisecondToSecond(currentPosition);

        this.currentTime.set(TimeUtil.secondToTime(second));
        this.currentSecond.set(second);
    }

    /**
     * seekBarProgressTask
     *
     * @return TimerTask
     */
    private TimerTask seekBarProgressTask() {
        return new TimerTask() {
            public void run() {
                seekBarProgress();
            }
        };
    }

    /**
     * setSeekBarProgressTask
     */
    public void setSeekBarProgressTask() {
        seekBarProgressTask = new Timer();
        seekBarProgressTask.schedule(seekBarProgressTask(), 0, 1000L);
    }

    /**
     * cancelSeekBarProgressTask
     */
    private void cancelSeekBarProgressTask() {
        if (seekBarProgressTask == null) return;

        seekBarProgressTask.cancel();
        seekBarProgressTask = null;
    }

    /**
     * stopDisplayUpdate
     * 画面更新の必要がないスリープに入る時などに使用する
     */
    public void stopDisplayUpdate() {
        cancelSeekBarProgressTask();
    }

    /**
     * setFastBackwardTask
     */
    private void setFastBackwardTask() {
        fastBackwardTask = new Timer();
        fastBackwardTask.schedule(fastBackwardTask(), KEY_PRESS_DELAY_MS, KEY_PRESS_INTERVAL_MS);
    }

    /**
     * fastBackwardTask
     */
    private TimerTask fastBackwardTask() {
        return new TimerTask() {
            public void run() {
                fastBackward();
            }
        };
    }

    /**
     * fastBackward
     */
    private void fastBackward() {
        int nextMsec = listener.onGetCurrentPosition() - DECREMENT_MS;

        if (nextMsec >= 0) {
            listener.onSeekTo(nextMsec);
            seekBarProgress(nextMsec);
        } else {
            cancelFastBackwardTask();
            listener.onSeekTo(0);
            seekBarProgress(0);
        }
    }

    /**
     * cancelFastBackwardTask
     */
    private void cancelFastBackwardTask() {
        if (fastBackwardTask == null) return;

        fastBackwardTask.cancel();
        fastBackwardTask = null;
    }

    /**
     * setFastForward
     */
    private void setFastForward() {
        fastForwardTask = new Timer();
        fastForwardTask.schedule(fastForwardTask(), KEY_PRESS_DELAY_MS, KEY_PRESS_INTERVAL_MS);
    }

    /**
     * fastForwardTask
     */
    private TimerTask fastForwardTask() {
        return new TimerTask() {
            public void run() {
                fastForward();
            }
        };
    }

    /**
     * fastForward
     */
    private void fastForward() {
        int nextMsec = listener.onGetCurrentPosition() + INCREMENT_MS;

        if (nextMsec <= durationMSecond) {
            listener.onSeekTo(nextMsec);
            seekBarProgress(nextMsec);
        } else {
            cancelFastForwardTask();
            listener.onSeekTo(durationMSecond);
            seekBarProgress(durationMSecond);
        }
    }

    /**
     * cancelFastForwardTask
     */
    private void cancelFastForwardTask() {
        if (fastForwardTask == null) return;

        fastForwardTask.cancel();
        fastForwardTask = null;
    }

    /**
     * setRepeat
     *
     * @param repeat
     */
    private void setRepeat(int repeat) {
        this.isRepeatOff.set(repeat == MusicService.REPEAT_OFF);
        this.isRepeatOne.set(repeat == MusicService.REPEAT_ONE);
        this.isRepeatAll.set(repeat == MusicService.REPEAT_ALL);
    }

    private SeekBar.OnSeekBarChangeListener createSeekBarChangeListener() {
        return new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (!fromUser) return;

                int msec = progress * 1000;

                seekBarProgress(msec);
                listener.onSeekTo(msec);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        };
    }

    /**
     * interface
     */
    public interface OnPlayerListener {
        /**
         * 曲の再生
         */
        void onPlay();

        /**
         * 曲の一時停止
         */
        void onPause();

        /**
         * 前の曲
         */
        void onPrev();

        /**
         * 次の曲
         */
        void onNext();

        /**
         * リピート
         */
        int onRepeat();

        /**
         * シャッフル
         */
        boolean onShuffle();

        /**
         * 再生曲の現在時間を取得
         */
        int onGetCurrentPosition();

        /**
         * seekTo
         */
        void onSeekTo(int msec);
    }
}
