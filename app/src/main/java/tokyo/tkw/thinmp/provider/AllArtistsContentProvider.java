package tokyo.tkw.thinmp.provider;

import android.content.Context;
import android.provider.MediaStore;

public class AllArtistsContentProvider extends MediaStoreAudioArtistsProvider {
    public AllArtistsContentProvider(Context context) {
        super(context);

        selection = null;
        selectionArgs = null;
        sortOrder = MediaStore.Audio.Artists.ARTIST + " ASC";
    }
}
