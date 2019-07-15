package tokyo.tkw.thinmp.playlist;

import android.content.Context;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.List;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import tokyo.tkw.thinmp.realm.PlaylistRealm;

public class Playlists {
    private Context context;
    private Realm realm;
    private List<Integer> playlistIds;
    private RealmResults<PlaylistRealm> realmResults;
    private List<Playlist> playlists;

    private Playlists(Context context) {
        this.context = context;
        this.realm = Realm.getDefaultInstance();
        this.realmResults = findAll();
        this.playlistIds = getPlaylistIds();
    }

    private Playlists(Context context, List<Integer> playlistIds) {
        this.context = context;
        this.realm = Realm.getDefaultInstance();
        this.realmResults = findById(playlistIds);
        this.playlistIds = playlistIds;
    }

    public static Playlists createInstance(Context context) {
        return new Playlists(context);
    }

    public static Playlists createInstance(Context context, List<Integer> playlistIds) {
        return new Playlists(context, playlistIds);
    }

    public RealmResults<PlaylistRealm> getRealmResults() {
        return realmResults;
    }

    private RealmResults<PlaylistRealm> findById(List<Integer> playlistIds) {
        return realm.where(PlaylistRealm.class).in(PlaylistRealm.ID,
                playlistIds.toArray(new Integer[0])).findAll();
    }

    private RealmResults<PlaylistRealm> findAll() {
        return realm.where(PlaylistRealm.class).findAll().sort(PlaylistRealm.ORDER);
    }

    private List<Integer> getPlaylistIds() {
        return Stream.of(realmResults).map(PlaylistRealm::getId).toList();
    }

    public RealmList<PlaylistRealm> getRealmList() {
        RealmList<PlaylistRealm> realmList = new RealmList<>();
        realmList.addAll(realmResults.subList(0, realmResults.size()));

        return realmList;
    }

    public List<PlaylistRealm> getPlaylistRealmList() {
        return Stream.of(realmResults).toList();
    }

    public Map<Integer, Playlist> getPlaylistMap() {
        return Stream.of(playlistIds)
                .collect(Collectors.toMap(
                        playlistId -> playlistId,
                        playlistId -> Playlist.createInstance(context, playlistId)));

    }
}
