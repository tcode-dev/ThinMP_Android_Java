package tokyo.tkw.thinmp.player;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.graphics.Bitmap;
import android.renderscript.RenderScript;
import android.view.MotionEvent;
import android.view.View;
import android.widget.SeekBar;

import java.util.Timer;
import java.util.TimerTask;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.databinding.ActivityPlayerBinding;
import tokyo.tkw.thinmp.favorite.FavoriteArtistRegister;
import tokyo.tkw.thinmp.favorite.FavoriteSongRegister;
import tokyo.tkw.thinmp.music.Track;
import tokyo.tkw.thinmp.plugin.RSBlurProcessor;
import tokyo.tkw.thinmp.provider.ThumbnailProvider;
import tokyo.tkw.thinmp.util.ActivityUtil;
import tokyo.tkw.thinmp.util.TimeUtil;

/**
 * 再生中の画面
 */
public class Player {
    private final int DECREMENT_MS = 3000;
    private final int INCREMENT_MS = 3000;
    private final long KEY_PRESS_INTERVAL_MS = 100L;
    private final long KEY_PRESS_DELAY_MS = 0;
    public ObservableField<String> trackName = new ObservableField<>();
    public ObservableField<String> artistName = new ObservableField<>();
    public ObservableField<String> currentTime = new ObservableField<>();
    public ObservableField<String> durationTime = new ObservableField<>();
    public ObservableField<Integer> durationSecond = new ObservableField<>();
    public ObservableField<Integer> currentSecond = new ObservableField<>();
    public ObservableBoolean isPlaying = new ObservableBoolean();
    public ObservableField<Bitmap> thumbnail = new ObservableField<>();
    public ObservableField<Bitmap> background = new ObservableField<>();
    public ObservableField<Integer> repeat = new ObservableField<>();
    public ObservableBoolean hasNext = new ObservableBoolean();
    public ObservableBoolean isShuffle = new ObservableBoolean();
    public ObservableBoolean isFavorite = new ObservableBoolean();
    public ObservableBoolean isFavoriteArtist = new ObservableBoolean();

    private ActivityPlayerBinding mBinding;
    private Timer mSeekBarProgressTask;
    private Timer mFastBackwardTask;
    private Timer mFastForwardTask;
    private int mCurrentPositionSecond;
    private OnPlayerListener mListener;
    private Track mTrack;
    private int mDurationMSecond;
    private MusicState mMusicState;
    private SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (!fromUser) return;

            int msec = progress * 1000;

            mListener.onSeekTo(msec);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    public Player(ActivityPlayerBinding binding, OnPlayerListener listener) {
        mBinding = binding;
        mListener = listener;
    }

    public void update(Track track, MusicState state) {
        // track
        this.mTrack = track;
        // 状態
        this.mMusicState = state;
        // 曲名
        this.trackName.set(track.getTitle());
        // アーティスト名
        this.artistName.set(track.getArtistName());
        // 再生位置の秒数（0のときも反映されるように初期値を設定）
        this.mCurrentPositionSecond = -1;//
        // seekBarProgress
        seekBarProgress(state.getCurrentPosition());
        // 曲の時間
        this.durationTime.set(track.getDurationTime());
        // 曲の秒数
        this.durationSecond.set(track.getDurationSecond());
        // 曲のミリ秒
        this.mDurationMSecond = track.getDurationSecond() * 1000;
        // 再生中
        this.isPlaying.set(state.isPlaying());
        // サムネイル
        Bitmap thumbnailBitmap = new ThumbnailProvider(R.drawable.playing).getThumbnail(track.getThumbnailId());
        this.mBinding.thumbnail.setImageBitmap(thumbnailBitmap);
        // 背景画像
        Bitmap backgroundBitmap = thumbnailBitmap.copy(Bitmap.Config.ARGB_8888, true);
        RenderScript rs = RenderScript.create(ActivityUtil.getContext());
        RSBlurProcessor rsBlurProcessor = new RSBlurProcessor(rs);
        Bitmap blurBitMap = rsBlurProcessor.blur(backgroundBitmap, 20f, 3);
        this.mBinding.background.setImageBitmap(blurBitMap);
        // リピート
        this.repeat.set(state.getRepeat());
        // シャッフル
        this.isShuffle.set(state.isShuffle());
        // お気に入り
        this.isFavorite.set(state.isFavoriteSong());
        // お気に入りアーティスト
        this.isFavoriteArtist.set(state.isFavoriteArtist());
        // seekbar
        this.mBinding.seekBar.setOnSeekBarChangeListener(seekBarChangeListener);
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
        mListener.onPlay();
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
        mListener.onPause();
        this.isPlaying.set(false);
    }

