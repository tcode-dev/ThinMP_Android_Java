package tokyo.tkw.thinmp.provider;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import tokyo.tkw.thinmp.music.AlbumList;
import tokyo.tkw.thinmp.music.ArtistList;
import tokyo.tkw.thinmp.music.Track;
import tokyo.tkw.thinmp.music.TrackList;

import static android.net.Uri.parse;

/**
 * MusicProvider
 * ストレージから曲を取得して一覧を作成する
 */
public class MusicProvider {
    private Cursor mCursor;
    private TrackList mTrackList = new TrackList();
    private ArtistList mArtistList = new ArtistList();
    private AlbumList mAlbumList = new AlbumList();

    public MusicProvider(Activity activity) {
        makeList(activity);
    }

    /**
     * TrackListを取得
     * @return
     */
    public TrackList getTrackList() {
        return mTrackList;
    };

    /**
     * ArtistListを取得
     * @return
     */
    public ArtistList getArtistList() {
        return mArtistList;
    };

    /**
     * AlbumListを取得
     * @return
     */
    public AlbumList getAlbumList() {
        return mAlbumList;
    };

    /**
     * 曲一覧、アーティスト一覧、アルバム一覧を作成する
     */
    private void makeList(Activity activity) {
        mCursor = new StorageProvider(activity).getCursor();

        if (mCursor == null) return;

        fetchAll();

        mCursor.close();
        mCursor = null;
    }
    /**
     * cursorから全曲取得して一覧を作成する
     */
    private void fetchAll() {
        while (mCursor.moveToNext()) {
            Track track = makeTrack();

            mTrackList.add(track);
            mArtistList.add(track);
            mAlbumList.add(track);
        }
    }

    /**
     * Trackを作成
     * @return Track
     */
    private Track makeTrack() {
        String id = getId();
        String title = getTitle();
        Uri uri = getUri();
        String artistId = getArtistId();
        String artistName = getArtistName();
        String albumId = getAlbumId();
        String albumName = getAlbumName();
        String thumbnailId = albumId;//サムネイルはアルバムアートを使用する
        int duration = getDuration();

        Track track = new Track(id, title, uri, artistId, artistName, albumId, albumName, thumbnailId, duration);

        return track;
    }

    /**
     * idを取得
     * @return
     */
    private String getId() {
        return mCursor.getString(mCursor.getColumnIndex(MediaStore.Audio.Media._ID));
    }

    /**
     * 曲名を取得
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
     * @return
     */
    private String getArtistName() {
        return mCursor.getString(mCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
    }

    /**
     * 曲のuriを取得
     * @return
     */
    private Uri getUri() {
        String url = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI + "/" + getId();
        Uri uri = parse(url);

        return uri;
    }

    /**
     * アルバムidを取得
     * @return
     */
    private String getAlbumId() {
        return mCursor.getString(mCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
    }

    /**
     * アルバム名を取得
     * @return
     */
    private String getAlbumName() {
        return mCursor.getString(mCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
    }

    /**
     * 再生時間を取得
     * @return
     */
    private int getDuration() {
        return mCursor.getInt(mCursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
    }
}
