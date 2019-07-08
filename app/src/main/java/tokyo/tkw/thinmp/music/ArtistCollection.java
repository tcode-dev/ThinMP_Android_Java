package tokyo.tkw.thinmp.music;

import android.content.Context;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.List;
import java.util.Map;

import tokyo.tkw.thinmp.provider.AlbumArtContentProvider;
import tokyo.tkw.thinmp.provider.ArtistContentProvider;

public class ArtistCollection {
    private List<Artist> artistList;
    private Map<String, String> albumArtMap;

    private ArtistCollection(Context context) {
        ArtistContentProvider artistContentProvider = new ArtistContentProvider(context);
        AlbumArtContentProvider albumArtContentProvider = new AlbumArtContentProvider(context);
        this.artistList = artistContentProvider.findAll();
        this.albumArtMap = toArtistAlbumArtMap(albumArtContentProvider.findAll());
    }

    private ArtistCollection(Context context, List<String> artistIdList) {
        ArtistContentProvider artistContentProvider = new ArtistContentProvider(context);
        AlbumArtContentProvider albumArtContentProvider = new AlbumArtContentProvider(context);
        this.artistList = artistContentProvider.findById(artistIdList);
        this.albumArtMap = toArtistAlbumArtMap(albumArtContentProvider.findByArtist(getArtistIdList()));
    }

    public List<Artist> getList() {
        return Stream.of(artistList).map(artist -> {
            artist.setAlbumArtId(albumArtMap.get(artist.getId()));
            return artist;
        }).toList();
    }

    public static ArtistCollection createArtistCollectionInstance(Context context, List<String> artistIdList) {
        return new ArtistCollection(context, artistIdList);
    }

    public static ArtistCollection createAllArtistCollectionInstance(Context context) {
        return new ArtistCollection(context);
    }

    private List<String> getArtistIdList() {
        return Stream.of(artistList).map(Artist::getId).toList();
    }

    private Map<String, String> toArtistAlbumArtMap(List<Album> artistAlbumList) {
        return Stream.of(artistAlbumList)
                .distinctBy(Album::getArtistId)
                .collect(Collectors.toMap(Album::getArtistId, Album::getAlbumArtId));
    }
}
