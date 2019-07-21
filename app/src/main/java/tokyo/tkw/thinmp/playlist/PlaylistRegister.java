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

    /**
     * playlistを新規作成
     */
    public void create(String name, Music music) {
        int nextOrder = getNextPlaylistOrder();

        beginTransaction();

        PlaylistRealm playlist = realm.createObject(PlaylistRealm.class, UUID.randomUUID().toString());
        playlist.set(name, createPlaylistTrackRealmList(playlist.getId(), music.getTrackList()), nextOrder);

        commitTransaction();
    }

    /**
     * playlistにtrackを追加
     */
    public void add(String playlistId, Music music) {
        beginTransaction();

        List<PlaylistTrackRealm> playlistTrackRealmList = createPlaylistTrackRealmList(
                playlistId,
                music.getTrackList()
        );
        PlaylistRealm playlist = realm.where(PlaylistRealm.class).equalTo(PlaylistRealm.ID, playlistId).findFirst();
        playlist.getTrackRealmList().addAll(playlistTrackRealmList);

        commitTransaction();
    }

    private List<PlaylistTrackRealm> createPlaylistTrackRealmList(String playlistId, List<Track> trackList) {
        return Stream.of(trackList).map(track -> {
            PlaylistTrackRealm playlistTrackRealm = realm.createObject(
                    PlaylistTrackRealm.class,
                    UUID.randomUUID().toString()
            );
            playlistTrackRealm.set(playlistId, track);
            return playlistTrackRealm;
        }).toList();
    }

    private int getNextPlaylistOrder() {
        Number max = realm.where(PlaylistRealm.class).max(PlaylistRealm.ORDER);
        return (max != null) ? max.intValue() + 1 : 1;
    }


}
