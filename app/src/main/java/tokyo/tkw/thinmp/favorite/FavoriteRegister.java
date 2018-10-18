package tokyo.tkw.thinmp.favorite;

import io.realm.Realm;
import tokyo.tkw.thinmp.util.ActivityUtil;

public class FavoriteRegister {

    public static void update(String trackId) {
        Realm.init(ActivityUtil.getContext());
        Realm realm = Realm.getDefaultInstance();
        Favorite favorite = realm.where(Favorite.class).equalTo("trackId", trackId).findFirst();

        realm.beginTransaction();

        if (favorite == null) {
            realm.createObject(Favorite.class, trackId);
        } else {
            favorite.deleteFromRealm();
        }

        realm.commitTransaction();
    }

    public static Favorite get(String trackId) {
        Realm.init(ActivityUtil.getContext());
        Realm realm = Realm.getDefaultInstance();

        return realm.where(Favorite.class).equalTo("trackId", trackId).findFirst();
    }

    public static void set(String trackId) {
        update(trackId);
    }

    public static boolean exists(String trackId) {
        return get(trackId) != null;
    }
}
