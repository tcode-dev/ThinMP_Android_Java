package tokyo.tkw.thinmp.provider;

import android.content.Context;
import android.provider.MediaStore;

import java.util.ArrayList;

public class TracksContentProvider extends MediaStoreAudioMediaProvider {
    public TracksContentProvider(Context context, ArrayList<String> trackIdList) {
        super(context);

        selection =
                MediaStore.Audio.Media._ID + " IN (" + makePlaceholders(trackIdList.size()) + ") " +
                        "AND " + MediaStore.Audio.Media.IS_MUSIC + " = 1";
        selectionArgs = toStringArray(trackIdList);
        sortOrder = null;
    }
}
