package tokyo.tkw.thinmp.provider;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

public class AllArtistsContentProvider extends MediaStoreAudioArtistsProvider {
    public AllArtistsContentProvider(Context context) {
        super(context);
    }

    @Override
    Cursor createCursor() {
        return mContext.getContentResolver().query(
                MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI,
                new String[]{
                        MediaStore.Audio.Artists._ID,
                        MediaStore.Audio.Artists.ARTIST
                },
                null,
                null,
                MediaStore.Audio.Artists.ARTIST + " ASC"
        );
    }
}
