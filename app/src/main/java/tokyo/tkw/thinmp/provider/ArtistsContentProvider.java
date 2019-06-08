package tokyo.tkw.thinmp.provider;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import tokyo.tkw.thinmp.music.Artist;

public class ArtistsContentProvider extends MusicContentProvider<Artist> {
    public ArtistsContentProvider(Context context) {
        super(context);
    }

    @Override
    Cursor createCursor() {
        return mContext.getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                new String[]{
                        "DISTINCT " + MediaStore.Audio.Media.ARTIST_ID,
                        MediaStore.Audio.Media.ARTIST
                },
                MediaStore.Audio.Media.IS_MUSIC + " = 1",
                null,
                MediaStore.Audio.Media.ARTIST + " ASC"
        );
    }

    @Override
    Artist fetch() {
        return getArtist();
    }
}
