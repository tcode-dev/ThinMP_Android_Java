package tokyo.tkw.thinmp.music;

import android.content.Context;

import com.annimon.stream.Optional;

import tokyo.tkw.thinmp.provider.AlbumContentProvider;

/**
 * アルバム情報
 */
public class Album extends Music {
    public static final String ALBUM_ID = "albumId";

    private String id;
    private String name;
    private String artistId;
    private String artistName;
    private Optional<String> albumArtId;

    public Album(String id, String name, String artistId, String artistName, Optional<String> albumArtId) {
        this.id = id;
        this.name = name;
        this.artistId = artistId;
        this.artistName = artistName;
        this.albumArtId = albumArtId;
    }

    public static Optional<Album> createInstance(Context context, String id) {
        AlbumContentProvider provider = new AlbumContentProvider(context);

        return provider.findById(id);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getArtistId() {
        return artistId;
    }

    public String getArtistName() {
        return artistName;
    }

    public Optional<String> getAlbumArtId() {
        return albumArtId;
    }
}
