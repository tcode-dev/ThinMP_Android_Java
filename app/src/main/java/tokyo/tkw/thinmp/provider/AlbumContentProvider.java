package tokyo.tkw.thinmp.provider;

import android.content.Context;
import android.provider.MediaStore;

public class AlbumContentProvider extends MediaStoreAudioAlbumsProvider {
    public AlbumContentProvider(Context context, String albumId) {
        super(context);

        selection = MediaStore.Audio.Albums._ID + " = ?";
        selectionArgs = new String[]{albumId};
        sortOrder = null;
    }
}
