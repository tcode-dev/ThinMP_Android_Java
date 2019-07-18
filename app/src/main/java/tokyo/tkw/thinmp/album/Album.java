package tokyo.tkw.thinmp.album;

import android.content.Context;

import com.annimon.stream.Optional;

import java.util.List;

import tokyo.tkw.thinmp.music.Music;
import tokyo.tkw.thinmp.track.Track;
import tokyo.tkw.thinmp.provider.AlbumContentProvider;
import tokyo.tkw.thinmp.provider.TrackContentProvider;

/**
 * アルバム情報
 */
public class Album extends Music {
    public static final String ALBUM_ID = "albumId";

    private Context context;
    private String id;
    private String name;
    private String artistId;
    private String artistName;
    private Optional<String> albumArtId;

    public Album(Context context, String id, String name, String artistId, String artistName, Optional<String> albumArtId) {
        this.context = context;
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

    public List<Track> getTrackList() {
        TrackContentProvider provider = new TrackContentProvider(context);

        return provider.findByAlbum(id);
    }
}
