package tokyo.tkw.thinmp.favorite;

import android.content.Context;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.List;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmResults;
import tokyo.tkw.thinmp.music.Artist;
import tokyo.tkw.thinmp.provider.ArtistContentProvider;
import tokyo.tkw.thinmp.realm.FavoriteArtistRealm;

public class FavoriteArtists {
    private Context mContext;
    private RealmResults<FavoriteArtistRealm> mRealmResults;

    public FavoriteArtists(Context context) {
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

    private List<String> toArtistIdList(List<FavoriteArtistRealm> favoriteArtistRealmList) {
        return Stream.of(favoriteArtistRealmList)
                .map(FavoriteArtistRealm::getArtistId)
                .collect(Collectors.toList());
    }
}
