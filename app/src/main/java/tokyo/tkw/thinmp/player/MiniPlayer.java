package tokyo.tkw.thinmp.player;

import android.graphics.Bitmap;
import android.view.MotionEvent;
import android.view.View;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;

import java.util.Timer;
import java.util.TimerTask;

import tokyo.tkw.thinmp.databinding.FragmentMiniPlayerBinding;
import tokyo.tkw.thinmp.track.Track;
import tokyo.tkw.thinmp.util.GlideUtil;

/**
 * 画面下のミニプレイヤー
 * UIの変更を行う
 */
public class MiniPlayer {
    private final int INCREMENT_MS = 3000;
    private final long KEY_PRESS_INTERVAL_MS = 100L;
    private final long KEY_PRESS_DELAY_MS = 0;

    public ObservableBoolean isActive = new ObservableBoolean();
    public ObservableBoolean isPlaying = new ObservableBoolean();
    public ObservableField<String> trackName = new ObservableField<>();
    public ObservableField<Bitmap> albumArt = new ObservableField<>();

    private FragmentMiniPlayerBinding mBinding;
    private OnMiniPlayerListener mListener;
    private int mDurationMSecond;
    private Timer mFastForwardTask;

    public MiniPlayer(FragmentMiniPlayerBinding binding, MiniPlayer.OnMiniPlayerListener listener) {
        mBinding = binding;
        mListener = listener;
    }

    /**
     * プレイヤーエリアを更新する
     *
     * @param track
     */
    public void update(Track track) {
        if (track == null) {
            setInactive();
        } else {
            setActive(track);
        }
    }

    /**
     * プレイヤーを非表示
     */
    private void setInactive() {
        this.isActive.set(false);
    }

    /**
     * プレイヤーを表示
     *
     * @param track
     */
    private void setActive(Track track) {
        this.isActive.set(true);
        this.isPlaying.set(mListener.onIsPlaying());
        this.changeTrack(track);
    }

    /**
     * changeTrack
     *
     * @param track
     */
    private void changeTrack(Track track) {
        this.trackName.set(track.getName());
        GlideUtil.bitmap(track.getAlbumArtId(), this.mBinding.albumArt);
        this.mDurationMSecond = track.getDurationSecond() * 1000;
    }

    /**
     * プレイヤーエリアクリック時のイベント
     *
     * @param view
     */
    public void onClickPlayer(View view) {
        // ページ遷移
        mListener.onClickPlayer();
    }

    /**
     * 再生開始ボタン
     *
     * @param view
     */
    public void onClickPlay(View view) {
        this.isPlaying.set(true);
        mListener.onClickPlay();
    }

    /**
     * 一時停止ボタン
     *
     * @param view
     */
    public void onClickPause(View view) {
        this.isPlaying.set(false);
        mListener.onClickPause();
    }

    /**
     * 次へボタン
     *
     * @param view
     */
    public void onClickNext(View view) {
        mListener.onClickNext();
        changeTrack(mListener.onGetTrack());
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
        } else {
            cancelFastForwardTask();
            mListener.onSeekTo(mDurationMSecond);
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
    public interface OnMiniPlayerListener {
        /**
         * プレイヤー全画面表示（ページ遷移）
         */
        void onClickPlayer();

        /**
         * 曲の再生
         */
        void onClickPlay();

        /**
         * 曲の一時停止
         */
        void onClickPause();

        /**
         * 次の曲
         */
        void onClickNext();

        /**
         * onGetTrack
         *
         * @return
         */
        Track onGetTrack();

        /**
         * onGetState
         *
         * @return
         */
        boolean onIsPlaying();

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
