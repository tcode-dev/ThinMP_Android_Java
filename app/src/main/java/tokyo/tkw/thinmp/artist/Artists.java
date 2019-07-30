package tokyo.tkw.thinmp.artist;

import android.content.Context;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.List;
import java.util.Map;

import tokyo.tkw.thinmp.album.Album;
import tokyo.tkw.thinmp.provider.AlbumArtContentProvider;
import tokyo.tkw.thinmp.provider.ArtistContentProvider;

public class Artists {
    private ArtistContentProvider artistContentProvider;
    private AlbumArtContentProvider albumArtContentProvider;

    private Artists(Context context) {
        artistContentProvider = new ArtistContentProvider(context);
        albumArtContentProvider = new AlbumArtContentProvider(context);
    }

    public static Artists createInstance(Context context) {
        return new Artists(context);
    }

    public List<Artist> getArtistList() {
        List<Artist> artistList = artistContentProvider.findAll();
        List<Album> albumList = albumArtContentProvider.findAll();
        Map<String, String> albumMap = getAlbumMap(albumList);

        return mapAlbumArt(artistList, albumMap);
    }

    private Map<String, String> getAlbumMap(List<Album> albumList) {
        return Stream.of(albumList)
                .distinctBy(Album::getArtistId)
                .collect(Collectors.toMap(Album::getArtistId, Album::getAlbumArtId));
    }

    private List<Artist> mapAlbumArt(List<Artist> artistList, Map<String, String> albumMap) {
        return Stream.of(artistList).map(artist -> {
            artist.setAlbumArtId(albumMap.get(artist.getId()));
            return artist;
        }).toList();
    }
}
