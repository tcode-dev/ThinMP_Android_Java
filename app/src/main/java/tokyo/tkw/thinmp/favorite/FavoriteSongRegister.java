package tokyo.tkw.thinmp.favorite;

import com.annimon.stream.IntStream;

import java.util.List;

import tokyo.tkw.thinmp.realm.FavoriteSongRealm;
import tokyo.tkw.thinmp.realm.RealmRegister;

public class FavoriteSongRegister extends RealmRegister {
    public static FavoriteSongRegister createInstance() {
        return new FavoriteSongRegister();
    }

    public static boolean set(String trackId) {
        FavoriteSongRegister favoriteRegister = FavoriteSongRegister.createInstance();

        return favoriteRegister.update(trackId);
    }

    public static boolean exists(String trackId) {
        FavoriteSongRegister favoriteRegister = FavoriteSongRegister.createInstance();

        return favoriteRegister.findFirst(trackId) != null;
    }

    public static void update(List<String> list) {
        FavoriteSongRegister favoriteRegister = FavoriteSongRegister.createInstance();
        favoriteRegister.allUpdate(list);
    }

    /**
     * 削除
     */
    public void remove(List<String> trackIdList) {
        beginTransaction();

        realm.where(FavoriteSongRealm.class)
                .in("trackId", trackIdList.toArray(new String[0]))
                .findAll().deleteAllFromRealm();

        commitTransaction();
    }

    public FavoriteSongRealm findFirst(String trackId) {
        return realm.where(FavoriteSongRealm.class).equalTo("trackId", trackId).findFirst();
    }

    /**
     * trackをupdate
     *
     * @param trackId
     * @return
     */
    private boolean update(String trackId) {
        FavoriteSongRealm favorite = findFirst(trackId);

        beginTransaction();

        boolean isFavorite;
        if (favorite == null) {
            realm.createObject(FavoriteSongRealm.class, nextId()).setTrackId(trackId);
            isFavorite = true;
        } else {
            favorite.deleteFromRealm();
            isFavorite = false;
        }

        commitTransaction();

        return isFavorite;
    }

    /**
     * truncateしてlistを登録する
     *
     * @param list
     */
    private void allUpdate(List<String> list) {
        beginTransaction();

        realm.delete(FavoriteSongRealm.class);

        IntStream.range(0, list.size()).forEach(i -> realm.createObject(FavoriteSongRealm.class,
                i + 1).setTrackId(list.get(i).toString()));

        commitTransaction();
    }

    private int nextId() {
        Number maxId = realm.where(FavoriteSongRealm.class).max("id");

        return (maxId != null) ? maxId.intValue() + 1 : 1;
    }
}
