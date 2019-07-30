package tokyo.tkw.thinmp.favorite;

import com.annimon.stream.Stream;

import java.util.List;

import tokyo.tkw.thinmp.realm.FavoriteSongRealm;
import tokyo.tkw.thinmp.realm.RealmRegister;

public class FavoriteSongRegister extends RealmRegister {
    public static FavoriteSongRegister createInstance() {
        return new FavoriteSongRegister();
    }

    public boolean exists(String trackId) {
        return findFirst(trackId) != null;
    }

    public void add(String trackId) {
        beginTransaction();

        realm.createObject(FavoriteSongRealm.class, uuid())
                .set(trackId, increment(FavoriteSongRealm.class, FavoriteSongRealm.ORDER));

        commitTransaction();
    }

    public void delete(String trackId) {
        beginTransaction();

        findFirst(trackId).deleteFromRealm();

        commitTransaction();
    }

    public void delete(List<String> trackIdList) {
        beginTransaction();

        realm.where(FavoriteSongRealm.class)
                .in(FavoriteSongRealm.TRACK_ID, trackIdList.toArray(new String[0]))
                .findAll()
                .deleteAllFromRealm();

        commitTransaction();
    }

    /**
     * truncateしてlistを登録する
     *
     * @param trackIdList
     */
    public void update(List<String> trackIdList) {
        beginTransaction();

        realm.delete(FavoriteSongRealm.class);

        Stream.of(trackIdList).forEach((trackId) -> {
            realm.createObject(FavoriteSongRealm.class, uuid())
                    .set(trackId, increment(FavoriteSongRealm.class, FavoriteSongRealm.ORDER));
        });

        commitTransaction();
    }

    private FavoriteSongRealm findFirst(String trackId) {
        return realm.where(FavoriteSongRealm.class).equalTo(FavoriteSongRealm.TRACK_ID, trackId).findFirst();
    }
}
