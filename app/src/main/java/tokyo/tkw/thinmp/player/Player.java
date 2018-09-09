package tokyo.tkw.thinmp.player;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.graphics.Bitmap;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

import tokyo.tkw.thinmp.databinding.ActivityPlayerBinding;
import tokyo.tkw.thinmp.music.Track;
import tokyo.tkw.thinmp.util.ActivityUtil;
import tokyo.tkw.thinmp.util.ThumbnailController;
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

    public Player(ActivityPlayerBinding binding) {
        mBinding = binding;
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
        this.mBinding.thumbnail.setImageBitmap(new ThumbnailController((Context) ActivityUtil.getContext()).getThumbnail(track.getThumbnailId()));
    }

    /**
     * 再生ボタンのイベント
     * @param view
     */
    public void onClickPlay(View view) {
        // 開始・停止ボタンのトグル
        this.isPlaying.set(true);
        // 曲を再生
        MusicManager.play();
        this.setDurationTimer();
    }

    /**
     * 一時停止ボタンのイベント
     * @param view
     */
    public void onClickPause(View view) {
        // 開始・停止ボタンのトグル
        this.isPlaying.set(false);
        // 曲を一時停止
        MusicManager.pause();
        // 再生時間の更新を停止
        this.cancelDurationTimer();
    }

    /**
     * prevボタンのイベント
     * @param view
     */
    public void onClickPrev(View view) {
        // 前の曲を再生
        MusicManager.prev();
        cancelDurationTimer();
    }

    /**
     * nextボタンのイベント
     * @param view
     */
    public void onClickNext(View view) {
        // 次の曲を再生
        MusicManager.next();
        cancelDurationTimer();
    }

    /**
     * 再生中の時間を設定
     */
    public void setDuration() {
        int second = TimeUtil.millisecondToSecond(MusicManager.getCurrentPosition());

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
}
