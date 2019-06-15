package tokyo.tkw.thinmp.provider;

import android.content.Context;
import android.provider.MediaStore;

import tokyo.tkw.thinmp.music.Track;

public abstract class MediaStoreAudioMediaProvider extends MediaStoreAudioProvider<Track> {
    public MediaStoreAudioMediaProvider(Context context) {
        super(context);
    }

    private String getId() {
        return mCursor.getString(mCursor.getColumnIndex(MediaStore.Audio.Media._ID));
    }

    protected String getTitle() {
        return mCursor.getString(mCursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
    }

    protected String getArtistId() {
        return mCursor.getString(mCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST_ID));
    }

    protected String getArtistName() {
        return mCursor.getString(mCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
    }

    protected String getAlbumId() {
        return mCursor.getString(mCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
    }

    protected String getAlbumName() {
        return mCursor.getString(mCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
    }

    protected int getDuration() {
        return mCursor.getInt(mCursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
    }

    protected Track getTrack() {
        return new Track(getId(), getTitle(), getArtistId(), getArtistName(),
                getAlbumId(), getAlbumName(), getDuration());
    }

    @Override
    Track fetch() {
        return getTrack();
    }
}
