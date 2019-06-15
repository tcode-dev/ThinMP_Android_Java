package tokyo.tkw.thinmp.playlist;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmResults;
import tokyo.tkw.thinmp.realm.PlaylistRealm;

public class Playlist {
    private Context mContext;
    private RealmResults<PlaylistRealm> mRealmResults;

    public Playlist(Context context) {
        mContext = context;
        mRealmResults = findAll();
    }

    public RealmResults<PlaylistRealm> getRealmResults() {
        return mRealmResults;
    }

    private RealmResults<PlaylistRealm> findAll() {
        Realm.init(mContext);
        Realm realm = Realm.getDefaultInstance();

        return realm.where(PlaylistRealm.class).findAll().sort("order");
    }
}
