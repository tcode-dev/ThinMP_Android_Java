package tokyo.tkw.thinmp.favorite;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.List;

import io.realm.Realm;
import tokyo.tkw.thinmp.util.ActivityUtil;

public class FavoriteSongRegister {
    private final String fieldName = "trackId";
    private Realm mRealm;

    public FavoriteSongRegister() {
        Realm.init(ActivityUtil.getContext());
        mRealm = Realm.getDefaultInstance();
    }

    public static FavoriteSongRegister createInstance() {
        return new FavoriteSongRegister();
    }

    public void beginTransaction() {
        mRealm.beginTransaction();
    }

    public void commitTransaction() {
        mRealm.commitTransaction();
    }

    public FavoriteSong findFirst(String trackId) {
        return mRealm.where(FavoriteSong.class).equalTo(fieldName, trackId).findFirst();
    }

    /**
     * trackをupdate
     * @param trackId
     * @return
     */
    private boolean update(String trackId) {
        FavoriteSong favorite = findFirst(trackId);

        beginTransaction();

        boolean isFavorite;
        if (favorite == null) {
            mRealm.createObject(FavoriteSong.class, trackId);
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
     * @param list
     */
    private void allUpdate(List list) {
        beginTransaction();

        mRealm.delete(FavoriteSong.class);

        Stream.of(list).map(trackId -> mRealm.createObject(FavoriteSong.class, trackId)).collect(Collectors.toList());

        commitTransaction();
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
}
