package tokyo.tkw.thinmp.provider;

import android.content.Context;
import android.provider.MediaStore;

import java.util.ArrayList;

import tokyo.tkw.thinmp.music.Album;

public class AlbumArtContentProvider extends MediaStoreAudioAlbumsProvider {
    public AlbumArtContentProvider(Context context) {
        super(context);
    }

    public ArrayList<Album> findByArtist(ArrayList<String> artistIdList) {
        selection =
                MediaStore.Audio.Media.ARTIST_ID + " IN (" + makePlaceholders(artistIdList.size()) + ") AND " +
                        MediaStore.Audio.Albums.ALBUM_ART + " IS NOT NULL";
        selectionArgs = toStringArray(artistIdList);
        sortOrder = MediaStore.Audio.Albums.ALBUM + " ASC";

        return getList();
    }

    public ArrayList<Album> findAll() {
        selection = MediaStore.Audio.Albums.ALBUM_ART + " IS NOT NULL";
        selectionArgs = null;
        sortOrder = MediaStore.Audio.Albums.ALBUM + " ASC";

        return getList();
    }
}
