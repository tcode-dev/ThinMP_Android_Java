package tokyo.tkw.thinmp.player;

public class MusicState {
    private boolean isPlaying;
    private int currentPosition;
    private boolean hasPrev;
    private boolean hasNext;
    private int repeat;
    private boolean isShuffle;
    private boolean isFavorite;
    private boolean isFavoriteArtist;

    public MusicState(boolean isPlaying, int currentPosition, boolean hasPrev, boolean hasNext, int repeat, boolean isShuffle, boolean isFavorite, boolean isFavoriteArtist) {
        this.isPlaying = isPlaying;
        this.currentPosition = currentPosition;
        this.hasPrev = hasPrev;
        this.hasNext = hasNext;
        this.repeat = repeat;
        this.isShuffle = isShuffle;
        this.isFavorite = isFavorite;
        this.isFavoriteArtist = isFavoriteArtist;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public boolean hasPrev() {
        return hasPrev;
    }

    public boolean hasNext() {
        return hasNext;
    }

    public int getRepeat() {
        return repeat;
    }

    public boolean isShuffle() {
        return isShuffle;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public boolean isFavoriteArtist() {
        return isFavoriteArtist;
    }
}
