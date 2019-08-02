package tokyo.tkw.thinmp.register.exists;

import tokyo.tkw.thinmp.realm.FavoriteSongRealm;
import tokyo.tkw.thinmp.register.RealmRegister;

public class FavoriteSongExists extends RealmRegister {
    public static FavoriteSongExists createInstance() {
        return new FavoriteSongExists();
    }

    public boolean exists(String trackId) {
        return findFirst(trackId) != null;
    }

    private FavoriteSongRealm findFirst(String trackId) {
        return realm.where(FavoriteSongRealm.class).equalTo(FavoriteSongRealm.TRACK_ID, trackId).findFirst();
    }
}
