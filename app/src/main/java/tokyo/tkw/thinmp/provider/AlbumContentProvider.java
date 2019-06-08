package tokyo.tkw.thinmp.provider;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import tokyo.tkw.thinmp.music.Album;

public class AlbumContentProvider extends MusicContentProvider<Album> {
    private String mAlbumId;

    public AlbumContentProvider(Context context, String albumId) {
        super(context);

        mAlbumId = albumId;
    }

    @Override
    Cursor createCursor() {
        return mContext.getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                new String[]{
                        MediaStore.Audio.Media.ALBUM_ID,
                        MediaStore.Audio.Media.ALBUM,
                        MediaStore.Audio.Media.ARTIST_ID,
                        MediaStore.Audio.Media.ARTIST
                },
                MediaStore.Audio.Media.ALBUM_ID + " = " + mAlbumId + " AND " + MediaStore.Audio.Media.IS_MUSIC + " = 1",
                null,
                null
        );
    }

    @Override
    Album fetch() {
        return getAlbum();
    }
}
