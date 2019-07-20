package tokyo.tkw.thinmp.provider;

import android.content.Context;
import android.provider.MediaStore;

import tokyo.tkw.thinmp.artist.Artist;

abstract class MediaStoreAudioArtistsProvider extends MediaStoreAudioProvider<Artist> {
    MediaStoreAudioArtistsProvider(Context context) {
        super(context);

        uri = MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI;
        projection = new String[]{
                MediaStore.Audio.Artists._ID,
                MediaStore.Audio.Artists.ARTIST,
                MediaStore.Audio.Artists.NUMBER_OF_ALBUMS,
                MediaStore.Audio.Artists.NUMBER_OF_TRACKS
        };
    }

    private String getId() {
        return mCursor.getString(mCursor.getColumnIndex(MediaStore.Audio.Artists._ID));
    }

    private String getArtistName() {
        return mCursor.getString(mCursor.getColumnIndex(MediaStore.Audio.Artists.ARTIST));
    }

    private String getNumberOfAlbums() {
        return mCursor.getString(mCursor.getColumnIndex(MediaStore.Audio.Artists.NUMBER_OF_ALBUMS));
    }

    private String getNumberOfTracks() {
        return mCursor.getString(mCursor.getColumnIndex(MediaStore.Audio.Artists.NUMBER_OF_TRACKS));
    }

    private Artist getArtist() {
        return new Artist(mContext, getId(), getArtistName(), getNumberOfAlbums(), getNumberOfTracks());
    }

    @Override
    Artist fetch() {
        return getArtist();
    }
}
