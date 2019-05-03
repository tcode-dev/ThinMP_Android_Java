package tokyo.tkw.thinmp.music;

import android.app.Activity;

import java.util.ArrayList;

import tokyo.tkw.thinmp.provider.MusicProvider;

/**
 * MusicList
 */
public class MusicList {
    private static MusicList instance = null;
    private TrackList mTrackList;
    private ArtistList mArtistList;
    private AlbumList mAlbumList;

    public MusicList(Activity activity) {
        MusicProvider musicProvider = new MusicProvider(activity);
        mTrackList = musicProvider.getTrackList();
        mArtistList = musicProvider.getArtistList();
        mAlbumList = musicProvider.getAlbumList();
    }

    public static MusicList getInstance() {
        return instance;
    }

    public static void setInstance(Activity activity) {
        instance = new MusicList(activity);
    }

    /**
     * 曲一覧を取得
     *
     * @return
     */
    public static ArrayList<Track> getTrackList() {
        return instance.mTrackList.getList();
    }

    /**
     * アーティスト一覧を取得
     *
     * @return
     */
    public static ArrayList<Artist> getArtistList() {
        return instance.mArtistList.getList();
    }

    /**
     * アーティストを取得
     *
     * @param id
     * @return
     */
    public static Artist getArtist(String id) {
        return instance.mArtistList.getArtist(id);
    }

    /**
     * アルバム一覧を取得
     *
     * @return
     */
    public static ArrayList<Album> getAlbumList() {
        return instance.mAlbumList.getList();
    }

    /**
     * アルバムを取得
     *
     * @param id
     * @return
     */
    public static Album getAlbum(String id) {
        return instance.mAlbumList.getAlbum(id);
    }

    /**
     * アーティストのアルバム一覧を取得
     *
     * @param artistId
     * @return
     */
    public static ArrayList<Album> getArtistAlbumList(String artistId) {
        Artist artist = getArtist(artistId);
        ArrayList<String> albumIdList = artist.getAlbumIdList();
        ArrayList<Album> albumList = new ArrayList<>();

        for (String id : albumIdList) {
            Album album = instance.mAlbumList.getAlbum(id);
            albumList.add(album);
        }

        return albumList;
    }

    /**
     * アーティストの曲一覧を取得
     *
     * @param artistId
     * @return
     */
    public static ArrayList<Track> getArtistTrackList(String artistId) {
        Artist artist = getArtist(artistId);
        ArrayList<String> trackIdList = artist.getTrackIdList();
        ArrayList<Track> trackList = new ArrayList<>();

        for (String id : trackIdList) {
            Track track = instance.mTrackList.getTrack(id);
            trackList.add(track);
        }

        return trackList;
    }

    /**
     * アルバムの曲一覧を取得
     *
     * @param albumId
     * @return
     */
    public static ArrayList<Track> getAlbumTrackList(String albumId) {
        Album album = getAlbum(albumId);
        ArrayList<String> trackIdList = album.getTrackIdList();
        ArrayList<Track> trackList = new ArrayList<>();

        for (String id : trackIdList) {
            Track track = instance.mTrackList.getTrack(id);
            trackList.add(track);
        }

        return trackList;
    }

    /**
     * trackを取得
     *
     * @param id
     * @return
     */
    public static Track getTrack(String id) {
        return instance.mTrackList.getTrack(id);
    }
}
