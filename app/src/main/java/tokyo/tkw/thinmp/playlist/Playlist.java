package tokyo.tkw.thinmp.playlist;

import android.content.Context;

import com.annimon.stream.Optional;

import java.util.List;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmList;
import tokyo.tkw.thinmp.track.Track;
import tokyo.tkw.thinmp.realm.PlaylistRealm;
import tokyo.tkw.thinmp.realm.PlaylistTrackRealm;

public class Playlist {
    public static final String PLAYLIST_ID = "playlistId";

    private Realm realm;
    private PlaylistRealm playlistRealm;
    private RealmList<PlaylistTrackRealm> trackRealmList;
    private PlaylistTrack playlistTrack;

    private Playlist(Context context, String playlistId) {
        this.realm = Realm.getDefaultInstance();
        this.playlistRealm = findById(playlistId);
        this.trackRealmList = playlistRealm.getTrackRealmList();
        this.playlistTrack = PlaylistTrack.createInstance(context, trackRealmList);
    }

    public static Playlist createInstance(Context context, String playlistId) {
        return new Playlist(context, playlistId);
    }

    public String getId() {
        return playlistRealm.getId();
    }

    public String getName() {
        return playlistRealm.getName();
    }

    public PlaylistRealm getPlaylistRealm() {
        return playlistRealm;
    }

    public RealmList<PlaylistTrackRealm> getTrackRealmList() {
        return trackRealmList;
    }

    public Map<String, Track> getTrackMap() {
        return playlistTrack.getTrackMap();
    }

    public Optional<String> getAlbumArtId() {
        return playlistTrack.getAlbumArtId();
    }

    public List<Track> getSortedTrackList() {
        return playlistTrack.getSortedTrackList();
    }

    private PlaylistRealm findById(String playlistId) {
        return realm.where(PlaylistRealm.class).equalTo(PlaylistRealm.ID, playlistId).findFirst();
    }
}
