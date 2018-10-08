package tokyo.tkw.thinmp.music;

import java.util.ArrayList;
import java.util.Collections;

/**
 * 再生中の曲リスト
 */
public class PlayingList {
    // 再生曲リスト
    private ArrayList<Track> mPlayingList;
    // 現在位置
    private int mCurrentPosition;
    // 最後の曲のインデックス
    private static int mLastIndex;

    public PlayingList(ArrayList<Track> playingList, int position) {
        mPlayingList = playingList;
        mCurrentPosition = position;
        mLastIndex = playingList.size() - 1;
    }

    /**
     * 再生中のtrackを取得
     * @return
     */
    public Track getTrack() {
        return mPlayingList.get(mCurrentPosition);
    }

    /**
     * 現在の曲の位置を取得
     * @return
     */
    public int getCurrentPosition() {
        return mCurrentPosition;
    }

    /**
     * 前の曲が存在するか
     * @return
     */
    public boolean hasPrev() {
        return mCurrentPosition != 0;
    }

    /**
     * 次の曲が存在するか
     * @return
     */
    public boolean hasNext() {
        return mCurrentPosition != mLastIndex;
    }

    /**
     * 前の曲へ
     */
    public void prev() {
        if (hasPrev()) {
            mCurrentPosition--;
        } else {
            mCurrentPosition = mLastIndex;
        }
    }

    /**
     * 次の曲へ
     */
    public void next() {
        if (hasNext()) {
            mCurrentPosition++;
        } else {
            mCurrentPosition = 0;
        }
    }

    /**
     * プレイリストをシャッフルする
     */
    public PlayingList getShufflePlayingList() {
        Track currentTrack = getTrack();
        ArrayList<Track> playingList = (ArrayList<Track>) mPlayingList.clone();
        playingList.remove(currentTrack);
        Collections.shuffle(playingList);
        playingList.add(0, currentTrack);

        return new PlayingList(playingList, 0);
    }
}
