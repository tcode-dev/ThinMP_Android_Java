package tokyo.tkw.thinmp.playlist;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.annimon.stream.IntStream;

import java.util.ArrayList;

import io.realm.Realm;
import tokyo.tkw.thinmp.music.Album;
import tokyo.tkw.thinmp.music.Track;
import tokyo.tkw.thinmp.music.TrackCollection;
import tokyo.tkw.thinmp.realm.PlaylistRealm;
import tokyo.tkw.thinmp.realm.PlaylistTrackRealm;

public class PlaylistRegister extends Fragment {
    private Realm mRealm;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    private void init() {
        mRealm = Realm.getDefaultInstance();
    }

    private void beginTransaction() {
        mRealm.beginTransaction();
    }

    private void commitTransaction() {
        mRealm.commitTransaction();
    }

    /**
     * 曲を登録
     *
     * @param name
     * @param track
     */
    public void create(String name, Track track) {
        ArrayList<Track> trackList = new ArrayList<>();
        trackList.add(track);
        create(name, trackList);
    }

    /**
     * アルバムの曲を登録
     *
     * @param name
     * @param album
     */
    public void create(String name, Album album) {
        TrackCollection trackCollection = TrackCollection.createAlbumTrackCollectionInstance(getContext(), album.getId());

        create(name, trackCollection.getList());
    }

    public void create(String name, ArrayList<Track> trackList) {
        init();

        int playlistId = createNextPlaylistId();
        int nextOrder = createNextPlaylistOrder();

        beginTransaction();

        PlaylistRealm playlist = mRealm.createObject(PlaylistRealm.class, playlistId);
        playlist.set(name, createPlaylistTrackRealmList(playlistId, trackList), nextOrder);

        commitTransaction();
    }

    public void add(int playlistId, Track track) {
        init();

        PlaylistRealm playlist =
                mRealm.where(PlaylistRealm.class).equalTo(PlaylistRealm.ID, playlistId).findFirst();

        int nextOrder = createNextPlaylistTrackOrder();

        beginTransaction();

        PlaylistTrackRealm realm = new PlaylistTrackRealm();
        realm.set(playlistId, track, nextOrder);
        playlist.getTracks().add(realm);

        commitTransaction();
    }

    private ArrayList<PlaylistTrackRealm> createPlaylistTrackRealmList(int playlistId,
                                                                   ArrayList<Track> trackList) {
        return (ArrayList<PlaylistTrackRealm>) IntStream.range(0, trackList.size()).mapToObj(i -> {
            Track track = trackList.get(i);
            int playlistTrackRealmId = createNextPlaylistTrackId();
            PlaylistTrackRealm realm = mRealm.createObject(PlaylistTrackRealm.class, playlistTrackRealmId);
            realm.set(playlistId, track, i);
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
        Number max = mRealm.where(PlaylistTrackRealm.class).max(PlaylistRealm.ID);
        return createNextInt(max);
    }

    private int createNextPlaylistTrackOrder() {
        Number max = mRealm.where(PlaylistTrackRealm.class).max(PlaylistTrackRealm.ORDER);
        return createNextInt(max);
    }

    private int createNextInt(Number num) {
        return (num != null) ? num.intValue() + 1 : 1;
    }
}
