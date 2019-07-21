package tokyo.tkw.thinmp.epoxy.controller;

import com.airbnb.epoxy.TypedEpoxyController;
import com.annimon.stream.Stream;

import java.util.List;

import tokyo.tkw.thinmp.artist.Artist;
import tokyo.tkw.thinmp.dto.ArtistsDto;
import tokyo.tkw.thinmp.dto.FavoriteArtistsDto;
import tokyo.tkw.thinmp.epoxy.model.ArtistModel_;
import tokyo.tkw.thinmp.epoxy.model.PageHeaderMarginTopWithButtonModel_;
import tokyo.tkw.thinmp.listener.ArtistClickListener;
import tokyo.tkw.thinmp.listener.FavoriteArtistsEditClickListener;

public class FavoriteArtistsController extends TypedEpoxyController<FavoriteArtistsDto> {

    @Override
    protected void buildModels(FavoriteArtistsDto dto) {
        buildHeader(dto.title);
        buildArtistList(dto.artistList);
    }

    private void buildHeader(String title) {
        new PageHeaderMarginTopWithButtonModel_()
                .id("header")
                .title(title)
                .clickListener(new FavoriteArtistsEditClickListener())
                .addTo(this);
    }

    private void buildArtistList(List<Artist> artistList) {
        Stream.of(artistList).forEach(artist -> {
            new ArtistModel_()
                    .id(artist.getId())
                    .albumArtId(artist.getAlbumArtId())
                    .primaryText(artist.getName())
                    .clickListener(new ArtistClickListener(artist.getId()))
                    .addTo(this);
        });
    }
}
