package tokyo.tkw.thinmp.logic;

import android.content.Context;

import com.annimon.stream.Optional;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.dto.ArtistDetailDto;
import tokyo.tkw.thinmp.artist.Artist;

public class ArtistDetailLogic {
    private Context context;
    private Optional<Artist> artist;

    private ArtistDetailLogic(Context context, String artistId) {
        this.context = context;
        this.artist = Artist.createInstance(context, artistId);
    }

    public static ArtistDetailLogic createInstance(Context context, String artistId) {
        return new ArtistDetailLogic(context, artistId);
    }

    public ArtistDetailDto createDto() {
        ArtistDetailDto dto = new ArtistDetailDto();

        artist.ifPresent(artist -> {
            dto.artistName = artist.getName();
            dto.albumArtId = artist.getAlbumArtId();
            dto.albumList = artist.getAlbumList();
            dto.trackList = artist.getTrackList();
        });

        dto.albumsTitle = context.getResources().getString(R.string.albums);
        dto.songsTitle = context.getResources().getString(R.string.songs);
        dto.titleSpanSize = 2;
        dto.albumListSpanSize = 1;
        dto.trackListSpanSize = 2;

        return dto;
    }
}
