package tokyo.tkw.thinmp.playlist;

import android.content.Context;

import com.annimon.stream.Optional;

import java.util.List;

import io.realm.RealmList;
import tokyo.tkw.thinmp.realm.PlaylistRealm;
import tokyo.tkw.thinmp.realm.PlaylistTrackRealm;
import tokyo.tkw.thinmp.track.Track;

public class Playlist {
    public static final String PLAYLIST_ID = "playlistId";
    private PlaylistRealm playlistRealm;
    private PlaylistTrack playlistTrack;

    private Playlist(Context context, PlaylistRealm playlistRealm) {
        this.playlistRealm = playlistRealm;
        RealmList<PlaylistTrackRealm> trackRealmList = playlistRealm.getTrackRealmList();
        this.playlistTrack = PlaylistTrack.createInstance(context, trackRealmList);
    }

    public static Optional<Playlist> createInstance(Context context, String playlistId) {
        return Optional.ofNullable(PlaylistRealm.createInstance(playlistId))
                .map(playlistRealm -> new Playlist(context, playlistRealm));
    }

    public String getId() {
        return playlistRealm.getId();
    }

    public String getName() {
        return playlistRealm.getName();
    }

    public String getAlbumArtId() {
        return playlistTrack.getAlbumArtId();
    }

    public List<String> getTrackIdList() {
        return playlistTrack.getTrackIdList();
    }

    public List<Track> getSortedTrackList() {
        return playlistTrack.getSortedTrackList();
    }
}
