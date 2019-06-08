package tokyo.tkw.thinmp.provider;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import tokyo.tkw.thinmp.music.Artist;

public class ArtistContentProvider extends MusicContentProvider<Artist> {
    private String mArtistId;

    public ArtistContentProvider(Context context, String artistId) {
        super(context);

        mArtistId = artistId;
    }

    @Override
    Cursor createCursor() {
        return mContext.getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                new String[]{
                        MediaStore.Audio.Media.ARTIST_ID,
                        MediaStore.Audio.Media.ARTIST
                },
                MediaStore.Audio.Media.ARTIST_ID + " = " + mArtistId + " AND " + MediaStore.Audio.Media.IS_MUSIC + " = 1",
                null,
                null
        );
    }

    @Override
    Artist fetch() {
        return getArtist();
    }
}
