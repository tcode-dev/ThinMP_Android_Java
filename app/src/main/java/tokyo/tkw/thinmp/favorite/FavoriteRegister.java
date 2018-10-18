package tokyo.tkw.thinmp.favorite;

import io.realm.Realm;
import tokyo.tkw.thinmp.util.ActivityUtil;

public class FavoriteRegister {
    private static final String fieldName = "trackId";

    private static boolean update(String trackId) {
        Realm.init(ActivityUtil.getContext());
        Realm realm = Realm.getDefaultInstance();
        Favorite favorite = realm.where(Favorite.class).equalTo(fieldName, trackId).findFirst();

        realm.beginTransaction();

        boolean isFavorite;
        if (favorite == null) {
            realm.createObject(Favorite.class, trackId);
            isFavorite = true;
        } else {
            favorite.deleteFromRealm();
            isFavorite = false;
        }

        realm.commitTransaction();

        return isFavorite;
    }

    private static Favorite get(String trackId) {
        Realm.init(ActivityUtil.getContext());
        Realm realm = Realm.getDefaultInstance();

        return realm.where(Favorite.class).equalTo("trackId", trackId).findFirst();
    }

    public static boolean set(String trackId) {
        return update(trackId);
    }

    public static boolean exists(String trackId) {
        return get(trackId) != null;
    }
}
