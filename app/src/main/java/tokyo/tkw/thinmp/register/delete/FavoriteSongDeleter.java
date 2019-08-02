package tokyo.tkw.thinmp.register.delete;

import com.annimon.stream.Optional;

import java.util.List;

import io.realm.RealmResults;
import tokyo.tkw.thinmp.realm.FavoriteSongRealm;
import tokyo.tkw.thinmp.register.RealmRegister;

public class FavoriteSongDeleter extends RealmRegister {
    public static FavoriteSongDeleter createInstance() {
        return new FavoriteSongDeleter();
    }

    public void delete(String trackId) {
        Optional.ofNullable(findFirst(trackId)).ifPresent(realm -> {
            beginTransaction();
            realm.deleteFromRealm();
            commitTransaction();
        });
    }

    public void delete(List<String> trackIdList) {
        Optional.ofNullable(findByTrackId(trackIdList)).ifPresent(realmResults -> {
            beginTransaction();
            realmResults.deleteAllFromRealm();
            commitTransaction();
        });
    }

    private FavoriteSongRealm findFirst(String trackId) {
        return realm.where(FavoriteSongRealm.class).equalTo(FavoriteSongRealm.TRACK_ID, trackId).findFirst();
    }

    private RealmResults findByTrackId(List<String> trackIdList) {
        return realm.where(FavoriteSongRealm.class)
                .in(FavoriteSongRealm.TRACK_ID, trackIdList.toArray(new String[0]))
                .findAll();
    }
}
