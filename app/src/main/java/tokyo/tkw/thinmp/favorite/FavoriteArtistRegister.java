package tokyo.tkw.thinmp.favorite;

import io.realm.Realm;
import tokyo.tkw.thinmp.util.ActivityUtil;

public class FavoriteArtistRegister {
    private static final String fieldName = "artistId";

    private static boolean update(String artistId) {
        Realm.init(ActivityUtil.getContext());
        Realm realm = Realm.getDefaultInstance();
        FavoriteArtist favorite = realm.where(FavoriteArtist.class).equalTo(fieldName, artistId).findFirst();

        realm.beginTransaction();

        boolean isFavorite;
        if (favorite == null) {
            realm.createObject(FavoriteArtist.class, artistId);
            isFavorite = true;
        } else {
            favorite.deleteFromRealm();
            isFavorite = false;
        }

        realm.commitTransaction();

        return isFavorite;
    }

    private static FavoriteArtist get(String artistId) {
        Realm.init(ActivityUtil.getContext());
        Realm realm = Realm.getDefaultInstance();

        return realm.where(FavoriteArtist.class).equalTo("artistId", artistId).findFirst();
    }

    public static boolean set(String artistId) {
        return update(artistId);
    }

    public static boolean exists(String artistId) {
        return get(artistId) != null;
    }
}
