package tokyo.tkw.thinmp.music;

import android.content.Context;

import java.util.ArrayList;

import tokyo.tkw.thinmp.provider.AlbumContentProvider;
import tokyo.tkw.thinmp.provider.AlbumTracksContentProvider;
import tokyo.tkw.thinmp.provider.ArtistContentProvider;

/**
 * アルバム情報
 */
public class Album extends Music {
    public static final String ALBUM_ID = "albumId";

    private Context mContext;
    private String id;
    private String name;
    private String artistName;
    private String thumbnailId;

    public Album(Context context, String id, String name, String artistName, String thumbnailId) {
        this.mContext = context;
        this.id = id;
        this.name = name;
        this.artistName = artistName;
        this.thumbnailId = thumbnailId;
    }

    /**
     * ContentProviderからalbumを取得
     * @param context
     * @param id
     * @return
     */
    public static Album createInstance(Context context, String id) {
        AlbumContentProvider provider = new AlbumContentProvider(context, id);
        return provider.get();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getThumbnailId() {
        return thumbnailId;
    }

    public ArrayList<Track> getTrackList() {
        AlbumTracksContentProvider provider = new AlbumTracksContentProvider(mContext, id);

        return provider.getList();
    }
}
