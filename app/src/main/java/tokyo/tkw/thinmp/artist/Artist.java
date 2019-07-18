package tokyo.tkw.thinmp.artist;

import android.content.Context;

import com.annimon.stream.Optional;

import java.util.List;

import tokyo.tkw.thinmp.album.Album;
import tokyo.tkw.thinmp.music.Music;
import tokyo.tkw.thinmp.track.Track;
import tokyo.tkw.thinmp.provider.ArtistContentProvider;

/**
 * Artist
 */
public class Artist extends Music {
    public static final String ARTIST_ID = "artistId";

    private Context context;
    private String id;
    private String name;

    public Artist(Context context, String id, String name) {
        this.context = context;
        this.id = id;
        this.name = name;
    }

    public static Optional<Artist> createInstance(Context context, String id) {
        ArtistContentProvider provider = new ArtistContentProvider(context);

        return provider.findById(id);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Optional<String> getAlbumArtId() {
        ArtistAlbumArt artistAlbumArt = ArtistAlbumArt.createInstance(context, id);

        return artistAlbumArt.getAlbumArtId();
    }

    public List<Album> getAlbumList() {
        ArtistAlbum artistAlbum = ArtistAlbum.createInstance(context, id);

        return artistAlbum.getAlbumList();
    }

    public List<Track> getTrackList() {
        ArtistTrack artistTrack = ArtistTrack.createInstance(context, id);

        return artistTrack.getTrackList();
    }
}
