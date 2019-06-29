package tokyo.tkw.thinmp.provider;

import android.content.Context;
import android.provider.MediaStore;

import java.util.ArrayList;

public class ArtistsAlbumArtContentProvider extends MediaStoreAudioAlbumsProvider {
    public ArtistsAlbumArtContentProvider(Context context, ArrayList<String> artistIdList) {
        super(context);

        selection =
                MediaStore.Audio.Media.ARTIST_ID + " IN (" + makePlaceholders(artistIdList.size()) + ") AND " +
                        MediaStore.Audio.Albums.ALBUM_ART + " IS NOT NULL";
        selectionArgs = toStringArray(artistIdList);
        sortOrder = MediaStore.Audio.Albums.ALBUM + " ASC";
    }
}
