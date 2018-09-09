package tokyo.tkw.thinmp.player;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;

import tokyo.tkw.thinmp.util.ActivityUtil;

/**
 * MusicPlayer
 * @description MediaPlayerを操作する
 */
public class MusicPlayer implements OnCompletionListener {
    private MediaPlayer mMediaPlayer;
    private Uri mUri;

    /**
     * コンストラクタ
     * @param uri
     */
    public MusicPlayer(Uri uri) {
        mUri = uri;
    }

    /**
     * MediaPlayerをセット
     */
    private void setPlayer() {
        destroy();

        mMediaPlayer = MediaPlayer.create((Context) ActivityUtil.getContext(), mUri);
    }

    /**
     * 開始
     */
    public void start() {
        setPlayer();
        play();
        mMediaPlayer.setOnCompletionListener((OnCompletionListener) this);
    }

    /**
     * 再生
     */
    public void play() {
        mMediaPlayer.start();
    }

    /**
     * 一時停止
     */
    public void pause() {
        mMediaPlayer.pause();
    }

    /**
     * 停止
     */
    public void stop() {
        if (! isPlaying()) return;

        mMediaPlayer.stop();
    }

    /**
     * 再生中か
     * @return
     */
    public boolean isPlaying() {
        return mMediaPlayer != null && mMediaPlayer.isPlaying();
    }

    /**
     * 現在の再生位置を取得
     * @return int
     */
    public int getCurrentPosition() {
        return mMediaPlayer.getCurrentPosition();
    }

    /**
     * 破棄
     */
    public void destroy() {
        stop();

        if (mMediaPlayer == null) return;

        mMediaPlayer.release();
        mMediaPlayer = null;
    }

    /**
     * 再生完了後の処理
     * @param mp
     */
    @Override
    public void onCompletion(MediaPlayer mp) {
//        nextPlay();
    }
}
