package tokyo.tkw.thinmp.provider;

import android.content.Context;
import android.provider.MediaStore;

import tokyo.tkw.thinmp.music.Album;

public abstract class MediaStoreAudioAlbumsProvider extends MediaStoreAudioProvider<Album> {
    public MediaStoreAudioAlbumsProvider(Context context) {
        super(context);

        uri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
        projection = new String[]{
                MediaStore.Audio.Albums._ID,
                MediaStore.Audio.Albums.ALBUM,
                MediaStore.Audio.Media.ARTIST_ID,
                MediaStore.Audio.Albums.ARTIST,
                MediaStore.Audio.Albums.ALBUM_ART
        };
    }

    private String getId() {
        return mCursor.getString(mCursor.getColumnIndex(MediaStore.Audio.Albums._ID));
    }

    protected String getAlbumName() {
        return mCursor.getString(mCursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM));
    }

    protected String getArtistId() {
        return mCursor.getString(mCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST_ID));
    }

    protected String getArtistName() {
        return mCursor.getString(mCursor.getColumnIndex(MediaStore.Audio.Albums.ARTIST));
    }

    protected int getNumSongs() {
        return mCursor.getInt(mCursor.getColumnIndex(MediaStore.Audio.Albums.NUMBER_OF_SONGS));
    }

    protected String getAlbumArtId() {
        return mCursor.getString(mCursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART)) != null ? getId() : null;
    }

    protected Album getAlbum() {
        return new Album(getId(), getAlbumName(), getArtistId(), getArtistName(), getAlbumArtId());
    }

    @Override
    Album fetch() {
        return getAlbum();
    }
}
