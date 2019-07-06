package tokyo.tkw.thinmp.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import tokyo.tkw.thinmp.music.Track;

public class PlaylistTrackRealm extends RealmObject {
    public static final String ID = "id";
    public static final String PLAYLIST_ID = "playlistId";
    public static final String TRACK_ID = "trackId";
    public static final String ALBUM_ID = "albumId";
    @PrimaryKey
    private int id;
    private int playlistId;
    private String trackId;
    private String albumId;

    public void set(int playlistId, Track track) {
        this.playlistId = playlistId;
        this.trackId = track.getId();
        this.albumId = track.getAlbumId();
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

    public String getAlbumArtId() {
        return albumId;
    }
}
