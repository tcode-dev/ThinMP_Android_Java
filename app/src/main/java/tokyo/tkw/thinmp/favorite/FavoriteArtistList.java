package tokyo.tkw.thinmp.favorite;

import android.content.Context;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.List;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmResults;
import tokyo.tkw.thinmp.music.Album;
import tokyo.tkw.thinmp.music.Artist;
import tokyo.tkw.thinmp.provider.AlbumArtContentProvider;
import tokyo.tkw.thinmp.provider.ArtistContentProvider;
import tokyo.tkw.thinmp.realm.FavoriteArtistRealm;

public class FavoriteArtistList {
    private Context mContext;
    private RealmResults<FavoriteArtistRealm> mRealmResults;

    public FavoriteArtistList(Context context) {
        mContext = context;
        mRealmResults = findAll();
    }

    public RealmResults<FavoriteArtistRealm> getRealmResults() {
        return mRealmResults;
    }

    public List<FavoriteArtistRealm> getFavoriteArtistRealmList() {
        return Stream.of(mRealmResults).toList();
    }

    public Map<String, Artist> getArtistMap() {
        ArtistContentProvider provider = new ArtistContentProvider(mContext);

        return Stream.of(provider.findById(toArtistIdList(getFavoriteArtistRealmList())))
                .collect(Collectors.toMap(Artist::getId, artist -> artist));
    }

    private RealmResults<FavoriteArtistRealm> findAll() {
        Realm realm = Realm.getDefaultInstance();

        return realm.where(FavoriteArtistRealm.class).findAll().sort(FavoriteArtistRealm.ID);
    }

    public Map<String, String> getArtistAlbumArtMap() {
        return toArtistAlbumArtMap(getArtistsAlbumArtList());
    }

    private List<Album> getArtistsAlbumArtList() {
        AlbumArtContentProvider provider = new AlbumArtContentProvider(mContext);

        return provider.findByArtist(toArtistIdList(getFavoriteArtistRealmList()));
    }

    private List<String> toArtistIdList(List<FavoriteArtistRealm> favoriteArtistRealmList) {
        return Stream.of(favoriteArtistRealmList)
                .map(FavoriteArtistRealm::getArtistId)
                .collect(Collectors.toList());
    }

    private Map<String, String> toArtistAlbumArtMap(List<Album> artistAlbumList) {
        return Stream.of(artistAlbumList)
                .distinctBy(Album::getArtistId)
                .collect(Collectors.toMap(Album::getArtistId, Album::getAlbumArtId));
    }
}
