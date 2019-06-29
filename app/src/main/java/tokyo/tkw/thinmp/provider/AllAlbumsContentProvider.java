package tokyo.tkw.thinmp.provider;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

public class AllAlbumsContentProvider extends MediaStoreAudioAlbumsProvider {
    public AllAlbumsContentProvider(Context context) {
        super(context);

        selection = null;
        selectionArgs = null;
        sortOrder = MediaStore.Audio.Albums.ALBUM + " ASC";
    }
}
