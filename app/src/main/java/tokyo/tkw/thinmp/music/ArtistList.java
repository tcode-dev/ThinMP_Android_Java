package tokyo.tkw.thinmp.music;

import android.content.Context;

import java.util.ArrayList;

import tokyo.tkw.thinmp.epoxy.controller.ArtistsController;
import tokyo.tkw.thinmp.provider.AllArtistsContentProvider;

public class ArtistList {
    private Context mContext;

    public ArtistList(Context context) {
        mContext = context;
    }

    public ArrayList<Artist> getAllArtistList() {
        AllArtistsContentProvider provider = new AllArtistsContentProvider(mContext);

        return provider.getList();
    }
}
