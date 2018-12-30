package tokyo.tkw.thinmp.player;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.graphics.Bitmap;
import android.view.View;

import tokyo.tkw.thinmp.databinding.FragmentMiniPlayerBinding;
import tokyo.tkw.thinmp.music.Track;
import tokyo.tkw.thinmp.provider.ThumbnailProvider;

/**
 * 画面下のミニプレイヤー
 * UIの変更を行う
 */
public class MiniPlayer {
    public ObservableBoolean isActive = new ObservableBoolean();
    public ObservableBoolean isPlaying = new ObservableBoolean();
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
        this.isPlaying.set(true);
        this.changeTrack(track);
    }

    /**
     * changeTrack
     *
     * @param track
     */
    private void changeTrack(Track track) {
        this.trackName.set(track.getTitle());
        this.mBinding.thumbnail.setImageBitmap(new ThumbnailProvider().getThumbnail(track.getThumbnailId()));
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
    }
}
