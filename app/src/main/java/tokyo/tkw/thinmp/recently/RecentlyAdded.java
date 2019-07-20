package tokyo.tkw.thinmp.recently;

import android.content.Context;

import java.util.List;

import tokyo.tkw.thinmp.album.Album;
import tokyo.tkw.thinmp.provider.AlbumContentProvider;

public class RecentlyAdded {
    private static final int LIMIT = 10;
    private AlbumContentProvider albumContentProvider;

    private RecentlyAdded(Context context) {
        this.albumContentProvider = new AlbumContentProvider(context);
    }

    public static RecentlyAdded createInstance(Context context) {
        return new RecentlyAdded(context);
    }

    public List<Album> getRecentlyAddedList() {
        return albumContentProvider.findRecentlyAdded(LIMIT);
    }
}
