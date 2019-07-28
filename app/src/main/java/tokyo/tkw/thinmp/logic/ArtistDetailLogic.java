package tokyo.tkw.thinmp.logic;

import android.content.Context;

import com.annimon.stream.Optional;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.artist.Artist;
import tokyo.tkw.thinmp.constant.LayoutSpanSizeEnum;
import tokyo.tkw.thinmp.dto.ArtistDetailDto;

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

    public Optional<ArtistDetailDto> createDto() {
        return artist.map(artist -> {
            ArtistDetailDto dto = new ArtistDetailDto();

            dto.artistName = artist.getName();
            dto.albumArtId = artist.getAlbumArtId();
            dto.meta = artist.getMeta();
            dto.albumList = artist.getAlbumList();
            dto.trackList = artist.getTrackList();
            dto.albumsTitle = context.getResources().getString(R.string.albums);
            dto.songsTitle = context.getResources().getString(R.string.songs);
            dto.layoutSpanSize = LayoutSpanSizeEnum.LAYOUT.spanSize();
            dto.headerSpanSize = LayoutSpanSizeEnum.HEADER.spanSize();
            dto.albumListSpanSize = LayoutSpanSizeEnum.LIST_ITEM_GRID.spanSize();
            dto.trackListSpanSize = LayoutSpanSizeEnum.LIST_ITEM_LINEAR.spanSize();

            return dto;
        });
    }
}
