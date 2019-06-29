package tokyo.tkw.thinmp.provider;

import android.content.Context;
import android.provider.MediaStore;

public class ArtistContentProvider extends MediaStoreAudioArtistsProvider {
    public ArtistContentProvider(Context context, String artistId) {
        super(context);

        projection = new String[]{
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Artists.ARTIST,
                MediaStore.Audio.Artists.NUMBER_OF_ALBUMS,
                MediaStore.Audio.Artists.NUMBER_OF_TRACKS
        };
        selection = MediaStore.Audio.Media._ID + " = ?";
        selectionArgs = new String[]{artistId};
        sortOrder = null;
    }
}
