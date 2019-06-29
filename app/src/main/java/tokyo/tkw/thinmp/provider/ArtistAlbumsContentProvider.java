package tokyo.tkw.thinmp.provider;

import android.content.Context;
import android.provider.MediaStore;

public class ArtistAlbumsContentProvider extends MediaStoreAudioAlbumsProvider {
    public ArtistAlbumsContentProvider(Context context, String artistId) {
        super(context);

        selection = MediaStore.Audio.Media.ARTIST_ID + " = ?";
        selectionArgs = new String[]{artistId};
        sortOrder = MediaStore.Audio.Albums.ALBUM + " ASC";
    }
}
