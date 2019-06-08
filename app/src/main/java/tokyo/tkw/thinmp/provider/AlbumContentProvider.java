package tokyo.tkw.thinmp.provider;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import tokyo.tkw.thinmp.music.Album;

public class AlbumContentProvider extends MusicContentProvider<Album> {
    public AlbumContentProvider(Context context) {
        super(context);
    }

    @Override
    Cursor createCursor() {
        return mContext.getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                new String[]{
                        "Distinct " + MediaStore.Audio.Media.ALBUM_ID,
                        MediaStore.Audio.Media.ALBUM,
                        MediaStore.Audio.Media.ARTIST_ID,
                        MediaStore.Audio.Media.ARTIST},
                MediaStore.Audio.Media.IS_MUSIC + " = 1",
                null,
                null
        );
    }

    @Override
    Album fetch() {
        return new Album(getAlbumId(), getAlbumName(), getArtistId(), getArtistName(), getAlbumId());
    }
}
