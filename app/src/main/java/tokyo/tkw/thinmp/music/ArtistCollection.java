package tokyo.tkw.thinmp.music;

import android.content.Context;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.List;
import java.util.Map;

import tokyo.tkw.thinmp.provider.AlbumArtContentProvider;
import tokyo.tkw.thinmp.provider.ArtistContentProvider;

public class ArtistCollection {
    private Context mContext;
    private List<Artist> mArtistList;

    public ArtistCollection(Context context, List<Artist> artistList) {
        mContext = context;
        mArtistList = artistList;
    }

    public List<Artist> getList() {
        return mArtistList;
    }

    public static ArtistCollection createAllArtistCollectionInstance(Context context) {
        ArtistContentProvider provider = new ArtistContentProvider(context);

        return new ArtistCollection(context, provider.findAll());
    }

    public List<Album> getAllArtistAlbumArtList() {
        AlbumArtContentProvider provider = new AlbumArtContentProvider(mContext);

        return provider.findAll();
    }

    public Map<String, String> getAllArtistAlbumArtMap() {
        return toArtistAlbumArtMap(getAllArtistAlbumArtList());
    }

    public Map<String, String> toArtistAlbumArtMap(List<Album> artistAlbumList) {
        return Stream.of(artistAlbumList)
                .distinctBy(Album::getArtistId)
                .collect(Collectors.toMap(Album::getArtistId, Album::getAlbumArtId));
    }
}
