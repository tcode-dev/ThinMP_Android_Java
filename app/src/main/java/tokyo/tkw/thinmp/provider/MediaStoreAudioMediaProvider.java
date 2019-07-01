package tokyo.tkw.thinmp.provider;

import android.content.Context;
import android.provider.MediaStore;

import tokyo.tkw.thinmp.music.Track;

abstract class MediaStoreAudioMediaProvider extends MediaStoreAudioProvider<Track> {
    MediaStoreAudioMediaProvider(Context context) {
        super(context);

        uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        projection = new String[]{
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST_ID,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.ALBUM_ID,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.DURATION
        };
    }

    private String getId() {
        return mCursor.getString(mCursor.getColumnIndex(MediaStore.Audio.Media._ID));
    }

    private String getTitle() {
        return mCursor.getString(mCursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
    }

    private String getArtistId() {
        return mCursor.getString(mCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST_ID));
    }

    private String getArtistName() {
        return mCursor.getString(mCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
    }

    private String getAlbumId() {
        return mCursor.getString(mCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
    }

    private String getAlbumName() {
        return mCursor.getString(mCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
    }

    private int getDuration() {
        return mCursor.getInt(mCursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
    }

    private Track getTrack() {
        return new Track(getId(), getTitle(), getArtistId(), getArtistName(),
                getAlbumId(), getAlbumName(), getDuration());
    }

    @Override
    Track fetch() {
        return getTrack();
    }
}
