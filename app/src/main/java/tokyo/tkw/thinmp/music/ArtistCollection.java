package tokyo.tkw.thinmp.music;

import android.content.Context;

import java.util.List;

import tokyo.tkw.thinmp.provider.ArtistContentProvider;

public class ArtistCollection {
    private List<Artist> artistList;

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
}
