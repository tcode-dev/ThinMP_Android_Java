package tokyo.tkw.thinmp.register.add;

import tokyo.tkw.thinmp.realm.FavoriteArtistRealm;
import tokyo.tkw.thinmp.register.RealmRegister;

public class FavoriteArtistAdder extends RealmRegister {
    public static FavoriteArtistAdder createInstance() {
        return new FavoriteArtistAdder();
    }

    public void add(String artistId) {
        beginTransaction();

        realm.createObject(FavoriteArtistRealm.class, uuid())
                .set(artistId, increment(FavoriteArtistRealm.class, FavoriteArtistRealm.ORDER));

        commitTransaction();
    }
}
