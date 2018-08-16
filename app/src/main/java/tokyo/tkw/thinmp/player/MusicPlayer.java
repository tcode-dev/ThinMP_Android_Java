package tokyo.tkw.thinmp.player;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;

import java.util.ArrayList;

import tokyo.tkw.thinmp.music.Track;

/**
 * Created by tk on 2018/03/19.
 */

public class MusicPlayer extends BasePlayer implements OnCompletionListener {
    private MediaPlayer mMediaPlayer;
    private Activity mContext;
    private ArrayList<Track> mTrackList;
    private int mPosition;
    private int mLastIndex;

    public MusicPlayer(Activity context, ArrayList<Track> trackList, int position) {
        mContext = context;
        mTrackList = trackList;
        mPosition = position;
        mLastIndex = trackList.size() - 1;
    }

    /**
     * MediaPlayerをセット
     */
    private void setPlayer() {
        destroy();

        mMediaPlayer = MediaPlayer.create(mContext, mTrackList.get(mPosition).getUri());
    }

    /**
     * 開始
     */
    public void start() {
        setPlayer();

        mMediaPlayer.setVolume(mVolume, mVolume);
        play();
        mMediaPlayer.setOnCompletionListener(this);
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
        if (! isPlaying()) return;

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
     * 前へ
     */
    public boolean prev() {
        if (! isActive()) return false;
        if (mPosition == 0) return false;

        mPosition--;

        return true;
    }

    /**
     * 次へ
     */
    public boolean next() {
        if (! isActive()) return false;
        if (mPosition == mLastIndex) return false;

        mPosition++;

        return true;
    }

    /**
     * 前曲再生
     */
    public void prevPlay() {
        if (! prev()) return;

        start();
    }

    /**
     * 次曲再生
     */
    public void nextPlay() {
        if (! next()) return;

        start();
    }

    /**
     * MediaPlayerが生成されているか
     */
    public boolean isActive() {
        return mMediaPlayer != null;
    }

    /**
     * 再生中か
     */
    public boolean isPlaying() {
        return isActive() && mMediaPlayer.isPlaying();
    }

    /**
     * 破棄
     */
    public void destroy() {
        if (! isActive()) return;

        stop();

        mMediaPlayer.release();
        mMediaPlayer = null;
    }

    /**
     * 再生完了後の処理
     * @param mp
     */
    @Override
    public void onCompletion(MediaPlayer mp) {
        nextPlay();
    }
}
