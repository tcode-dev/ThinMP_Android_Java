package tokyo.tkw.thinmp.music;

import android.content.Context;

import com.annimon.stream.Stream;

import java.util.List;

import tokyo.tkw.thinmp.provider.AlbumContentProvider;

public class AlbumCollection {
    private List<Album> mAlbumList;

    private AlbumCollection(List<Album> albumList) {
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
     * 指定されたアルバムを取得
     *
     * @param context
     * @param albumIdList
     * @return
     */
    public static AlbumCollection createAlbumCollectionInstance(Context context,
                                                                List<String> albumIdList) {
        AlbumContentProvider provider = new AlbumContentProvider(context);

        return new AlbumCollection(provider.findById(albumIdList));
    }

    /**
     * getList
     *
     * @return
     */
    public List<Album> getList() {
        return mAlbumList;
    }

    /**
     * AlbumArtIdを取得
     *
     * @return
     */
    public String getAlbumArtId() {
        return Stream.of(mAlbumList)
                .map(Album::getAlbumArtId)
                .withoutNulls()
                .findFirst()
                .orElse(null);
    }
}
