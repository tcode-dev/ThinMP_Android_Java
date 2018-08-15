package tokyo.tkw.thinmp.favorite;

import android.app.Activity;

import io.realm.Realm;

public class FavoriteManager {
    private Activity mContext;
    private static FavoriteManager instance = null;

    public FavoriteManager(Activity context) {
        mContext = context;
    }

    public static void setInstance(Activity context) {
        instance = new FavoriteManager(context);
    }

    public void update(String trackId) {
        Realm.init(mContext);
        Realm realm = Realm.getDefaultInstance();
        Favorite favorite = realm.where(Favorite.class).equalTo("track_id", trackId).findFirst();

        realm.beginTransaction();

        if (favorite == null) {
            favorite = realm.createObject(Favorite.class, (String) trackId);
        } else {
            favorite.deleteFromRealm();
        }

        realm.commitTransaction();
    }

    public Favorite get(String trackId) {
        Realm.init(mContext);
        Realm realm = Realm.getDefaultInstance();

        return realm.where(Favorite.class).equalTo("track_id", trackId).findFirst();
    }

    public static void set(String trackId) {
        instance.update(trackId);
    }

    public static boolean exists(String trackId) {
        return instance.get(trackId) != null;
    }
}
