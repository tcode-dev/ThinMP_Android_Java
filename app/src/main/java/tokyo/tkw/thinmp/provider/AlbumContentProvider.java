package tokyo.tkw.thinmp.provider;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

public class AlbumContentProvider extends MediaStoreAudioAlbumsProvider {
    private String mAlbumId;

    public AlbumContentProvider(Context context, String albumId) {
        super(context);

        mAlbumId = albumId;
    }

    @Override
    Cursor createCursor() {
        return mContext.getContentResolver().query(
                MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                new String[]{
                        MediaStore.Audio.Albums._ID,
                        MediaStore.Audio.Albums.ALBUM,
                        MediaStore.Audio.Media.ARTIST_ID,
                        MediaStore.Audio.Albums.ARTIST,
                        MediaStore.Audio.Albums.ALBUM_ART
                },
                MediaStore.Audio.Albums._ID + " = ?",
                new String[]{
                        mAlbumId
                },
                null
        );
    }
}
