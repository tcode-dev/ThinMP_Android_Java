package tokyo.tkw.thinmp.provider;

import android.content.Context;
import android.provider.MediaStore;

import tokyo.tkw.thinmp.music.Artist;

public abstract class MediaStoreAudioArtistsProvider extends MediaStoreAudioProvider<Artist> {
    public MediaStoreAudioArtistsProvider(Context context) {
        super(context);
    }

    private String getId() {
        return mCursor.getString(mCursor.getColumnIndex(MediaStore.Audio.Artists._ID));
    }

    private String getArtistName() {
        return mCursor.getString(mCursor.getColumnIndex(MediaStore.Audio.Artists.ARTIST));
    }

    protected String getNumberOfAlbums() {
        return mCursor.getString(mCursor.getColumnIndex(MediaStore.Audio.Artists.NUMBER_OF_ALBUMS));
    }

    protected String getNumberOfTracks() {
        return mCursor.getString(mCursor.getColumnIndex(MediaStore.Audio.Artists.NUMBER_OF_TRACKS));
    }

    private Artist getArtist() {
        return new Artist(getId(), getArtistName());
    }

    @Override
    Artist fetch() {
        return getArtist();
    }
}
