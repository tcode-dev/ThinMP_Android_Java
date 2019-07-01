package tokyo.tkw.thinmp.music;

import android.content.Context;

import java.util.ArrayList;

import tokyo.tkw.thinmp.provider.ArtistContentProvider;

/**
 * Artist
 */
public class Artist extends Music {
    public static final String ARTIST_ID = "artistId";

    private String id;
    private String name;
    private String albumArtId;

    public Artist(String id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * ContentProviderからartistを取得
     *
     * @param context
     * @param id
     * @return
     */
    public static Artist createInstance(Context context, String id) {
        ArtistContentProvider provider = new ArtistContentProvider(context);

        return provider.findById(id);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAlbumArtId() {
        return albumArtId;
    }

    public void setAlbumArtId(String albumArtId) {
        this.albumArtId = albumArtId;
    }
}
