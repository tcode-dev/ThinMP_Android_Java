package tokyo.tkw.thinmp.artist;

import android.content.Context;

import com.annimon.stream.Collectors;
import com.annimon.stream.Optional;
import com.annimon.stream.Stream;

import java.util.List;
import java.util.Map;

import tokyo.tkw.thinmp.album.Album;
import tokyo.tkw.thinmp.provider.AlbumArtContentProvider;

public class ArtistAlbumArt {
    private String artistId;
    private AlbumArtContentProvider provider;

    private ArtistAlbumArt(Context context, String artistId) {
        this.artistId = artistId;
        this.provider = new AlbumArtContentProvider(context);
    }

    private ArtistAlbumArt(Context context) {
        this.provider = new AlbumArtContentProvider(context);
    }

    public static ArtistAlbumArt createInstance(Context context) {
        return new ArtistAlbumArt(context);
    }

    public static ArtistAlbumArt createInstance(Context context, String artistId) {
        return new ArtistAlbumArt(context, artistId);
    }

    public String getAlbumArtId() {
        Optional<Album> album = provider.findByArtist(artistId);

        if (album.isEmpty()) return null;

        return album.get().getAlbumArtId();
    }

    public List<Artist> mapAlbumArt(List<Artist> artistList) {
        Map<String, String> albumMap = getAlbumMap();

        return Stream.of(artistList).map(artist -> {
            artist.setAlbumArtId(albumMap.get(artist.getId()));
            return artist;
        }).toList();
    }

    private Map<String, String> getAlbumMap() {
        return Stream.of(provider.findAll())
                .distinctBy(Album::getArtistId)
                .collect(Collectors.toMap(Album::getArtistId, Album::getAlbumArtId));
    }
}
