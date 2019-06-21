package tokyo.tkw.thinmp.playlist;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmResults;
import tokyo.tkw.thinmp.realm.PlaylistRealm;

public class Playlist {
    public static final String PLAYLIST_ID = "playlistId";

    private RealmResults<PlaylistRealm> mRealmResults;

    public Playlist() {
        mRealmResults = findAll();
    }

    public RealmResults<PlaylistRealm> getRealmResults() {
        return mRealmResults;
    }

    private RealmResults<PlaylistRealm> findAll() {
        Realm realm = Realm.getDefaultInstance();

        return realm.where(PlaylistRealm.class).findAll().sort(PlaylistRealm.ORDER);
    }
}
