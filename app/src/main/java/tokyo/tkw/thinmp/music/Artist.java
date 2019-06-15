package tokyo.tkw.thinmp.music;

import android.content.Context;

import java.util.ArrayList;

import tokyo.tkw.thinmp.provider.ArtistAlbumsContentProvider;
import tokyo.tkw.thinmp.provider.ArtistContentProvider;
import tokyo.tkw.thinmp.provider.ArtistTracksContentProvider;

/**
 * Artist
 */
public class Artist extends Music {
    public static final String ARTIST_ID = "artistId";

    private Context mContext;
    private String id;
    private String name;
    private ArrayList<String> albumIdList = new ArrayList<>();

    public Artist(Context context, String id, String name) {
        this.mContext = context;
        this.id = id;
        this.name = name;
    }

    /**
     * ContentProviderからartistを取得
     * @param context
     * @param id
     * @return
     */
    public static Artist createInstance(Context context, String id) {
        ArtistContentProvider artistContentProvider = new ArtistContentProvider(context, id);

        return artistContentProvider.get();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getThumbnailIdList() {
        return albumIdList;
    }

    public ArrayList<Album> getAlbumList() {
        ArtistAlbumsContentProvider provider =
                new ArtistAlbumsContentProvider(mContext, id);

        return provider.getList();
    }

    public ArrayList<Track> getTrackList() {
        ArtistTracksContentProvider provider =
                new ArtistTracksContentProvider(mContext, id);

        return provider.getList();
    }
}
