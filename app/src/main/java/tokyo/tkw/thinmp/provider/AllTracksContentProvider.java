package tokyo.tkw.thinmp.provider;

import android.content.Context;
import android.provider.MediaStore;

public class AllTracksContentProvider extends MediaStoreAudioMediaProvider {
    public AllTracksContentProvider(Context context) {
        super(context);

        selection = MediaStore.Audio.Media.IS_MUSIC + " = 1";
        selectionArgs = null;
        sortOrder = MediaStore.Audio.Media.ARTIST + " ASC, "
                + MediaStore.Audio.Media.ALBUM + " ASC, "
                + MediaStore.Audio.Media._ID + " ASC";
    }

}
