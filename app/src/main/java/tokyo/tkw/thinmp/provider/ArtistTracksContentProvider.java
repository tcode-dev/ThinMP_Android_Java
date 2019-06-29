package tokyo.tkw.thinmp.provider;

import android.content.Context;
import android.provider.MediaStore;

public class ArtistTracksContentProvider extends MediaStoreAudioMediaProvider {
    public ArtistTracksContentProvider(Context context, String artistId) {
        super(context);

        selection =
                MediaStore.Audio.Media.ARTIST_ID + " = ? AND " + MediaStore.Audio.Media.IS_MUSIC + " = 1";
        selectionArgs = new String[]{artistId};
        sortOrder = MediaStore.Audio.Media.ALBUM + " ASC, " + MediaStore.Audio.Media._ID + " ASC";
    }
}
