package tokyo.tkw.thinmp.provider;

import android.content.Context;
import android.provider.MediaStore;

import com.annimon.stream.Optional;

import java.util.List;

import tokyo.tkw.thinmp.music.Album;

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

    public List<Album> findByArtist(List<String> artistIdList) {
        selection =
                MediaStore.Audio.Media.ARTIST_ID + " IN (" + makePlaceholders(artistIdList.size()) + ") AND " +
                        MediaStore.Audio.Albums.ALBUM_ART + " IS NOT NULL";
        selectionArgs = toStringArray(artistIdList);
        sortOrder = null;

        return getList();
    }

    public List<Album> findById(List<String> albumIdList) {
        selection =
                MediaStore.Audio.Albums._ID + " IN (" + makePlaceholders(albumIdList.size()) + ") " +
                        "AND " + MediaStore.Audio.Albums.ALBUM_ART + " IS NOT NULL";
        selectionArgs = toStringArray(albumIdList);
        sortOrder = null;

        return getList();
    }

    /**
     * NOTE:uriからテーブル名を取得する方法がわからないので副問合せのテーブル名はハードコーディングする
     * @param trackIdList
     * @return
     */
    public List<Album> findByTrack(List<String> trackIdList) {
        selection = MediaStore.Audio.Albums.ALBUM_ART + " IS NOT NULL " +
                "AND " + MediaStore.Audio.Albums._ID  + " IN (" +
                    "SELECT " + MediaStore.Audio.Media.ALBUM_ID + " " +
                    "FROM audio " +
                    "WHERE " + MediaStore.Audio.Media._ID + " IN ("+ makePlaceholders(trackIdList.size()) + ")" +
                ")";
        selectionArgs = toStringArray(trackIdList);
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
