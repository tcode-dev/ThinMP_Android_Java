package tokyo.tkw.thinmp.playlist;

import io.realm.RealmObject;

public class PlaylistTrack extends RealmObject {
    private int playlistId;
    private String trackId;

    public int getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(int playlistId) {
        this.playlistId = playlistId;
    }

    public String getTrackId() {
        return trackId;
    }

    public void setTrackId(String trackId) {
        this.trackId = trackId;
    }
}
