package tokyo.tkw.thinmp.logic;

import android.content.Context;

import com.annimon.stream.Optional;

import tokyo.tkw.thinmp.dto.AlbumDetailDto;
import tokyo.tkw.thinmp.music.Album;

public class AlbumDetailLogic {
    private Optional<Album> album;

    private AlbumDetailLogic(Context context, String albumId) {
        album = Album.createInstance(context, albumId);
    }

    public static AlbumDetailLogic createInstance(Context context, String albumId) {
        return new AlbumDetailLogic(context, albumId);
    }

    public AlbumDetailDto createDto() {
        AlbumDetailDto dto = new AlbumDetailDto();

        album.ifPresent(album -> {
            dto.albumName = album.getName();
            dto.artistName = album.getArtistName();
            dto.albumArtId = album.getAlbumArtId();
            dto.trackList = album.getTrackList();
        });

        return dto;
    }
}
