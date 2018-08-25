package tokyo.tkw.thinmp.player;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;

import java.util.ArrayList;

import tokyo.tkw.thinmp.music.Track;

/**
 * MusicPlayer
 */
public class MusicPlayer implements OnCompletionListener {
    private MediaPlayer mMediaPlayer;
    private Activity mContext;
    private ArrayList<Track> mTrackList;
    private int mPosition;
    private int mLastIndex;

    // 音量
    private final int VOLUME_MIN  = 0;
    private final int VOLUME_MAX  = 100;

    // ミュート
    private final boolean MUTE_ON = true;
    private final boolean MUTE_OFF = false;

    // リピート
    private final boolean REPEAT_ON = true;
    private final boolean REPEAT_OFF = false;
    private final int REPEAT_ONE = 1;
    private final int REPEAT_ALL = 2;

    // シャッフル
    private final boolean SHUFFLE_ON  = true;
    private final boolean SHUFFLE_OFF = false;

    // 音量
    protected int mVolume = 100;
    protected boolean isMute;

    // リピート
    protected boolean isRepeat;
    protected int repeatMode;

    // シャッフル
    private boolean isShuffle;
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

//    public BasePlayer(int mVolume, boolean isMute, boolean isRepeat, int repeatMode, boolean isShuffle) {
//        this.mVolume = mVolume;
//        this.isMute = isMute;
//        this.isRepeat = isRepeat;
//        this.repeatMode = repeatMode;
//        this.isShuffle = isShuffle;
//    }

    public void getConfig(){

    }

    /**
     * 音量変更
     */
    public void changeVolume(int volume) {
        mVolume = volume;
    }

    /**
     * 1曲リピートか
     * @return
     */
    public boolean isRepeatOne() {
        return repeatMode == REPEAT_ONE;
    }

    /**
     * 全曲リピートか
     * @return
     */
    public boolean isRepeatAll() {
        return repeatMode == REPEAT_ALL;
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
