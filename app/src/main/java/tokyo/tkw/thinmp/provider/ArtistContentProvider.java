package tokyo.tkw.thinmp.provider;

import android.content.Context;
import android.provider.MediaStore;

import java.util.List;

import tokyo.tkw.thinmp.music.Artist;

public class ArtistContentProvider extends MediaStoreAudioArtistsProvider {
    public ArtistContentProvider(Context context) {
        super(context);
    }

    public Artist findById(String artistId) {
        selection = MediaStore.Audio.Media._ID + " = ?";
        selectionArgs = new String[]{artistId};
        sortOrder = null;

        return get();
    }

    public List<Artist> findById(List<String> artistIdList) {
        selection =
                MediaStore.Audio.Artists._ID + " IN (" + makePlaceholders(artistIdList.size()) +
                        ")";
        selectionArgs = toStringArray(artistIdList);
        sortOrder = MediaStore.Audio.Artists.ARTIST + " ASC";

        return getList();
    }

    public List<Artist> findAll() {
        selection = null;
        selectionArgs = null;
        sortOrder = MediaStore.Audio.Artists.ARTIST + " ASC";

        return getList();
    }
}
