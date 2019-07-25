package tokyo.tkw.thinmp.player;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.Collections;
import java.util.List;

import tokyo.tkw.thinmp.track.Track;

/**
 * 再生中の曲リスト
 */
public class PlayingList {
    // 再生曲リスト
    private List<Track> playingList;
    // シャッフル後に戻すためのバックアップ
    private List<Track> originalList;
    // 現在位置
    private int currentIndex;

    PlayingList(List<Track> playingList, int position) {
        this.playingList = playingList;
        originalList = Stream.of(playingList).toList();
        currentIndex = position;
    }

    /**
     * 再生中のtrackを取得
     *
     * @return
     */
    public Track getTrack() {
        return playingList.get(currentIndex);
    }

    /**
     * 前の曲が存在するか
     *
     * @return
     */
    private boolean hasPrev() {
        return currentIndex != 0;
    }

    /**
     * 次の曲が存在するか
     *
     * @return
     */
    boolean hasNext() {
        return currentIndex != getLastIndex();
    }

    /**
     * 前の曲へ
     */
    void prev() {
        if (hasPrev()) {
            currentIndex--;
        } else {
            currentIndex = getLastIndex();
        }
    }

    /**
     * 次の曲へ
     */
    public void next() {
        if (hasNext()) {
            currentIndex++;
        } else {
            currentIndex = 0;
        }
    }

    private int getLastIndex() {
        return playingList.size() - 1;
    }

    /**
     * プレイリストをシャッフルする
     */
    void shuffle() {
        Track currentTrack = getTrack();

        List<Track> playingList = Stream.of(originalList).toList();
        playingList.remove(currentTrack);
        Collections.shuffle(playingList);
        playingList.add(0, currentTrack);

        this.playingList = playingList;
        currentIndex = 0;
    }

    /**
     * プレイリストを元の順に戻す
     */
    void undo() {
        playingList = Stream.of(originalList).collect(Collectors.toList());
    }

    boolean validation() {
        remove();

        if (playingList.isEmpty()) return false;

        if (currentIndex == playingList.size()) {
            currentIndex = 0;
        }

        return true;
    }

    /**
     * ローカル楽曲が削除されている場合に一覧から削除する
     */
    private void remove() {
        playingList.remove(currentIndex);
    }
}
