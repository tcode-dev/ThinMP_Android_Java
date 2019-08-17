package tokyo.tkw.thinmp.playlist;

import android.content.Context;

import com.annimon.stream.Optional;
import com.annimon.stream.Stream;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import tokyo.tkw.thinmp.realm.PlaylistRealm;

public class Playlists {
    private Context context;
    private Realm realm;
    private List<String> playlistIds;
    private RealmResults<PlaylistRealm> realmResults;
    private List<String> albumArtList;

    private Playlists(Context context) {
        this.context = context;
        this.realm = Realm.getDefaultInstance();
        this.realmResults = findAll();
        this.playlistIds = getPlaylistIds();
        this.albumArtList = getAlbumArtList();
    }

    private Playlists(Context context, List<String> playlistIds) {
        this.context = context;
        this.realm = Realm.getDefaultInstance();
        this.realmResults = findById(playlistIds);
        this.playlistIds = playlistIds;
        this.albumArtList = getAlbumArtList();
    }

    public static Playlists createInstance(Context context) {
        return new Playlists(context);
    }

    public static Playlists createInstance(Context context, List<String> playlistIds) {
        return new Playlists(context, playlistIds);
    }

    private RealmResults<PlaylistRealm> findById(List<String> playlistIds) {
        return realm.where(PlaylistRealm.class).in(PlaylistRealm.ID,
                playlistIds.toArray(new String[0])).findAll();
    }

    private RealmResults<PlaylistRealm> findAll() {
        return realm.where(PlaylistRealm.class).findAll().sort(PlaylistRealm.ORDER);
    }

    private List<String> getAlbumArtList() {
        PlaylistAlbumArt playlistAlbumArt = PlaylistAlbumArt.createInstance(context);

        return playlistAlbumArt.getAlbumArtList();
    }

    public List<String> getPlaylistIds() {
        return Stream.of(realmResults).map(PlaylistRealm::getId).toList();
    }

    public List<Playlist> getPlaylists() {
        return Stream.of(playlistIds)
                .map(playlistId -> Playlist.createInstance(context, playlistId, albumArtList))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }
}
