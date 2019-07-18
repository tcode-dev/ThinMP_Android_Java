package tokyo.tkw.thinmp.music;

import android.content.Context;

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

    public List<Album> findAll() {
        return provider.findAll();
    }
}
