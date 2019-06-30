package tokyo.tkw.thinmp.provider;

import android.content.Context;
import android.provider.MediaStore;

public class ArtistContentProvider extends MediaStoreAudioArtistsProvider {
    public ArtistContentProvider(Context context, String artistId) {
        super(context);

        selection = MediaStore.Audio.Media._ID + " = ?";
        selectionArgs = new String[]{artistId};
        sortOrder = null;
    }
}
