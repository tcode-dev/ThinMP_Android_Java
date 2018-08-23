package tokyo.tkw.thinmp.playlist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import tokyo.tkw.thinmp.favorite.Favorite;
import tokyo.tkw.thinmp.music.Album;
import tokyo.tkw.thinmp.music.Track;

public class PlaylistRegister extends Fragment {
    private Realm mRealm;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    private void init() {
        Realm.init(getActivity());
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
     * @param name
     * @param track
     */
    public void create(String name, Track track) {
        String trackId = track.getId();
        List<String> trackIdList = new ArrayList<>();
        trackIdList.add(trackId);
        create(name, trackIdList);
    }

    /**
     * アルバムの曲を登録
     * @param name
     * @param album
     */
    public void create(String name, Album album) {
        List<String> trackIdList = album.getTrackIdList();

        create(name, trackIdList);
    }

    public void create(String name, List<String> trackIdList) {
        init();

        Number maxId = mRealm.where(Playlist.class).max("id");
        int playlistId = (maxId != null) ? maxId.intValue() + 1 : 1;

        Number maxOrder = mRealm.where(Playlist.class).max("order");
        int nextOrder = (maxOrder != null) ? maxOrder.intValue() + 1 : 1;

        beginTransaction();

        Playlist playlist = mRealm.createObject(Playlist.class, playlistId);
        playlist.setName(name);
        playlist.setOrder(nextOrder);
        List<PlaylistTrack> playlistTracks = new ArrayList<>();

        for (String trackId: trackIdList) {
            PlaylistTrack playlistTrack = new PlaylistTrack();
            playlistTrack.setPlaylistId(playlistId);
            playlistTrack.setTrackId(trackId);
            playlistTracks.add(playlistTrack);
        }

        playlist.getTracks().addAll(playlistTracks);

        commitTransaction();
    }

    public void add(int playlistId, Track track) {
        String trackId = track.getId();

        init();

        Playlist playlist = mRealm.where(Playlist.class).equalTo("id", playlistId).findFirst();

        beginTransaction();

        PlaylistTrack playlistTrack = new PlaylistTrack();
        playlistTrack.setPlaylistId(playlistId);
        playlistTrack.setTrackId(trackId);
        playlist.getTracks().add(playlistTrack);

        commitTransaction();
    }
}
