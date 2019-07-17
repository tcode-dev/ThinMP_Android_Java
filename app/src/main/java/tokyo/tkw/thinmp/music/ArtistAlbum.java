package tokyo.tkw.thinmp.music;

import android.content.Context;

import java.util.List;

import tokyo.tkw.thinmp.provider.AlbumContentProvider;

public class ArtistAlbum {
    private String artistId;
    private AlbumContentProvider provider;

    private ArtistAlbum(Context context, String artistId) {
        this.artistId = artistId;
        this.provider = new AlbumContentProvider(context);
    }

    public static ArtistAlbum createInstance(Context context, String artistId) {
        return new ArtistAlbum(context, artistId);
    }

    public List<Album> getAlbumList() {
        return provider.findByArtist(artistId);
    }
}
