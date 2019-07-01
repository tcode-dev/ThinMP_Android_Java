package tokyo.tkw.thinmp.music;

import android.content.Context;

import com.annimon.stream.Stream;

import java.util.ArrayList;

import tokyo.tkw.thinmp.provider.AlbumContentProvider;

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
        AlbumContentProvider provider = new AlbumContentProvider(context);

        return new AlbumCollection(provider.findAll());
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
        AlbumContentProvider provider = new AlbumContentProvider(context);

        return new AlbumCollection(provider.findByArtist(artistId));
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
