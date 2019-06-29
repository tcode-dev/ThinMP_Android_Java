package tokyo.tkw.thinmp.provider;

import android.content.Context;
import android.provider.MediaStore;

public class AlbumTracksContentProvider extends MediaStoreAudioMediaProvider {
    public AlbumTracksContentProvider(Context context, String albumId) {
        super(context);

        selection =
                MediaStore.Audio.Media.ALBUM_ID + " = ? AND " + MediaStore.Audio.Media.IS_MUSIC + " = 1";
        selectionArgs = new String[]{albumId};
        sortOrder = MediaStore.Audio.Media._ID + " ASC";
    }
}
