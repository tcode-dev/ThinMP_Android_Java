package tokyo.tkw.thinmp.provider;

import android.content.Context;
import android.provider.MediaStore;

import java.util.ArrayList;

import tokyo.tkw.thinmp.music.Album;

public class AlbumContentProvider extends MediaStoreAudioAlbumsProvider {
    public AlbumContentProvider(Context context) {
        super(context);
    }

    public Album findById(String albumId) {
        selection = MediaStore.Audio.Albums._ID + " = ?";
        selectionArgs = new String[]{albumId};
        sortOrder = null;

        return get();
    }

    public ArrayList<Album> findByArtist(String artistId) {
        selection = MediaStore.Audio.Media.ARTIST_ID + " = ?";
        selectionArgs = new String[]{artistId};
        sortOrder = MediaStore.Audio.Albums.ALBUM + " ASC";

        return getList();
    }

    public ArrayList<Album> findAll() {
        selection = null;
        selectionArgs = null;
        sortOrder = MediaStore.Audio.Albums.ALBUM + " ASC";

        return getList();
    }
}
