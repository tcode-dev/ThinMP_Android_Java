package tokyo.tkw.thinmp.epoxy.controller;

import com.airbnb.epoxy.TypedEpoxyController;
import com.annimon.stream.Stream;

import java.util.List;

import tokyo.tkw.thinmp.dto.ArtistsDto;
import tokyo.tkw.thinmp.epoxy.model.ArtistListItemModel_;
import tokyo.tkw.thinmp.epoxy.model.PageHeaderModel_;
import tokyo.tkw.thinmp.listener.ArtistClickListener;
import tokyo.tkw.thinmp.artist.Artist;

public class ArtistsController extends TypedEpoxyController<ArtistsDto> {

    @Override
    protected void buildModels(ArtistsDto dto) {
        buildHeader(dto.title);
        buildArtistList(dto.artistList);
    }

    private void buildHeader(String title) {
        new PageHeaderModel_()
                .id("header")
                .title(title)
                .addTo(this);
    }

    private void buildArtistList(List<Artist> artistList) {
        Stream.of(artistList).forEach(artist -> {
            new ArtistListItemModel_()
                    .id(artist.getId())
                    .albumArtId(artist.getAlbumArtId())
                    .primaryText(artist.getName())
                    .clickListener(new ArtistClickListener(artist.getId()))
                    .addTo(this);
        });
    }
}
