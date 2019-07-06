package tokyo.tkw.thinmp.music;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 再生中の曲リスト
 */
public class PlayingList {
    // 最後の曲のインデックス
    private static int mLastIndex;
    // 再生曲リスト
    private List<Track> mPlayingList;
    // シャッフル後に戻すためのバックアップ
    private List<Track> mOriginalList;
    // 現在位置
    private int mCurrentPosition;

    public PlayingList(List<Track> playingList, int position) {
        mPlayingList = playingList;
        mOriginalList = Stream.of(playingList).toList();
        mCurrentPosition = position;
        mLastIndex = playingList.size() - 1;
    }

    /**
     * 再生中のtrackを取得
     *
     * @return
     */
    public Track getTrack() {
        return mPlayingList.get(mCurrentPosition);
    }

    /**
     * 現在の曲の位置を取得
     *
     * @return
     */
    public int getCurrentPosition() {
        return mCurrentPosition;
    }

    /**
     * 前の曲が存在するか
     *
     * @return
     */
    public boolean hasPrev() {
        return mCurrentPosition != 0;
    }

    /**
     * 次の曲が存在するか
     *
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
    public void shuffle() {
        Track currentTrack = getTrack();

        List<Track> playingList = Stream.of(mOriginalList).toList();
        playingList.remove(currentTrack);
        Collections.shuffle(playingList);
        playingList.add(0, currentTrack);

        mPlayingList = playingList;
        mCurrentPosition = 0;
    }

    /**
     * プレイリストを元の順に戻す
     */
    public void undo() {
        mPlayingList = Stream.of(mOriginalList).collect(Collectors.toList());
    }
}
