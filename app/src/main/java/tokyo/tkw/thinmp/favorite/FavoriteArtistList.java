package tokyo.tkw.thinmp.favorite;

import android.content.Context;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.ArrayList;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmResults;
import tokyo.tkw.thinmp.music.Album;
import tokyo.tkw.thinmp.music.Artist;
import tokyo.tkw.thinmp.provider.ArtistsAlbumArtContentProvider;
import tokyo.tkw.thinmp.provider.ArtistsContentProvider;
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

    public ArrayList<FavoriteArtistRealm> getFavoriteArtistRealmList() {
        return (ArrayList<FavoriteArtistRealm>) Stream.of(mRealmResults).toList();
    }

    public Map<String, Artist> getArtistMap() {
        ArtistsContentProvider artistsContentProvider = new ArtistsContentProvider(mContext,
                toArtistIdList(getFavoriteArtistRealmList()));

        return Stream.of(artistsContentProvider.getList())
                .collect(Collectors.toMap(Artist::getId, artist -> artist));
    }

    private RealmResults<FavoriteArtistRealm> findAll() {
        Realm realm = Realm.getDefaultInstance();

        return realm.where(FavoriteArtistRealm.class).findAll().sort(FavoriteArtistRealm.ID);
    }

    public Map<String, String> getArtistAlbumArtMap() {
        return toArtistAlbumArtMap(getArtistsAlbumArtList());
    }

    public ArrayList<Album> getArtistsAlbumArtList() {
        ArtistsAlbumArtContentProvider provider = new ArtistsAlbumArtContentProvider(mContext,
                toArtistIdList(getFavoriteArtistRealmList()));
        return provider.getList();
    }

    private ArrayList<String> toArtistIdList(ArrayList<FavoriteArtistRealm> favoriteArtistRealmList) {
        return (ArrayList<String>) Stream.of(favoriteArtistRealmList)
                .map(FavoriteArtistRealm::getArtistId)
                .collect(Collectors.toList());
    }

    public Map<String, String> toArtistAlbumArtMap(ArrayList<Album> artistAlbumList) {
        return Stream.of(artistAlbumList)
                .distinctBy(Album::getArtistId)
                .collect(Collectors.toMap(Album::getArtistId, Album::getAlbumArtId));
    }
}
