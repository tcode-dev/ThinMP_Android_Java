package tokyo.tkw.thinmp.music;

import android.content.Context;

import java.util.ArrayList;

import tokyo.tkw.thinmp.provider.AlbumsContentProvider;
import tokyo.tkw.thinmp.provider.ArtistAlbumsContentProvider;

public class AlbumList {
    private Context mContext;

    public AlbumList(Context context) {
        mContext = context;
    }

    public ArrayList<Album> getAllAlbumList() {
        AlbumsContentProvider provider = new AlbumsContentProvider(mContext);
        return provider.getList();
    }

    public ArrayList<Album> getArtistAlbumList(String artistId) {
        ArtistAlbumsContentProvider provider = new ArtistAlbumsContentProvider(mContext, artistId);

        return provider.getList();
    }
}
