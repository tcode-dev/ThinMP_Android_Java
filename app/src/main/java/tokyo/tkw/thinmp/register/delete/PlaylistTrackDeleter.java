package tokyo.tkw.thinmp.register.delete;

import com.annimon.stream.Optional;

import java.util.List;

import io.realm.RealmResults;
import tokyo.tkw.thinmp.realm.PlaylistTrackRealm;
import tokyo.tkw.thinmp.register.RealmRegister;

public class PlaylistTrackDeleter extends RealmRegister {
    public static PlaylistTrackDeleter createInstance() {
        return new PlaylistTrackDeleter();
    }

    public void deleteByPlaylistId(List<String> playlistIdList) {
        beginTransaction();

        temporaryDeleteByPlaylistId(playlistIdList);

        commitTransaction();
    }

    public void delete(List<String> trackIdList) {
        beginTransaction();

        temporaryDelete(trackIdList);

        commitTransaction();
    }

    private void temporaryDeleteByPlaylistId(List<String> playlistIdList) {
        Optional.ofNullable(findByPlaylistId(playlistIdList)).ifPresent(realmResults -> {
            realmResults.deleteAllFromRealm();
        });
    }

    private void temporaryDelete(List<String> trackIdList) {
        Optional.ofNullable(findByTrackId(trackIdList)).ifPresent(realmResults -> {
            realmResults.deleteAllFromRealm();
        });
    }

    private RealmResults findByPlaylistId(List<String> playlistIdList) {
        return realm.where(PlaylistTrackRealm.class)
                .in(PlaylistTrackRealm.PLAYLIST_ID, playlistIdList.toArray(new String[0]))
                .findAll();
    }

    private RealmResults findByTrackId(List<String> trackIdList) {
        return realm.where(PlaylistTrackRealm.class)
                .in(PlaylistTrackRealm.TRACK_ID, trackIdList.toArray(new String[0]))
                .findAll();
    }
}
