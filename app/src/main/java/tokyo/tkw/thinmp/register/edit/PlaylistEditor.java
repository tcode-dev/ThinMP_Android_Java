package tokyo.tkw.thinmp.register.edit;

import java.util.List;

import tokyo.tkw.thinmp.realm.PlaylistRealm;
import tokyo.tkw.thinmp.realm.PlaylistTrackRealm;
import tokyo.tkw.thinmp.register.RealmRegister;
import tokyo.tkw.thinmp.register.add.PlaylistTrackAdder;
import tokyo.tkw.thinmp.register.delete.PlaylistDeleter;

public class PlaylistEditor extends RealmRegister {
    private PlaylistDeleter playlistDeleter;
    private PlaylistTrackAdder playlistTrackAdder;

    private PlaylistEditor() {
        playlistDeleter = PlaylistDeleter.createInstance();
        playlistTrackAdder = PlaylistTrackAdder.createInstance();
    }

    public static PlaylistEditor createInstance() {
        return new PlaylistEditor();
    }

    public void update(String playlistId, String name, List<String> fromTrackIdList, List<String> toTrackIdList) {
        beginTransaction();

        PlaylistRealm playlist = getPlaylist(playlistId);

        playlist.setName(name);

        playlistDeleter.temporaryDelete(fromTrackIdList);

        List<PlaylistTrackRealm> playlistTrackRealmList = playlistTrackAdder.create(
                playlistId,
                toTrackIdList
        );

        playlist.getTrackRealmList().deleteAllFromRealm();
        playlist.getTrackRealmList().addAll(playlistTrackRealmList);

        commitTransaction();
    }

    private PlaylistRealm getPlaylist(String playlistId) {
        return PlaylistRealm.createInstance(playlistId);
    }
}
