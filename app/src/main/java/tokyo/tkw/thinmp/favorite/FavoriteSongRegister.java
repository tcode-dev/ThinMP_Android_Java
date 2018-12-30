package tokyo.tkw.thinmp.favorite;

import com.annimon.stream.IntStream;

import java.util.List;

import io.realm.Realm;
import tokyo.tkw.thinmp.util.ActivityUtil;

public class FavoriteSongRegister {
    private Realm mRealm;

    public FavoriteSongRegister() {
        Realm.init(ActivityUtil.getContext());
        mRealm = Realm.getDefaultInstance();
    }

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

    public static void update(List list) {
        FavoriteSongRegister favoriteRegister = FavoriteSongRegister.createInstance();
        favoriteRegister.allUpdate(list);
    }

    public void beginTransaction() {
        mRealm.beginTransaction();
    }

    public void commitTransaction() {
        mRealm.commitTransaction();
    }

    public FavoriteSong findFirst(String trackId) {
        return mRealm.where(FavoriteSong.class).equalTo("trackId", trackId).findFirst();
    }

    /**
     * trackをupdate
     *
     * @param trackId
     * @return
     */
    private boolean update(String trackId) {
        FavoriteSong favorite = findFirst(trackId);

        beginTransaction();

        boolean isFavorite;
        if (favorite == null) {
            mRealm.createObject(FavoriteSong.class, nextId()).setTrackId(trackId);
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
    private void allUpdate(List list) {
        beginTransaction();

        mRealm.delete(FavoriteSong.class);

        IntStream.range(0, list.size()).forEach(i -> mRealm.createObject(FavoriteSong.class, i + 1).setTrackId(list.get(i).toString()));

        commitTransaction();
    }

    private int nextId() {
        Number maxId = mRealm.where(FavoriteSong.class).max("id");

        return (maxId != null) ? maxId.intValue() + 1 : 1;
    }
}
