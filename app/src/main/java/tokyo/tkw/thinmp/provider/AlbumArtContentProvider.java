package tokyo.tkw.thinmp.provider;

import android.content.Context;
import android.provider.MediaStore;

import com.annimon.stream.Optional;

import java.util.List;

import tokyo.tkw.thinmp.album.Album;

public class AlbumArtContentProvider extends MediaStoreAudioAlbumsProvider {
    public AlbumArtContentProvider(Context context) {
        super(context);
    }

    public Optional<Album> findByArtist(String artistId) {
        selection = MediaStore.Audio.Media.ARTIST_ID + " = ? AND " + MediaStore.Audio.Albums.ALBUM_ART + " IS NOT NULL";
        selectionArgs = new String[]{artistId};
        sortOrder = MediaStore.Audio.Albums.ALBUM + " ASC";

        return get();
    }

    public List<Album> findById(List<String> albumIdList) {
        selection =
                MediaStore.Audio.Albums._ID + " IN (" + makePlaceholders(albumIdList.size()) + ") " +
                        "AND " + MediaStore.Audio.Albums.ALBUM_ART + " IS NOT NULL";
        selectionArgs = toStringArray(albumIdList);
        sortOrder = null;

        return getList();
    }

    public List<Album> findAll() {
        selection = MediaStore.Audio.Albums.ALBUM_ART + " IS NOT NULL";
        selectionArgs = null;
        sortOrder = MediaStore.Audio.Albums.ALBUM + " ASC";

        return getList();
    }
}
