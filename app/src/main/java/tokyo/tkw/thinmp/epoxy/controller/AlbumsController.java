package tokyo.tkw.thinmp.epoxy.controller;

import com.airbnb.epoxy.TypedEpoxyController;
import com.annimon.stream.Stream;

import java.util.List;

import tokyo.tkw.thinmp.dto.AlbumsDto;
import tokyo.tkw.thinmp.epoxy.model.AlbumModel_;
import tokyo.tkw.thinmp.epoxy.model.PageHeaderModel_;
import tokyo.tkw.thinmp.listener.AlbumClickListener;
import tokyo.tkw.thinmp.music.Album;

public class AlbumsController extends TypedEpoxyController<AlbumsDto> {

    @Override
    protected void buildModels(AlbumsDto dto) {
        buildHeader(dto.title, dto.titleSpanSize);
        buildAlbumList(dto.albumList, dto.albumListSpanSize);
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
                    .primaryText(album.getName())
                    .secondaryText(album.getArtistName())
                    .albumArtId(album.getAlbumArtId())
                    .clickListener(new AlbumClickListener(album.getId()))
                    .spanSizeOverride((totalSpanCount, position, itemCount) -> spanSize)
                    .addTo(this);
        });
    }
}
