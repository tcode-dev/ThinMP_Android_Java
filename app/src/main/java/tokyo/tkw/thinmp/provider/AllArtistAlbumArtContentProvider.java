package tokyo.tkw.thinmp.provider;

import android.content.Context;
import android.provider.MediaStore;

import tokyo.tkw.thinmp.music.Album;

public class AllArtistAlbumArtContentProvider extends MediaStoreAudioAlbumsProvider {
    public AllArtistAlbumArtContentProvider(Context context) {
        super(context);

        selection = MediaStore.Audio.Albums.ALBUM_ART + " IS NOT NULL";
        selectionArgs = null;
        sortOrder = MediaStore.Audio.Albums.ALBUM + " ASC";
    }
}
