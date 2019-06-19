package tokyo.tkw.thinmp.music;

import android.content.Context;

import com.annimon.stream.Stream;

import java.util.ArrayList;

import tokyo.tkw.thinmp.provider.AlbumContentProvider;
import tokyo.tkw.thinmp.provider.AllAlbumsContentProvider;
import tokyo.tkw.thinmp.provider.ArtistAlbumsContentProvider;

public class AlbumCollection {
    private ArrayList<Album> mAlbumList;

    private AlbumCollection(ArrayList<Album> albumList) {
        mAlbumList = albumList;
    }

    /**
     * すべてのアルバムを取得
     *
     * @param context
     * @return
     */
    public static AlbumCollection createAllAlbumCollectionInstance(Context context) {
        AllAlbumsContentProvider provider = new AllAlbumsContentProvider(context);
        return new AlbumCollection(provider.getList());
    }

    /**
     * アーティストのすべてのアルバムを取得
     *
     * @param context
     * @param artistId
     * @return
     */
    public static AlbumCollection createArtistAlbumCollectionInstance(Context context,
                                                                      String artistId) {
        ArtistAlbumsContentProvider provider = new ArtistAlbumsContentProvider(context, artistId);
        return new AlbumCollection(provider.getList());
    }

    /**
     * getList
     *
     * @return
     */
    public ArrayList<Album> getList() {
        return mAlbumList;
    }

    /**
     * AlbumArtIdを取得
     *
     * @return
     */
    public String getAlbumArtId() {
        return Stream.of(mAlbumList)
                .map(album -> album.getAlbumArtId())
                .withoutNulls()
                .findFirst()
                .orElse(null);
    }
}
