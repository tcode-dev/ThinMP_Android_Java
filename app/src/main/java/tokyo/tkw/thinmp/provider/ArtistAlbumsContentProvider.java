package tokyo.tkw.thinmp.provider;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import tokyo.tkw.thinmp.music.Album;

public class ArtistAlbumsContentProvider extends MusicContentProvider<Album> {
    private String mArtistId;

    public ArtistAlbumsContentProvider(Context context, String artistId) {
        super(context);

        mArtistId = artistId;
    }

    @Override
    Cursor createCursor() {
        return mContext.getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                new String[]{
                        "DISTINCT " + MediaStore.Audio.Media.ALBUM_ID,
                        MediaStore.Audio.Media.ALBUM,
                        MediaStore.Audio.Media.ARTIST_ID,
                        MediaStore.Audio.Media.ARTIST
                },
                MediaStore.Audio.Media.ARTIST_ID + " = " + mArtistId + " AND " + MediaStore.Audio.Media.IS_MUSIC + " = 1",
                null,
                MediaStore.Audio.Media.ALBUM + " ASC"
        );
    }

    @Override
    Album fetch() {
        return getAlbum();
    }
}
