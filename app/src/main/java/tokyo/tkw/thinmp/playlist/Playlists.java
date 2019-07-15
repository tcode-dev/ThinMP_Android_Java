package tokyo.tkw.thinmp.playlist;

import android.content.Context;

import com.annimon.stream.Collectors;
import com.annimon.stream.Optional;
import com.annimon.stream.Stream;

import java.util.List;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import tokyo.tkw.thinmp.music.Album;
import tokyo.tkw.thinmp.provider.AlbumArtContentProvider;
import tokyo.tkw.thinmp.realm.PlaylistRealm;
import tokyo.tkw.thinmp.realm.PlaylistTrackRealm;

public class Playlists {
    public static final String PLAYLIST_ID = "playlistId";

    private Context mContext;
    private Realm realm;

    private Playlists(Context context) {
        mContext = context;
        realm = Realm.getDefaultInstance();
    }

    public static Playlists createInstance(Context context) {
        return new Playlists(context);
    }

    public PlaylistRealm findById(int playlistId) {
        return realm.where(PlaylistRealm.class).equalTo(PlaylistRealm.ID, playlistId).findFirst();
    }

    public RealmResults<PlaylistRealm> findById(List<Integer> playlistIds) {
        return realm.where(PlaylistRealm.class).in(PlaylistRealm.ID, playlistIds.toArray(new Integer[0])).findAll();
    }

    public RealmResults<PlaylistRealm> findAll() {
        return realm.where(PlaylistRealm.class).findAll().sort(PlaylistRealm.ORDER);
    }

    public List<PlaylistRealm> toList(RealmResults<PlaylistRealm> realmResults) {
        return Stream.of(realmResults).toList();
    }

    public RealmList<PlaylistRealm> toRealmList(RealmResults<PlaylistRealm> realmResults) {
        RealmList<PlaylistRealm> realmList = new RealmList<>();
        realmList.addAll(realmResults.subList(0, realmResults.size()));

        return realmList;
    }

    private List<String> toAlbumIdList(RealmResults<PlaylistRealm> realmResults) {
        return Stream.of(realmResults)
                .flatMap(playlistRealm -> Stream.of(playlistRealm.getTrackRealmList()))
                .map(PlaylistTrackRealm::getAlbumArtId)
                .distinct()
                .collect(Collectors.toList());
    }

    private List<Album> getAlbumList(RealmResults<PlaylistRealm> realmResults) {
        AlbumArtContentProvider provider = new AlbumArtContentProvider(mContext);

        return provider.findById(toAlbumIdList(realmResults));
    }

    private List<String> getAlbumArtList(RealmResults<PlaylistRealm> realmResults) {
        return Stream.of(getAlbumList(realmResults)).map(Album::getAlbumArtId).collect(Collectors.toList());
    }

    public Map<Integer, Optional<String>> getAlbumArtMap(RealmResults<PlaylistRealm> realmResults) {
        List<String> albumArtList = getAlbumArtList(realmResults);

        return Stream.of(realmResults)
                .collect(Collectors.toMap(PlaylistRealm::getId,
                        playlistRealm -> Stream.of(playlistRealm.getTrackRealmList())
                        .map(PlaylistTrackRealm::getAlbumArtId)
                        .filter(albumArtList::contains)
                        .findFirst()));
    }
}
