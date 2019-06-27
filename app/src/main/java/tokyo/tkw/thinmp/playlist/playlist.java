package tokyo.tkw.thinmp.playlist;

import com.annimon.stream.Stream;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import tokyo.tkw.thinmp.realm.PlaylistRealm;
import tokyo.tkw.thinmp.realm.PlaylistTrackRealm;

public class Playlist {
    public static final String PLAYLIST_ID = "playlistId";

    private RealmResults<PlaylistRealm> mRealmResults;

    public Playlist() {
        mRealmResults = findAll();
    }

    public RealmResults<PlaylistRealm> getRealmResults() {
        return mRealmResults;
    }

    public List<PlaylistRealm> getList() {
        return Stream.of(mRealmResults).toList();
    }

    private RealmResults<PlaylistRealm> findAll() {
        Realm realm = Realm.getDefaultInstance();

        return realm.where(PlaylistRealm.class).findAll().sort(PlaylistRealm.ORDER);
    }

    public RealmList<PlaylistRealm> getRealmList() {
        RealmList <PlaylistRealm> realmList = new RealmList<>();
        realmList.addAll(mRealmResults.subList(0, mRealmResults.size()));

        return realmList;
    }
}
