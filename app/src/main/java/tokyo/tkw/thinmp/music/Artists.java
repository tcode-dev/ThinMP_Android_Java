package tokyo.tkw.thinmp.music;

import android.content.Context;

import java.util.List;

import tokyo.tkw.thinmp.provider.ArtistContentProvider;

public class Artists {
    private ArtistContentProvider provider;

    private Artists(Context context) {
        provider = new ArtistContentProvider(context);
    }

    public static Artists createInstance(Context context) {
        return new Artists(context);
    }

    public List<Artist> getArtistList() {
        return provider.findAll();
    }
}
