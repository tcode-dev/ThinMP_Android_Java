package tokyo.tkw.thinmp.player;

import java.util.ArrayList;
import java.util.Collections;

import tokyo.tkw.thinmp.music.Track;

public class MusicManager {
    // リピート
    private static final int REPEAT_OFF = 0;
    private static final int REPEAT_ONE = 1;
    private static final int REPEAT_ALL = 2;

    // プレイヤーが表示されているか
    private static boolean isActive = false;
    // 再生中
    private static boolean isPlaying = false;
    // 前の曲が存在するか
    private static boolean hasPrev = false;
    // 次の曲が存在するか
    private static boolean hasNext = false;
    // リピート
    private static int repeat = REPEAT_OFF;
    // シャッフル
    private static boolean shuffle = false;
    // MusicPlayer
    private static MusicPlayer mMusicPlayer;
    // 再生曲リスト
    private static ArrayList<Track> mOriginalPlayList;
    // 再生曲リスト
    private static ArrayList<Track> mPlayList;
    // 現在位置
    private static int mPosition;
    // 最後の曲のインデックス
    private static int mLastIndex;

    public static void set(ArrayList<Track> trackList, int position) {
        destroy();

        mOriginalPlayList = trackList;
        mPlayList = trackList;
        mPosition = position;
        mLastIndex = trackList.size() - 1;
    }

    /**
     * 曲を変更する
     */
    public static void changeTrack() {
        destroy();
        mMusicPlayer = new MusicPlayer(getTrack().getUri());
    }

    /**
     * 曲をセットして再生を開始する
     */
    public static void start() {
        changeTrack();
        mMusicPlayer.start();
    }

    /**
     * 再生
     */
    public static void play() {
        mMusicPlayer.play();
    }

    /**
     * 一時停止
     */
    public static void pause() {
        mMusicPlayer.pause();
    }

    /**
     * 前の曲へ
     */
    public static void prev() {
        decrementPosition();

        if (isPlaying()) {
            start();
        } else {
            changeTrack();
        }
    }

    /**
     * 次の曲へ
     */
    public static void next() {
        incrementPosition();

        if (isPlaying()) {
            start();
        } else {
            changeTrack();
        }
    }

    /**
     * 次の曲を再生する
     * 曲の再生が終了したときに呼ばれる
     */
    public static void playNext() {
        if (! hasPlayNext()) return;

        next();
    }

    /**
     * 次の曲が存在するか
     * @return
     */
    public static boolean hasPlayNext() {
        switch (repeat) {
            case REPEAT_OFF :
                return ! isLast();
            case REPEAT_ONE :
                return ! isLast();
            case REPEAT_ALL :
                return true;
            default:
                return ! isLast();
        }
    }

    /**
     * 最初の曲か
     * @return
     */
    private static boolean isFirst() {
        return mPosition == 0;
    }

    /**
     * 最後の曲か
     * @return
     */
    private static boolean isLast() {
        return mPosition == mLastIndex;
    }

    /**
     * decrementPosition
     */
    private static void decrementPosition() {
        if (isFirst()) {
            mPosition= mLastIndex;
        } else {
            mPosition--;
        }
    }

    /**
     * incrementPosition
     */
    private static void incrementPosition() {
        if (isLast()) {
            mPosition = 0;
        } else {
            mPosition++;
        }
    }

    /**
     * 再生中か
     * @return
     */
    public static boolean isPlaying() {
        return mMusicPlayer.isPlaying();
    }

    /**
     * 破棄
     */
    public static void destroy() {
        if (mMusicPlayer == null) return;

        mMusicPlayer.destroy();
        mMusicPlayer = null;
    }

    /**
     * 現在の再生位置を取得
     * @return ミリ秒
     */
    public static int getCurrentPosition() {
        return mMusicPlayer.getCurrentPosition();
    }

    /**
     * 再生中のtrackを取得
     * @return
     */
    public static Track getTrack() {
        if (mPlayList == null) return null;

        return mPlayList.get(mPosition);
    }

    /**
     * プレイリストをシャッフルする
     */
    private static void setShufflePlayList() {
        Track currentTrack = getTrack();
        ArrayList<Track> playlist = (ArrayList<Track>) mPlayList.clone();
        playlist.remove(currentTrack);
        Collections.shuffle(playlist);
        playlist.add(0, currentTrack);

        mPlayList = playlist;
        mPosition = 0;
    }

    /**
     * オリジナルのプレイリストをセットする
     */
    private static void setOriginalPlayList() {
        mPlayList = mOriginalPlayList;
        mPosition = mPlayList.indexOf(getTrack());
    }

    /**
     * リピートを変更する
     */
    public static void changeRepeat() {
        switch (repeat) {
            case REPEAT_OFF :
                repeat = REPEAT_ONE;
                break;
            case REPEAT_ONE :
                repeat = REPEAT_ALL;
                break;
            case REPEAT_ALL :
                repeat = REPEAT_OFF;
                break;
            default:
                repeat = REPEAT_OFF;
        }
    }

    /**
     * シャッフルを変更する
     */
    public static void changeShuffle() {
        shuffle = !shuffle;

        if (shuffle) {
            setShufflePlayList();
        } else {
            setOriginalPlayList();
        }
    }
}
