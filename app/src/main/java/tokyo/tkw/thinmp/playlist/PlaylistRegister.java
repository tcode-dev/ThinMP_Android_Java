package tokyo.tkw.thinmp.playlist;

import com.annimon.stream.IntStream;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import tokyo.tkw.thinmp.music.Track;
import tokyo.tkw.thinmp.realm.PlaylistRealm;
import tokyo.tkw.thinmp.realm.PlaylistTrackRealm;

public class PlaylistRegister {
    private Realm mRealm;

    public PlaylistRegister() {
        mRealm = Realm.getDefaultInstance();
    }

    public void beginTransaction() {
        mRealm.beginTransaction();
    }

    public void commitTransaction() {
        mRealm.commitTransaction();
    }

    public void cancelTransaction() {
        mRealm.cancelTransaction();
    }

    /**
     * playlistを新規作成
     *
     * @param name
     * @param track
     */
    public void create(String name, Track track) {
        List<Track> trackList = new ArrayList<>();
        trackList.add(track);
        create(name, trackList);
    }

    /**
     * playlistを新規作成
     *
     * @param name
     * @param trackList
     */
    public void create(String name, List<Track> trackList) {
        int playlistId = createNextPlaylistId();
        int nextOrder = createNextPlaylistOrder();

        beginTransaction();

        PlaylistRealm playlist = mRealm.createObject(PlaylistRealm.class, playlistId);
        playlist.set(name, createPlaylistTrackRealmList(playlistId, trackList), nextOrder);

        commitTransaction();
    }

    public void add(int playlistId, Track track) {
        PlaylistRealm playlist =
                mRealm.where(PlaylistRealm.class).equalTo(PlaylistRealm.ID, playlistId).findFirst();

        beginTransaction();

        int playlistTrackRealmId = createNextPlaylistTrackId();
        PlaylistTrackRealm realm = mRealm.createObject(PlaylistTrackRealm.class,
                playlistTrackRealmId);
        realm.set(playlistId, track);
        playlist.getTrackRealmList().add(realm);

        commitTransaction();
    }

    private List<PlaylistTrackRealm> createPlaylistTrackRealmList(int playlistId,
                                                                  List<Track> trackList) {
        return IntStream.range(0, trackList.size()).mapToObj(i -> {
            Track track = trackList.get(i);
            int playlistTrackRealmId = createNextPlaylistTrackId();
            PlaylistTrackRealm realm = mRealm.createObject(PlaylistTrackRealm.class,
                    playlistTrackRealmId);
            realm.set(playlistId, track);
            return realm;
        }).toList();
    }

    private int createNextPlaylistId() {
        Number max = mRealm.where(PlaylistRealm.class).max(PlaylistRealm.ID);
        return createNextInt(max);
    }

    private int createNextPlaylistOrder() {
        Number max = mRealm.where(PlaylistRealm.class).max(PlaylistRealm.ORDER);
        return createNextInt(max);
    }

    private int createNextPlaylistTrackId() {
        Number max = mRealm.where(PlaylistTrackRealm.class).max(PlaylistTrackRealm.ID);
        return createNextInt(max);
    }

    private int createNextInt(Number num) {
        return (num != null) ? num.intValue() + 1 : 1;
    }
}
