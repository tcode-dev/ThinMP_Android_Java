package tokyo.tkw.thinmp.favorite;

import io.realm.Realm;
import io.realm.RealmResults;
import tokyo.tkw.thinmp.util.ActivityUtil;

public class FavoriteArtistList {
    public static RealmResults<FavoriteArtist> getFavoriteList() {
        Realm.init(ActivityUtil.getContext());
        Realm realm = Realm.getDefaultInstance();

        return realm.where(FavoriteArtist.class).findAll().sort("id");
    }
}
