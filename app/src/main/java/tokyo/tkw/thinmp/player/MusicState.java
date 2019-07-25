package tokyo.tkw.thinmp.player;

public class MusicState {
    private boolean isPlaying;
    private int currentPosition;
    private int repeat;
    private boolean isShuffle;
    private boolean isFavoriteSong;
    private boolean isFavoriteArtist;

    MusicState(
            boolean isPlaying,
            int currentPosition,
            int repeat,
            boolean isShuffle,
            boolean isFavoriteSong,
            boolean isFavoriteArtist
    ) {
        this.isPlaying = isPlaying;
        this.currentPosition = currentPosition;
        this.repeat = repeat;
        this.isShuffle = isShuffle;
        this.isFavoriteSong = isFavoriteSong;
        this.isFavoriteArtist = isFavoriteArtist;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    int getCurrentPosition() {
        return currentPosition;
    }

    public int getRepeat() {
        return repeat;
    }

    boolean isShuffle() {
        return isShuffle;
    }

    boolean isFavoriteSong() {
        return isFavoriteSong;
    }

    boolean isFavoriteArtist() {
        return isFavoriteArtist;
    }
}
