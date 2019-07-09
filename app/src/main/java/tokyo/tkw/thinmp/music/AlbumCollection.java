package tokyo.tkw.thinmp.music;

import android.content.Context;

import com.annimon.stream.Stream;

import java.util.List;

import tokyo.tkw.thinmp.provider.AlbumContentProvider;

public class AlbumCollection {
    private AlbumContentProvider provider;

    private AlbumCollection(Context context) {
        provider = new AlbumContentProvider(context);
    }

    public static AlbumCollection createInstance(Context context) {
        return new AlbumCollection(context);
    }

    public List<Album> findById(List<String> albumIdList) {
        return provider.findById(albumIdList);
    }

    public List<Album> findByArtist(String artistId) {
        return provider.findByArtist(artistId);
    }

    public List<Album> findAll() {
        return provider.findAll();
    }

    public String findFirstAlbumArtId(List<Album> albumList) {
        return Stream.of(albumList)
                .map(Album::getAlbumArtId)
                .withoutNulls()
                .findFirst()
                .orElse(null);
    }
}
