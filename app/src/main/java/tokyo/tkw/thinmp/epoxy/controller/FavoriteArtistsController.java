package tokyo.tkw.thinmp.epoxy.controller;

import com.airbnb.epoxy.TypedEpoxyController;
import com.annimon.stream.Stream;

import java.util.List;

import tokyo.tkw.thinmp.artist.Artist;
import tokyo.tkw.thinmp.dto.FavoriteArtistsDto;
import tokyo.tkw.thinmp.epoxy.model.ArtistModel_;
import tokyo.tkw.thinmp.listener.ArtistClickListener;
import tokyo.tkw.thinmp.listener.ArtistMenuClickListener;

public class FavoriteArtistsController extends TypedEpoxyController<FavoriteArtistsDto> {

    @Override
    protected void buildModels(FavoriteArtistsDto dto) {
        buildArtistList(dto.artistList);
    }

    private void buildArtistList(List<Artist> artistList) {
        Stream.of(artistList).forEach(artist -> {
            new ArtistModel_()
                    .id(artist.getId())
                    .albumArtId(artist.getAlbumArtId())
                    .primaryText(artist.getName())
                    .clickListener(new ArtistClickListener(artist.getId()))
                    .menuClickListener(new ArtistMenuClickListener(artist.getId()))
                    .addTo(this);
        });
    }
}
