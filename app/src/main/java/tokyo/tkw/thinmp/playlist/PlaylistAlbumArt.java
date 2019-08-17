package tokyo.tkw.thinmp.playlist;

import android.content.Context;

import com.annimon.stream.Stream;

import java.util.List;

import tokyo.tkw.thinmp.album.Album;
import tokyo.tkw.thinmp.provider.AlbumArtContentProvider;

public class PlaylistAlbumArt {
    private AlbumArtContentProvider provider;

    private PlaylistAlbumArt(Context context) {
        this.provider = new AlbumArtContentProvider(context);
    }

    public static PlaylistAlbumArt createInstance(Context context) {
        return new PlaylistAlbumArt(context);
    }

    public List<String> getAlbumArtList() {
        return Stream.of(provider.findAll())
                .map(Album::getAlbumArtId)
                .toList();
    }
}
