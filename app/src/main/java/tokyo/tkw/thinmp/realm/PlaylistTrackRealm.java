package tokyo.tkw.thinmp.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import tokyo.tkw.thinmp.track.Track;

public class PlaylistTrackRealm extends RealmObject {
    public static final String ID = "id";
    public static final String PLAYLIST_ID = "playlistId";
    public static final String TRACK_ID = "trackId";
    @PrimaryKey
    private String id;
    private String playlistId;
    private String trackId;

    public void set(String playlistId, Track track) {
        this.playlistId = playlistId;
        this.trackId = track.getId();
    }

    public String getId() {
        return id;
    }

    public String getPlaylistId() {
        return playlistId;
    }

    public String getTrackId() {
        return trackId;
    }
}
