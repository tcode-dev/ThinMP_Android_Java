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

/**
 * PlaylistRealmの一覧を取得
 * PlaylistRealmの一覧をlistに変換
 */
public class Playlist {
    public static final String PLAYLIST_ID = "playlistId";

    private Context mContext;
    private RealmResults<PlaylistRealm> mRealmResults;

    public Playlist(Context context) {
        mContext = context;
        mRealmResults = findAll();
    }

    public RealmResults<PlaylistRealm> getRealmResults() {
        return mRealmResults;
    }

    public List<PlaylistRealm> getList() {
        return Stream.of(mRealmResults).toList();
    }

    private RealmResults<PlaylistRealm> findAll() {
        Realm realm = Realm.getDefaultInstance();

        return realm.where(PlaylistRealm.class).findAll().sort(PlaylistRealm.ORDER);
    }

    public RealmList<PlaylistRealm> getRealmList() {
        RealmList<PlaylistRealm> realmList = new RealmList<>();
        realmList.addAll(mRealmResults.subList(0, mRealmResults.size()));

        return realmList;
    }

    private List<String> toAlbumIdList() {
        return Stream.of(mRealmResults)
                .flatMap(playlistRealm -> Stream.of(playlistRealm.getTracks()))
                .map(PlaylistTrackRealm::getAlbumArtId)
                .distinct()
                .collect(Collectors.toList());
    }

    private List<Album> getAlbumList() {
        AlbumArtContentProvider provider = new AlbumArtContentProvider(mContext);

        return provider.findByAlbum(toAlbumIdList());
    }

    private List<String> getAlbumArtList() {
        return Stream.of(getAlbumList()).map(Album::getAlbumArtId).collect(Collectors.toList());
    }

    public Map<Integer, Optional<String>> getAlbumArtMap() {
        List<String> albumArtList = getAlbumArtList();

        return Stream.of(mRealmResults)
                .collect(Collectors.toMap(PlaylistRealm::getId,
                        playlistRealm -> Stream.of(playlistRealm.getTracks())
                        .map(PlaylistTrackRealm::getAlbumArtId)
                        .filter(albumArtList::contains)
                        .findFirst()));
    }
}
