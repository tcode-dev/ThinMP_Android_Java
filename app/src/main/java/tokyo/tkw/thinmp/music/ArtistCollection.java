package tokyo.tkw.thinmp.music;

import android.content.Context;

import java.util.ArrayList;

import tokyo.tkw.thinmp.provider.AllArtistsContentProvider;

public class ArtistCollection {
    private ArrayList<Artist> mArtistList;

    public ArtistCollection(ArrayList<Artist> artistList) {
        mArtistList = artistList;
    }

    public ArrayList<Artist> getList() {
        return mArtistList;
    }

    public static ArtistCollection createAllArtistCollectionInstance(Context context) {
        AllArtistsContentProvider provider = new AllArtistsContentProvider(context);
        return new ArtistCollection(provider.getList());
    }
}
