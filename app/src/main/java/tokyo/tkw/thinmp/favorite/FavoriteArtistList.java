package tokyo.tkw.thinmp.favorite;

import android.content.Context;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.ArrayList;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmResults;
import tokyo.tkw.thinmp.music.Artist;
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
        ArrayList<FavoriteArtistRealm> favoriteArtistRealmList = getFavoriteArtistRealmList();
        ArrayList<String> artistIdList =
                (ArrayList<String>) Stream.of(favoriteArtistRealmList)
                        .map(FavoriteArtistRealm::getArtistId)
                        .collect(Collectors.toList());
        ArtistsContentProvider artistsContentProvider = new ArtistsContentProvider(mContext,
                artistIdList);

        return Stream.of(artistsContentProvider.getList())
                .collect(Collectors.toMap(Artist::getId, artist -> artist));
    }

    private RealmResults<FavoriteArtistRealm> findAll() {
        Realm realm = Realm.getDefaultInstance();

        return realm.where(FavoriteArtistRealm.class).findAll().sort(FavoriteArtistRealm.ID);
    }
}
