package tokyo.tkw.thinmp.provider;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import tokyo.tkw.thinmp.music.ArtistAlbumArt;

public class ArtistAlbumArtContentProvider extends MediaStoreAudioProvider<ArtistAlbumArt> {
    public ArtistAlbumArtContentProvider(Context context) {
        super(context);
    }

    @Override
    Cursor createCursor() {
        return mContext.getContentResolver().query(
                MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                new String[]{
                        MediaStore.Audio.Media.ARTIST_ID,
                        MediaStore.Audio.Albums._ID
                },
                MediaStore.Audio.Albums.ALBUM_ART + " IS NOT NULL",
                null,
                MediaStore.Audio.Albums.ALBUM + " ASC"
        );
    }

    private String getArtistId() {
        return mCursor.getString(mCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST_ID));
    }

    private String getAlbumArtId() {
        return mCursor.getString(mCursor.getColumnIndex(MediaStore.Audio.Albums._ID));
    }

    private ArtistAlbumArt getArtistAlbumArt() {
        return new ArtistAlbumArt(getArtistId(), getAlbumArtId());
    }

    @Override
    ArtistAlbumArt fetch() {
        return getArtistAlbumArt();
    }
}
