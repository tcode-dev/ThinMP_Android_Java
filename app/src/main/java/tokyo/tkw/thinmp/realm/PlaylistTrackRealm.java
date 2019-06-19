package tokyo.tkw.thinmp.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import tokyo.tkw.thinmp.music.Track;

public class PlaylistTrackRealm extends RealmObject {
    public static final String ID = "id";
    public static final String PLAYLIST_ID = "playlistId";
    public static final String TRACK_ID = "trackId";
    public static final String ALBUM_ID = "albumId";
    public static final String ORDER = "order";
    @PrimaryKey
    private int id;
    private int playlistId;
    private String trackId;
    private String albumId;
    private int order;

    public void set(int playlistId, Track track, int order) {
        this.playlistId = playlistId;
        this.trackId = track.getId();
        this.albumId = track.getAlbumId();
        this.order = order;
    }

    public int getId() {
        return id;
    }

    public int getPlaylistId() {
        return playlistId;
    }

    public String getTrackId() {
        return trackId;
    }

    public String getAlbumId() {
        return albumId;
    }

    public int getOrder() {
        return order;
    }

    public String getAlbumArtId() {
        return albumId;
    }
}
