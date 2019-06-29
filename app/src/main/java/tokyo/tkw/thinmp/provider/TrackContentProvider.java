package tokyo.tkw.thinmp.provider;

import android.content.Context;
import android.provider.MediaStore;

public class TrackContentProvider extends MediaStoreAudioMediaProvider {
    public TrackContentProvider(Context context, String trackId) {
        super(context);

        selection = MediaStore.Audio.Media._ID + " = ?";
        selectionArgs = new String[]{trackId};
        sortOrder = null;
    }
}
