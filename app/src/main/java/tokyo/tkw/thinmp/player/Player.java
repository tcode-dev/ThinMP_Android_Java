package tokyo.tkw.thinmp.player;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.graphics.Bitmap;
import android.renderscript.RenderScript;
import android.view.View;
import android.widget.SeekBar;

import java.util.Timer;
import java.util.TimerTask;

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
    private Timer mTimer;
    private int mCurrentPositionSecond;
    private OnPlayerListener mListener;
    private Track mTrack;
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
        // 曲名
        this.trackName.set(track.getTitle());
        // アーティスト名
        this.artistName.set(track.getArtistName());
        // 再生位置
        setCurrentPosition(state.getCurrentPosition());
        // 曲の時間
        this.durationTime.set(track.getDurationTime());
        // 曲の秒数
        this.durationSecond.set(track.getDurationSecond());
        // 再生中
        this.isPlaying.set(true);
        // サムネイル
        Bitmap thumbnailBitmap = new ThumbnailProvider().getThumbnail(track.getThumbnailId());
        this.mBinding.thumbnail.setImageBitmap(thumbnailBitmap);
        // 背景画像
        Bitmap backgroundBitmap = thumbnailBitmap.copy(Bitmap.Config.ARGB_8888,true);
        RenderScript rs = RenderScript.create(ActivityUtil.getContext());
        RSBlurProcessor rsBlurProcessor = new RSBlurProcessor(rs);
        Bitmap blurBitMap = rsBlurProcessor.blur(backgroundBitmap, 20f, 3);
        this.mBinding.background.setImageBitmap(blurBitMap);
        // 次へ
        this.hasNext.set(state.hasNext());
        // リピート
        this.repeat.set(state.getRepeat());
        // シャッフル
        this.isShuffle.set(state.isShuffle());
        // お気に入り
        this.isFavorite.set(state.isFavorite());
        // お気に入りアーティスト
        this.isFavoriteArtist.set(state.isFavoriteArtist());
        // seekbar
        this.mBinding.seekBar.setOnSeekBarChangeListener(seekBarChangeListener);
    }

    public void start() {
        this.setDurationTimer();
    }

    public void finish() {
        this.cancelDurationTimer();
    }

    /**
     * 再生ボタンのイベント
     *
     * @param view
     */
    public void onClickPlay(View view) {
        mListener.onPlay();
        this.setDurationTimer();
        this.isPlaying.set(true);
    }

    /**
     * 一時停止ボタンのイベント
     *
     * @param view
     */
    public void onClickPause(View view) {
        this.cancelDurationTimer();
        mListener.onPause();
        this.isPlaying.set(false);
    }

    /**
     * prevボタンのイベント
     *
     * @param view
     */
    public void onClickPrev(View view) {
        cancelDurationTimer();
        mListener.onPrev();
    }

    /**
     * nextボタンのイベント
     *
     * @param view
     */
    public void onClickNext(View view) {
        cancelDurationTimer();
        mListener.onNext();
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
     * 再生中の時間を設定
     */
    public void setCurrentPosition() {
        setCurrentPosition(mListener.onGetCurrentPosition());
    }

    /**
     * 再生中の時間を設定
     */
    public void setCurrentPosition(int currentPosition) {
        int second = TimeUtil.millisecondToSecond(currentPosition);

        if (mCurrentPositionSecond == second) return;

        mCurrentPositionSecond = second;

        this.currentTime.set(TimeUtil.secondToTime(second));
        this.currentSecond.set(mCurrentPositionSecond);
    }

    /**
     * durationTimer
     *
     * @return
     */
    public TimerTask durationTimer() {
        return new TimerTask() {
            public void run() {
                setCurrentPosition();
            }
        };
    }

    /**
     * Timerを登録
     */
    public void setDurationTimer() {
        mTimer = new Timer();
        mTimer.schedule(this.durationTimer(), 0, 1000L);
    }

    /**
     * Timerをキャンセル
     */
    public void cancelDurationTimer() {
        if (mTimer == null) return;

        mTimer.cancel();
        mTimer = null;
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
