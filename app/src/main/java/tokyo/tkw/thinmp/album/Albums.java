package tokyo.tkw.thinmp.album;

import android.content.Context;

import java.util.List;

import tokyo.tkw.thinmp.provider.AlbumContentProvider;

public class Albums {
    private AlbumContentProvider provider;

    private Albums(Context context) {
        provider = new AlbumContentProvider(context);
    }

    public static Albums createInstance(Context context) {
        return new Albums(context);
    }

    public List<Album> getAlbumList() {
        return provider.findAll();
    }
}
