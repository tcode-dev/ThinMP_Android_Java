package tokyo.tkw.thinmp.playlist;

import com.annimon.stream.Stream;

import java.util.List;
import java.util.UUID;

import tokyo.tkw.thinmp.music.Music;
import tokyo.tkw.thinmp.realm.PlaylistRealm;
import tokyo.tkw.thinmp.realm.PlaylistTrackRealm;
import tokyo.tkw.thinmp.realm.RealmRegister;
import tokyo.tkw.thinmp.track.Track;

public class PlaylistRegister extends RealmRegister {
    private PlaylistRegister() {
        super();
    }

    public static PlaylistRegister createInstance() {
        return new PlaylistRegister();
    }

    public void create(String name, Music music) {
        int nextOrder = getNextPlaylistOrder();

        beginTransaction();

        PlaylistRealm playlist = realm.createObject(PlaylistRealm.class, UUID.randomUUID().toString());
        playlist.set(name, createPlaylistTrackRealmList(playlist.getId(), music.getIdList()), nextOrder);

        commitTransaction();
    }

    public void add(String playlistId, Music music) {
        beginTransaction();

        PlaylistRealm playlist = getPlaylist(playlistId);

        List<PlaylistTrackRealm> playlistTrackRealmList = createPlaylistTrackRealmList(
                playlistId,
                music.getIdList()
        );

        playlist.getTrackRealmList().addAll(playlistTrackRealmList);

        commitTransaction();
    }

    public void delete(List<String> trackIdList) {
        beginTransaction();

        temporaryDelete(trackIdList);

        commitTransaction();
    }

    public void temporaryDelete(List<String> trackIdList) {
        realm.where(PlaylistTrackRealm.class)
                .in(PlaylistTrackRealm.TRACK_ID, trackIdList.toArray(new String[0]))
                .findAll()
                .deleteAllFromRealm();
    }

    public void update(String playlistId, String name, List<String> fromTrackIdList, List<String> toTrackIdList) {
        beginTransaction();

        PlaylistRealm playlist = getPlaylist(playlistId);

        playlist.setName(name);

        temporaryDelete(fromTrackIdList);

        List<PlaylistTrackRealm> playlistTrackRealmList = createPlaylistTrackRealmList(
                playlistId,
                toTrackIdList
        );

        playlist.getTrackRealmList().addAll(playlistTrackRealmList);

        commitTransaction();
    }

    private PlaylistRealm getPlaylist(String playlistId) {
        return realm.where(PlaylistRealm.class).equalTo(PlaylistRealm.ID, playlistId).findFirst();
    }

    private List<PlaylistTrackRealm> createPlaylistTrackRealmList(String playlistId, List<String> trackIdList) {
        return Stream.of(trackIdList).map(trackId -> {
            PlaylistTrackRealm playlistTrackRealm = realm.createObject(
                    PlaylistTrackRealm.class,
                    UUID.randomUUID().toString()
            );
            playlistTrackRealm.set(playlistId, trackId);
            return playlistTrackRealm;
        }).toList();
    }

    private int getNextPlaylistOrder() {
        Number max = realm.where(PlaylistRealm.class).max(PlaylistRealm.ORDER);
        return (max != null) ? max.intValue() + 1 : 1;
    }
}
