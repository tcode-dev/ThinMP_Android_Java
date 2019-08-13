package tokyo.tkw.thinmp.epoxy.controller;

import com.airbnb.epoxy.TypedEpoxyController;
import com.annimon.stream.Stream;

import java.util.List;

import tokyo.tkw.thinmp.artist.Artist;
import tokyo.tkw.thinmp.dto.FavoriteArtistsEditDto;
import tokyo.tkw.thinmp.epoxy.model.ArtistEditModel_;

public class FavoriteArtistsEditController extends TypedEpoxyController<FavoriteArtistsEditDto> {

    @Override
    protected void buildModels(FavoriteArtistsEditDto dto) {
        buildArtistList(dto.artistList);
    }

    private void buildArtistList(List<Artist> artistList) {
        Stream.of(artistList).forEachIndexed((i, artist) -> {
            new ArtistEditModel_()
                    .id(artist.getId())
                    .albumArtId(artist.getAlbumArtId())
                    .primaryText(artist.getName())
                    .addTo(this);
        });
    }
}
