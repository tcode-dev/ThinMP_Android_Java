package tokyo.tkw.thinmp.recently;

import android.content.Context;

import java.util.List;

import tokyo.tkw.thinmp.album.Album;
import tokyo.tkw.thinmp.provider.AlbumContentProvider;

public class RecentlyAdded {
    private int count;
    private AlbumContentProvider albumContentProvider;

    private RecentlyAdded(Context context, int count) {
        this.albumContentProvider = new AlbumContentProvider(context);
        this.count = count;
    }

    public static RecentlyAdded createInstance(Context context, int count) {
        return new RecentlyAdded(context, count);
    }

    public List<Album> getRecentlyAddedList() {
        return albumContentProvider.findRecentlyAdded(count);
    }
}
