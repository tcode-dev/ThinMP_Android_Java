package tokyo.tkw.thinmp.provider;

import android.content.Context;
import android.provider.MediaStore;

import java.util.ArrayList;

public class ArtistsContentProvider extends MediaStoreAudioArtistsProvider {
    public ArtistsContentProvider(Context context, ArrayList<String> artistIdList) {
        super(context);

        selection =
                MediaStore.Audio.Artists._ID + " IN (" + makePlaceholders(artistIdList.size()) + ")";
        selectionArgs = toStringArray(artistIdList);
        sortOrder = MediaStore.Audio.Artists.ARTIST + " ASC";
    }
}