    /**
     * onClickPrev
     *
     * @param view
     */
    public void onClickPrev(View view) {
        cancelSeekBarProgressTask();
        mListener.onPrev();
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
        if (mFastBackwardTask != null && event.getAction() == MotionEvent.ACTION_UP) {
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
        if (!mMusicState.hasNext()) {
            return;
        }

        cancelSeekBarProgressTask();
        mListener.onNext();
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
        if (mFastForwardTask != null && event.getAction() == MotionEvent.ACTION_UP) {
            cancelFastForwardTask();
        }

        return false;
    }

    /**
     * onClickRepeat
     *
     * @param view
     */
    public void onClickRepeat(View view) {
        Integer repeat = mListener.onRepeat();
        this.repeat.set(repeat);
    }

    /**
     * onClickShuffle
     *
     * @param view
     */
    public void onClickShuffle(View view) {
        boolean isShuffle = mListener.onShuffle();
        this.isShuffle.set(isShuffle);
    }

    /**
     * OnClickFavorite
     *
     * @param view
     */
    public void OnClickFavorite(View view) {
        boolean favorite = FavoriteSongRegister.set(mTrack.getId());
        isFavorite.set(favorite);
    }

    /**
     * OnClickFavoriteArtist
     *
     * @param view
     */
    public void OnClickFavoriteArtist(View view) {
        boolean favorite = FavoriteArtistRegister.set(mTrack.getArtistId());
        isFavoriteArtist.set(favorite);
    }

    /**
     * seekBarProgress
     */
    public void seekBarProgress() {
        seekBarProgress(mListener.onGetCurrentPosition());
    }

    /**
     * seekBarProgress
     */
    public void seekBarProgress(int currentPosition) {
        int second = TimeUtil.millisecondToSecond(currentPosition);

        if (mCurrentPositionSecond == second) return;

        mCurrentPositionSecond = second;

        this.currentTime.set(TimeUtil.secondToTime(second));
        this.currentSecond.set(mCurrentPositionSecond);
    }

    /**
     * seekBarProgressTask
     *
     * @return TimerTask
     */
    public TimerTask seekBarProgressTask() {
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
        mSeekBarProgressTask = new Timer();
        mSeekBarProgressTask.schedule(seekBarProgressTask(), 0, 1000L);
    }

    /**
     * cancelSeekBarProgressTask
     */
    private void cancelSeekBarProgressTask() {
        if (mSeekBarProgressTask == null) return;

        mSeekBarProgressTask.cancel();
        mSeekBarProgressTask = null;
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
    public void setFastBackwardTask() {
        mFastBackwardTask = new Timer();
        mFastBackwardTask.schedule(fastBackwardTask(), KEY_PRESS_DELAY_MS, KEY_PRESS_INTERVAL_MS);
    }

    /**
     * fastBackwardTask
     */
    public TimerTask fastBackwardTask() {
        return new TimerTask() {
            public void run() {
                fastBackward();
            }
        };
    }

    /**
     * fastBackward
     */
    public void fastBackward() {
        int nextMsec = mListener.onGetCurrentPosition() - DECREMENT_MS;

        if (nextMsec >= 0) {
            mListener.onSeekTo(nextMsec);
            seekBarProgress(nextMsec);
        } else {
            cancelFastBackwardTask();
            mListener.onSeekTo(0);
            seekBarProgress(0);
        }
    }

    /**
     * cancelFastBackwardTask
     */
    public void cancelFastBackwardTask() {
        if (mFastBackwardTask == null) return;

        mFastBackwardTask.cancel();
        mFastBackwardTask = null;
    }

    /**
     * setFastForward
     */
    public void setFastForward() {
        mFastForwardTask = new Timer();
        mFastForwardTask.schedule(fastForwardTask(), KEY_PRESS_DELAY_MS, KEY_PRESS_INTERVAL_MS);
    }

    /**
     * fastForwardTask
     */
    public TimerTask fastForwardTask() {
        return new TimerTask() {
            public void run() {
                fastForward();
            }
        };
    }

    /**
     * fastForward
     */
    public void fastForward() {
        int nextMsec = mListener.onGetCurrentPosition() + INCREMENT_MS;

        if (nextMsec <= mDurationMSecond) {
            mListener.onSeekTo(nextMsec);
            seekBarProgress(nextMsec);
        } else {
            cancelFastForwardTask();
            mListener.onSeekTo(mDurationMSecond);
            seekBarProgress(mDurationMSecond);
        }
    }

    /**
     * cancelFastForwardTask
     */
    public void cancelFastForwardTask() {
        if (mFastForwardTask == null) return;

        mFastForwardTask.cancel();
        mFastForwardTask = null;
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
        Integer onRepeat();

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
