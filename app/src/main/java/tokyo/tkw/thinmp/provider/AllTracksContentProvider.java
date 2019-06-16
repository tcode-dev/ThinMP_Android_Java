package tokyo.tkw.thinmp.provider;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

public class AllTracksContentProvider extends MediaStoreAudioMediaProvider {
    public AllTracksContentProvider(Context context) {
        super(context);
    }

    @Override
    Cursor createCursor() {
        return mContext.getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                new String[]{
                        MediaStore.Audio.Media._ID,
                        MediaStore.Audio.Media.TITLE,
                        MediaStore.Audio.Media.ARTIST_ID,
                        MediaStore.Audio.Media.ARTIST,
                        MediaStore.Audio.Media.ALBUM_ID,
                        MediaStore.Audio.Media.ALBUM,
                        MediaStore.Audio.Media.DURATION
                },
                MediaStore.Audio.Media.IS_MUSIC + " = 1",
                null,
                MediaStore.Audio.Media.ARTIST + " ASC, "
                        + MediaStore.Audio.Media.ALBUM + " ASC, "
                        + MediaStore.Audio.Media._ID + " ASC"
        );
    }
}