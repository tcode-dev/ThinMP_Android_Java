package tokyo.tkw.thinmp.provider;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

public class ArtistAlbumsContentProvider extends MediaStoreAudioAlbumsProvider {
    private String mArtistId;

    public ArtistAlbumsContentProvider(Context context, String artistId) {
        super(context);

        mArtistId = artistId;
    }

    @Override
    Cursor createCursor() {
        return mContext.getContentResolver().query(
                MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                new String[]{
                        MediaStore.Audio.Albums._ID,
                        MediaStore.Audio.Albums.ALBUM,
                        MediaStore.Audio.Albums.ARTIST,
                        MediaStore.Audio.Albums.ALBUM_ART
                },
                MediaStore.Audio.Media.ARTIST_ID + " = ?",
                new String[]{
                        mArtistId
                },
                MediaStore.Audio.Albums.ALBUM + " ASC"
        );
    }
}
