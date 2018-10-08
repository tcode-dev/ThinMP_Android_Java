package tokyo.tkw.thinmp.player;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.SeekBar;

import java.util.Timer;
import java.util.TimerTask;

import tokyo.tkw.thinmp.databinding.ActivityPlayerBinding;
import tokyo.tkw.thinmp.music.Track;
import tokyo.tkw.thinmp.provider.ThumbnailProvider;
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

    private ActivityPlayerBinding mBinding;
    private Timer mTimer;
    private int mCurrentPositionSecond;
    private OnPlayerListener mListener;

    public Player(ActivityPlayerBinding binding, OnPlayerListener listener) {
        mBinding = binding;
        mListener = listener;
    }

    public void update(Track track) {
        // 曲名
        this.trackName.set(track.getTitle());
        // アーティスト名
        this.artistName.set(track.getArtistName());
        // 現在の曲の時間
        this.currentTime.set("0:00");
        // 曲の時間
        this.durationTime.set(track.getDurationTime());
        // 現在の曲の秒
        this.currentSecond.set(0);
        // 曲の秒数
        this.durationSecond.set(track.getDurationSecond());
        // 再生中
        this.isPlaying.set(true);
        // サムネイル
        this.mBinding.thumbnail.setImageBitmap(new ThumbnailProvider().getThumbnail(track.getThumbnailId()));
        // seekbar
        this.mBinding.seekBar.setOnSeekBarChangeListener(seekBarChangeListener);
    }

    /**
     * 再生ボタンのイベント
     * @param view
     */
    public void onClickPlay(View view) {
        mListener.onPlay();
        this.isPlaying.set(true);
        this.setDurationTimer();
    }

    /**
     * 一時停止ボタンのイベント
     * @param view
     */
    public void onClickPause(View view) {
        mListener.onPause();
        this.isPlaying.set(false);
        this.cancelDurationTimer();
    }

    /**
     * prevボタンのイベント
     * @param view
     */
    public void onClickPrev(View view) {
        cancelDurationTimer();
        mListener.onPrev();
    }

    /**
     * nextボタンのイベント
     * @param view
     */
    public void onClickNext(View view) {
        cancelDurationTimer();
        mListener.onNext();
    }

    /**
     * 再生中の時間を設定
     */
    public void setDuration() {
        int second = TimeUtil.millisecondToSecond(mListener.onGetCurrentPosition());

        if (mCurrentPositionSecond == second) return;

        mCurrentPositionSecond = second;

        this.currentTime.set(TimeUtil.secondToTime(second));
        this.currentSecond.set(mCurrentPositionSecond);
    }

    /**
     * durationTimer
     * @return
     */
    public TimerTask durationTimer() {
        return new TimerTask() {
            public void run() {
                setDuration();
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

    private SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (! fromUser) return;

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
         * 再生曲の現在時間を取得
         */
        int onGetCurrentPosition();

        /**
         * seekTo
         */
        void onSeekTo(int msec);
    }
}
