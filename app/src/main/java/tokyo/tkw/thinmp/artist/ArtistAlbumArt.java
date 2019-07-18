package tokyo.tkw.thinmp.artist;

import android.content.Context;

import com.annimon.stream.Optional;

import tokyo.tkw.thinmp.album.Album;
import tokyo.tkw.thinmp.provider.AlbumArtContentProvider;

public class ArtistAlbumArt {
    private String artistId;
    private AlbumArtContentProvider provider;

    private ArtistAlbumArt(Context context, String artistId) {
        this.artistId = artistId;
        this.provider = new AlbumArtContentProvider(context);
    }

    public static ArtistAlbumArt createInstance(Context context, String artistId) {
        return new ArtistAlbumArt(context, artistId);
    }

    public Optional<String> getAlbumArtId() {
        Optional<Album> album = provider.findByArtist(artistId);

        return album.isEmpty() ? Optional.empty() : album.get().getAlbumArtId();
    }
}
