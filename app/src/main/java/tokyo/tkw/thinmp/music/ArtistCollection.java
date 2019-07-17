package tokyo.tkw.thinmp.music;

import android.content.Context;

import com.annimon.stream.Collectors;
import com.annimon.stream.Optional;
import com.annimon.stream.Stream;

import java.util.List;
import java.util.Map;

import tokyo.tkw.thinmp.provider.AlbumArtContentProvider;
import tokyo.tkw.thinmp.provider.ArtistContentProvider;

public class ArtistCollection {
    private List<Artist> artistList;

    private ArtistCollection(Context context) {
        ArtistContentProvider artistContentProvider = new ArtistContentProvider(context);
        this.artistList = artistContentProvider.findAll();
    }

    private ArtistCollection(Context context, List<String> artistIdList) {
        ArtistContentProvider artistContentProvider = new ArtistContentProvider(context);
        this.artistList = artistContentProvider.findById(artistIdList);
    }

    public List<Artist> getList() {
        return artistList;
    }

    public static ArtistCollection createArtistCollectionInstance(Context context, List<String> artistIdList) {
        return new ArtistCollection(context, artistIdList);
    }

    public static ArtistCollection createAllArtistCollectionInstance(Context context) {
        return new ArtistCollection(context);
    }
}
