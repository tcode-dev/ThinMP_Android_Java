package tokyo.tkw.thinmp.epoxy.controller;

import com.airbnb.epoxy.TypedEpoxyController;
import com.annimon.stream.Stream;

import java.util.List;

import tokyo.tkw.thinmp.dto.PlaylistsDto;
import tokyo.tkw.thinmp.epoxy.model.PageHeaderWithButtonModel_;
import tokyo.tkw.thinmp.epoxy.model.PlaylistModel_;
import tokyo.tkw.thinmp.listener.PlaylistClickListener;
import tokyo.tkw.thinmp.listener.PlaylistsEditClickListener;
import tokyo.tkw.thinmp.playlist.Playlist;

public class PlaylistsController extends TypedEpoxyController<PlaylistsDto> {

    @Override
    protected void buildModels(PlaylistsDto dto) {
        buildHeader(dto.title, dto.titleSpanSize);
        buildList(dto.playlists, dto.listSpanSize);
    }

    private void buildHeader(String title, int spanSize) {
        new PageHeaderWithButtonModel_()
                .id("header")
                .title(title)
                .clickListener(new PlaylistsEditClickListener())
                .spanSizeOverride((totalSpanCount, position, itemCount) -> spanSize)
                .addTo(this);
    }

    private void buildList(List<Playlist> playlists, int spanSize) {
        Stream.of(playlists).forEach(playlist -> {
            new PlaylistModel_()
                    .id(playlist.getId())
                    .albumArtId(playlist.getAlbumArtId())
                    .primaryText(playlist.getName())
                    .clickListener(new PlaylistClickListener(playlist.getId()))
                    .spanSizeOverride((totalSpanCount, position, itemCount) -> spanSize)
                    .addTo(this);
        });
    }
}
