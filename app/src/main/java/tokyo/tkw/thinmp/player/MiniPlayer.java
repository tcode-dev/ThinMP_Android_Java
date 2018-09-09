package tokyo.tkw.thinmp.player;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.graphics.Bitmap;
import android.view.View;

import tokyo.tkw.thinmp.databinding.FragmentMiniPlayerBinding;

import tokyo.tkw.thinmp.music.Track;
import tokyo.tkw.thinmp.util.ActivityUtil;
import tokyo.tkw.thinmp.util.ThumbnailController;

/**
 * 画面下のミニプレイヤー
 * UIの変更を行う
 */
public class MiniPlayer {
    public ObservableBoolean isActive = new ObservableBoolean();
    public ObservableBoolean isPlaying = new ObservableBoolean();
    public ObservableBoolean hasNext = new ObservableBoolean();
    public ObservableField<String> trackName = new ObservableField<>();
    public ObservableField<Bitmap> thumbnail = new ObservableField<>();

    private FragmentMiniPlayerBinding mBinding;
    private OnMiniPlayerListener mListener;

    public MiniPlayer(FragmentMiniPlayerBinding binding, MiniPlayer.OnMiniPlayerListener listener) {
        mBinding = binding;
        mListener = listener;
    }

    /**
     * プレイヤーエリアを更新する
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
     * プレイヤーを非表示
     * @param track
     */
    private void setActive(Track track) {
        this.isActive.set(true);
        this.isPlaying.set(true);
        this.trackName.set(track.getTitle());
        this.mBinding.thumbnail.setImageBitmap(new ThumbnailController((Context) ActivityUtil.getContext()).getThumbnail(track.getThumbnailId()));
    }

    /**
     * プレイヤーエリアクリック時のイベント
     * @param view
     */
    public void onClickPlayer(View view) {
        // ページ遷移
        mListener.onClickPlayer();
    }

    /**
     * 再生開始ボタン
     * @param view
     */
    public void onClickPlay(View view) {
        // 開始・停止ボタンのトグル
        this.isPlaying.set(true);
        // 曲の再生
        mListener.onClickPlay();
    }

    /**
     * 一時停止ボタン
     * @param view
     */
    public void onClickPause(View view) {
        // 開始・停止ボタンのトグル
        this.isPlaying.set(false);
        // 曲の一時停止
        mListener.onClickPause();
    }

    /**
     * 次へボタン
     * @param view
     */
    public void onClickNext(View view) {
        // 次の曲
        mListener.onClickNext();
    }

    /**
     * interface
     */
    public interface OnMiniPlayerListener {
        /**
         * ページ遷移
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
    }
}
