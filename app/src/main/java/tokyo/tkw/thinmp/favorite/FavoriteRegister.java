package tokyo.tkw.thinmp.favorite;

import android.app.Activity;

import io.realm.Realm;

public class FavoriteRegister {
    private Activity mContext;
    private static FavoriteRegister instance = null;

    public FavoriteRegister(Activity context) {
        mContext = context;
    }

    public static void setInstance(Activity context) {
        instance = new FavoriteRegister(context);
    }

    public void update(String trackId) {
        Realm.init(mContext);
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

    public Favorite get(String trackId) {
        Realm.init(mContext);
        Realm realm = Realm.getDefaultInstance();

        return realm.where(Favorite.class).equalTo("trackId", trackId).findFirst();
    }

    public static void set(String trackId) {
        instance.update(trackId);
    }

    public static boolean exists(String trackId) {
        return instance.get(trackId) != null;
    }
}
