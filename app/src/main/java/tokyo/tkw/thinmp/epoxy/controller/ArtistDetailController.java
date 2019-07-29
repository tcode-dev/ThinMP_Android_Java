package tokyo.tkw.thinmp.epoxy.controller;

import com.airbnb.epoxy.TypedEpoxyController;
import com.annimon.stream.Stream;

import java.util.List;

import tokyo.tkw.thinmp.album.Album;
import tokyo.tkw.thinmp.dto.ArtistDetailDto;
import tokyo.tkw.thinmp.epoxy.model.ArtistAlbumModel_;
import tokyo.tkw.thinmp.epoxy.model.ArtistTrackModel_;
import tokyo.tkw.thinmp.epoxy.model.SectionHeaderForGridModel_;
import tokyo.tkw.thinmp.epoxy.model.SectionHeaderForLinearModel_;
import tokyo.tkw.thinmp.listener.AlbumClickListener;
import tokyo.tkw.thinmp.listener.TrackClickListener;
import tokyo.tkw.thinmp.listener.TrackMenuClickListener;
import tokyo.tkw.thinmp.track.Track;

public class ArtistDetailController extends TypedEpoxyController<ArtistDetailDto> {

    @Override
    protected void buildModels(ArtistDetailDto dto) {
        buildAlbumsHeader(dto.albumsTitle, dto.headerSpanSize);
        buildAlbumList(dto.albumList, dto.albumListSpanSize);
        buildSongsHeader(dto.songsTitle, dto.headerSpanSize);
        buildTrackList(dto.trackList, dto.trackListSpanSize);
    }

    private void buildAlbumsHeader(String title, int spanSize) {
        new SectionHeaderForGridModel_()
                .id("albums header")
                .title(title)
                .spanSizeOverride((totalSpanCount, position, itemCount) -> spanSize)
                .addTo(this);
    }

    private void buildAlbumList(List<Album> albumList, int spanSize) {
        Stream.of(albumList).forEach(album -> {
            new ArtistAlbumModel_()
                    .id("album", album.getId())
                    .primaryText(album.getName())
                    .albumArtId(album.getAlbumArtId())
                    .albumClickListener(new AlbumClickListener(album.getId()))
                    .spanSizeOverride((totalSpanCount, position, itemCount) -> spanSize)
                    .addTo(this);
        });
    }

    private void buildSongsHeader(String title, int spanSize) {
        new SectionHeaderForLinearModel_()
                .id("tracks header")
                .title(title)
                .spanSizeOverride((totalSpanCount, position, itemCount) -> spanSize)
                .addTo(this);
    }

    private void buildTrackList(List<Track> trackList, int spanSize) {
        Stream.of(trackList).forEachIndexed((i, track) -> {
            new ArtistTrackModel_()
                    .id("track", track.getId())
                    .albumArtId(track.getAlbumArtId())
                    .primaryText(track.getName())
                    .spanSizeOverride((totalSpanCount, position, itemCount) -> spanSize)
                    .trackClickListener(new TrackClickListener(trackList, i))
                    .menuClickListener(new TrackMenuClickListener(track.getId()))
                    .addTo(this);
        });
    }
}
