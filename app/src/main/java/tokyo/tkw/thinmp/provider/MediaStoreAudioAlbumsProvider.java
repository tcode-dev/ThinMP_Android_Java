package tokyo.tkw.thinmp.provider;

import android.content.Context;
import android.provider.MediaStore;

import tokyo.tkw.thinmp.music.Album;

public abstract class MediaStoreAudioAlbumsProvider extends MediaStoreAudioProvider<Album> {
    public MediaStoreAudioAlbumsProvider(Context context) {
        super(context);
    }

    private String getId() {
        return mCursor.getString(mCursor.getColumnIndex(MediaStore.Audio.Albums._ID));
    }

    protected String getAlbumName() {
        return mCursor.getString(mCursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM));
    }

    protected String getArtistName() {
        return mCursor.getString(mCursor.getColumnIndex(MediaStore.Audio.Albums.ARTIST));
    }

    protected int getNumSongs() {
        return mCursor.getInt(mCursor.getColumnIndex(MediaStore.Audio.Albums.NUMBER_OF_SONGS));
    }

    protected String getThumbnailId() {
        return mCursor.getString(mCursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART)) != null ? getId() : null;
    }

    protected Album getAlbum() {
        return new Album(mContext, getId(), getAlbumName(), getArtistName(), getThumbnailId());
    }

    @Override
    Album fetch() {
        return getAlbum();
    }
}
