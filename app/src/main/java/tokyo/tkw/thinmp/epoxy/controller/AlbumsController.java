package tokyo.tkw.thinmp.epoxy.controller;

import com.airbnb.epoxy.TypedEpoxyController;
import com.annimon.stream.Stream;

import java.util.List;

import tokyo.tkw.thinmp.album.Album;
import tokyo.tkw.thinmp.dto.AlbumsDto;
import tokyo.tkw.thinmp.epoxy.model.AlbumModel_;
import tokyo.tkw.thinmp.epoxy.model.PageHeaderModel_;
import tokyo.tkw.thinmp.listener.AlbumClickListener;

public class AlbumsController extends TypedEpoxyController<AlbumsDto> {

    @Override
    protected void buildModels(AlbumsDto dto) {
        buildHeader(dto.title, dto.headerSpanSize);
        buildAlbumList(dto.albumList, dto.listSpanSize);
    }

    private void buildHeader(String title, int spanSize) {
        new PageHeaderModel_()
                .id("header")
                .title(title)
                .spanSizeOverride((totalSpanCount, position, itemCount) -> spanSize)
                .addTo(this);
    }

    private void buildAlbumList(List<Album> albumList, int spanSize) {
        Stream.of(albumList).forEach(album -> {
            new AlbumModel_()
                    .id(album.getId())
                    .albumArtId(album.getAlbumArtId())
                    .primaryText(album.getName())
                    .secondaryText(album.getArtistName())
                    .clickListener(new AlbumClickListener(album.getId()))
                    .spanSizeOverride((totalSpanCount, position, itemCount) -> spanSize)
                    .addTo(this);
        });
    }
}
