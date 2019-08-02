package tokyo.tkw.thinmp.register.delete;

import com.annimon.stream.Optional;

import java.util.List;

import io.realm.RealmResults;
import tokyo.tkw.thinmp.realm.FavoriteArtistRealm;
import tokyo.tkw.thinmp.register.RealmRegister;

public class FavoriteArtistDeleter extends RealmRegister {
    public static FavoriteArtistDeleter createInstance() {
        return new FavoriteArtistDeleter();
    }

    public void delete(String artistId) {
        Optional.ofNullable(findFirst(artistId)).ifPresent(realm -> {
            beginTransaction();
            realm.deleteFromRealm();
            commitTransaction();
        });
    }

    public void delete(List<String> artistIdList) {
        Optional.ofNullable(findByArtistId(artistIdList)).ifPresent(realmResults -> {
            beginTransaction();
            realmResults.deleteAllFromRealm();
            commitTransaction();
        });
    }

    private FavoriteArtistRealm findFirst(String artistId) {
        return realm.where(FavoriteArtistRealm.class).equalTo(FavoriteArtistRealm.ARTIST_ID, artistId).findFirst();
    }

    private RealmResults findByArtistId(List<String> artistIdList) {
        return realm.where(FavoriteArtistRealm.class)
                .in(FavoriteArtistRealm.ARTIST_ID, artistIdList.toArray(new String[0]))
                .findAll();
    }
}
