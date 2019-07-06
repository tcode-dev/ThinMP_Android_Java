package tokyo.tkw.thinmp.provider;

import android.content.Context;
import android.provider.MediaStore;

import java.util.List;

import tokyo.tkw.thinmp.music.Track;

public class TrackContentProvider extends MediaStoreAudioMediaProvider {
    public TrackContentProvider(Context context) {
        super(context);
    }

    public Track findById(String trackId) {
        selection = MediaStore.Audio.Media._ID + " = ?";
        selectionArgs = new String[]{trackId};
        sortOrder = null;

        return get();
    }

    public List<Track> findById(List<String> trackIdList) {
        selection =
                MediaStore.Audio.Media._ID + " IN (" + makePlaceholders(trackIdList.size()) + ") " +
                        "AND " + MediaStore.Audio.Media.IS_MUSIC + " = 1";
        selectionArgs = toStringArray(trackIdList);
        sortOrder = null;

        return getList();
    }

    public List<Track> findByArtist(String artistId) {
        selection =
                MediaStore.Audio.Media.ARTIST_ID + " = ? AND " + MediaStore.Audio.Media.IS_MUSIC + " = 1";
        selectionArgs = new String[]{artistId};
        sortOrder = MediaStore.Audio.Media.ALBUM + " ASC, " + MediaStore.Audio.Media._ID + " ASC";

        return getList();
    }

    public List<Track> findByAlbum(String albumId) {
        selection =
                MediaStore.Audio.Media.ALBUM_ID + " = ? AND " + MediaStore.Audio.Media.IS_MUSIC + " = 1";
        selectionArgs = new String[]{albumId};
        sortOrder = MediaStore.Audio.Media._ID + " ASC";

        return getList();
    }

    public List<Track> findAll() {
        selection = MediaStore.Audio.Media.IS_MUSIC + " = 1";
        selectionArgs = null;
        sortOrder = MediaStore.Audio.Media.ARTIST + " ASC, "
                + MediaStore.Audio.Media.ALBUM + " ASC, "
                + MediaStore.Audio.Media._ID + " ASC";

        return getList();
    }
}
