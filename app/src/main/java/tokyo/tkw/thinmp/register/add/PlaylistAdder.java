package tokyo.tkw.thinmp.register.add;

import java.util.List;

import tokyo.tkw.thinmp.music.Music;
import tokyo.tkw.thinmp.realm.PlaylistRealm;
import tokyo.tkw.thinmp.realm.PlaylistTrackRealm;
import tokyo.tkw.thinmp.register.RealmRegister;

public class PlaylistAdder extends RealmRegister {
    public static PlaylistAdder createInstance() {
        return new PlaylistAdder();
    }

    /**
     * プレイリストの新規作成
     *
     * @param name
     * @param music
     */
    public void create(String name, Music music) {
        beginTransaction();

        PlaylistRealm playlist = realm.createObject(PlaylistRealm.class, uuid());
        playlist.set(name, increment(PlaylistRealm.class, PlaylistRealm.ORDER),
                createPlaylistTrackRealmList(playlist.getId(), music.getIdList()));

        commitTransaction();
    }

    /**
     * 既存のプレイリストに曲を追加
     *
     * @param playlistId
     * @param music
     */
    public void add(String playlistId, Music music) {
        beginTransaction();

        PlaylistRealm playlist = getPlaylist(playlistId);
        List<PlaylistTrackRealm> playlistTrackRealmList = createPlaylistTrackRealmList(playlistId, music.getIdList());

        playlist.getTrackRealmList().addAll(playlistTrackRealmList);

        commitTransaction();
    }

    private List<PlaylistTrackRealm> createPlaylistTrackRealmList(String playlistId, List<String> trackIdList) {
        PlaylistTrackAdder playlistTrackAdder = PlaylistTrackAdder.createInstance();

        return playlistTrackAdder.create(playlistId, trackIdList);
    }

    private PlaylistRealm getPlaylist(String playlistId) {
        return PlaylistRealm.createInstance(playlistId);
    }
}
