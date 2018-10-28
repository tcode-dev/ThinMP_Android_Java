package tokyo.tkw.thinmp.favorite;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.List;

import io.realm.Realm;
import tokyo.tkw.thinmp.util.ActivityUtil;

public class FavoriteRegister {
    private final String fieldName = "trackId";
    private Realm mRealm;

    public FavoriteRegister() {
        Realm.init(ActivityUtil.getContext());
        mRealm = Realm.getDefaultInstance();
    }

    public static FavoriteRegister createInstance() {
        return new FavoriteRegister();
    }

    public void beginTransaction() {
        mRealm.beginTransaction();
    }

    public void commitTransaction() {
        mRealm.commitTransaction();
    }

    public Favorite findFirst(String trackId) {
        return mRealm.where(Favorite.class).equalTo(fieldName, trackId).findFirst();
    }

    /**
     * trackをupdate
     * @param trackId
     * @return
     */
    private boolean update(String trackId) {
        Favorite favorite = findFirst(trackId);

        beginTransaction();

        boolean isFavorite;
        if (favorite == null) {
            mRealm.createObject(Favorite.class, trackId);
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

        mRealm.delete(Favorite.class);

        Stream.of(list).map(trackId -> mRealm.createObject(Favorite.class, trackId)).collect(Collectors.toList());

        commitTransaction();
    }


    public static boolean set(String trackId) {
        FavoriteRegister favoriteRegister = FavoriteRegister.createInstance();

        return favoriteRegister.update(trackId);
    }

    public static boolean exists(String trackId) {
        FavoriteRegister favoriteRegister = FavoriteRegister.createInstance();

        return favoriteRegister.findFirst(trackId) != null;
    }

    public static void update(List list) {
        FavoriteRegister favoriteRegister = FavoriteRegister.createInstance();
        favoriteRegister.allUpdate(list);
    }
}
