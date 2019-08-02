package tokyo.tkw.thinmp.register.exists;

import tokyo.tkw.thinmp.realm.FavoriteArtistRealm;
import tokyo.tkw.thinmp.register.RealmRegister;

public class FavoriteArtistExists extends RealmRegister {
    public static FavoriteArtistExists createInstance() {
        return new FavoriteArtistExists();
    }

    public boolean exists(String artistId) {
        return findFirst(artistId) != null;
    }

    private FavoriteArtistRealm findFirst(String artistId) {
        return realm.where(FavoriteArtistRealm.class).equalTo(FavoriteArtistRealm.ARTIST_ID, artistId).findFirst();
    }
}
