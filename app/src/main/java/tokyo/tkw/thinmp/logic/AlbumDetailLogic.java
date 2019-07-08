package tokyo.tkw.thinmp.logic;

import android.content.Context;

import tokyo.tkw.thinmp.dto.AlbumDetailDto;
import tokyo.tkw.thinmp.music.Album;
import tokyo.tkw.thinmp.music.TrackCollection;

public class AlbumDetailLogic {
    private Album album;
    private TrackCollection trackCollection;

    private AlbumDetailLogic(Context context, String albumId) {
        album = Album.createInstance(context, albumId);
        trackCollection = TrackCollection.createAlbumTrackCollectionInstance(context, albumId);
    }

    public static AlbumDetailLogic createInstance(Context context, String albumId) {
        return new AlbumDetailLogic(context, albumId);
    }

    public AlbumDetailDto createDto() {
        AlbumDetailDto dto = new AlbumDetailDto();

        dto.albumName = album.getName();
        dto.artistName = album.getArtistName();
        dto.albumArtId = album.getAlbumArtId();
        dto.trackList = trackCollection.getList();

        return dto;
    }
}
