package tokyo.tkw.thinmp.music;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.ArrayList;

import tokyo.tkw.thinmp.model.Album;
import tokyo.tkw.thinmp.model.Artist;
import tokyo.tkw.thinmp.model.Track;
import tokyo.tkw.thinmp.util.StorageController;

import static android.net.Uri.parse;

/**
 * Created by tk on 2018/03/22.
 */

public class MusicList {
    private Cursor mCursor;
    private Activity mContext;

    private TrackList mTrackList = new TrackList();
    private ArtistList mArtistList = new ArtistList();
    private AlbumList mAlbumList = new AlbumList();

    private static MusicList instance = null;

    public MusicList(Activity context) {
        mContext = context;
        init();
    }

    public void init() {
        mCursor = getCursor();

        if (mCursor == null) return;

        setAll();

        mCursor.close();
        mCursor = null;
        mContext = null;
    }

    public static void setInstance(Activity context) {
        instance = new MusicList(context);
    }

    public static MusicList getInstance() {
        return instance;
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
     * @return
     */
    public static Album getAlbum(String id) {
        return instance.mAlbumList.getAlbum(id);
    }

    /**
     * アーティストのアルバム一覧を取得
     */
    public static ArrayList<Album> getArtistAlbumList(String artistId) {
        Artist artist = getArtist(artistId);
        ArrayList<String> albumIdList = artist.getAlbumIdList();
        ArrayList<Album> albumList = new ArrayList<Album>();

        for (String id: albumIdList) {
            Album album = instance.mAlbumList.getAlbum(id);
            albumList.add(album);
        }

        return albumList;
    }

    /**
     * アルバムの曲一覧を取得
     */
    public static ArrayList<Track> getAlbumTrackList(String albumId) {
        Album album = getAlbum(albumId);
        ArrayList<String> trackIdList = album.getTrackIdList();
        ArrayList<Track> trackList = new ArrayList<Track>();

        for (String id: trackIdList) {
            Track track = instance.mTrackList.getTrack(id);
            trackList.add(track);
        }

        return trackList;
    }

    /**
     * ストレージから曲一覧を取得
     */
    private Cursor getCursor() {
        StorageController storageController = new StorageController(mContext);

        return storageController.getCursor();
    }

    /**
     * 曲一覧を設定
     */
    private void setAll() {
        while (mCursor.moveToNext()) {
            Track track = getTrack();

            mTrackList.add(track);
            mArtistList.add(track);
            mAlbumList.add(track);
        }
    }

    /**
     * 曲情報を取得
     * @return
     */
    private Track getTrack() {
        String id = getId();
        String title = getTitle();
        Uri uri = getUri();
        String artistId = getArtistId();
        String artistName = getArtistName();
        String albumId = getAlbumId();
        String albumName = getAlbumName();
        String thumbnailId = albumId;//サムネイルはアルバムアートを使用する

        Track track = new Track(id, title, uri, artistId, artistName, albumId, albumName, thumbnailId);

        return track;
    }

    /**
     * idを取得
     *
     * @return
     */
    private String getId() {
        return mCursor.getString(mCursor.getColumnIndex(MediaStore.Audio.Media._ID));
    }

    /**
     * 曲名を取得
     *
     * @return
     */
    private String getTitle() {
        return mCursor.getString(mCursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
    }

    /**
     * アーティストidを取得
     * @return
     */
    private String getArtistId() {
        return mCursor.getString(mCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST_ID));
    }

    /**
     * アーティスト名を取得
     *
     * @return
     */
    private String getArtistName() {
        return mCursor.getString(mCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
    }

    /**
     * 曲のuriを取得
     *
     * @return
     */
    private Uri getUri() {
        String url = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI + "/" + getId();
        Uri uri = parse(url);

        return uri;
    }

    /**
     * アルバムidを取得
     *
     * @return
     */
    private String getAlbumId() {
        return mCursor.getString(mCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
    }

    /**
     * アルバム名を取得
     *
     * @return
     */
    private String getAlbumName() {
        return mCursor.getString(mCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
    }
}
