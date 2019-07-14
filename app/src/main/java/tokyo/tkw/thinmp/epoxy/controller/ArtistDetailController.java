package tokyo.tkw.thinmp.epoxy.controller;

import com.airbnb.epoxy.TypedEpoxyController;
import com.annimon.stream.Stream;

import java.util.List;

import tokyo.tkw.thinmp.dto.ArtistDetailDto;
import tokyo.tkw.thinmp.epoxy.model.ArtistAlbumModel_;
import tokyo.tkw.thinmp.epoxy.model.ArtistTrackModel_;
import tokyo.tkw.thinmp.epoxy.model.SectionHeaderModel_;
import tokyo.tkw.thinmp.listener.AlbumClickListener;
import tokyo.tkw.thinmp.listener.EpoxyTrackClickListener;
import tokyo.tkw.thinmp.music.Album;
import tokyo.tkw.thinmp.music.Track;

public class ArtistDetailController extends TypedEpoxyController<ArtistDetailDto> {

    @Override
    protected void buildModels(ArtistDetailDto dto) {
        buildAlbumsHeader(dto.albumsTitle, dto.titleSpanSize);
        buildAlbumList(dto.albumList, dto.albumListSpanSize);
        buildSongsHeader(dto.songsTitle, dto.titleSpanSize);
        buildTrackList(dto.trackList, dto.trackListSpanSize);
    }

    private void buildAlbumsHeader(String title, int spanSize) {
        new SectionHeaderModel_()
                .id("albums header")
                .title(title)
                .spanSizeOverride((totalSpanCount, position, itemCount) -> spanSize)
                .addTo(this);
    }

    private void buildAlbumList(List<Album> albumList, int spanSize) {
        Stream.of(albumList).forEach(album -> {
            new ArtistAlbumModel_()
                    .id("album", album.getId())
                    .albumName(album.getName())
                    .albumArtId(album.getAlbumArtId())
                    .albumClickListener(new AlbumClickListener(album.getId()))
                    .spanSizeOverride((totalSpanCount, position, itemCount) -> spanSize)
                    .addTo(this);
        });
    }

    private void buildSongsHeader(String title, int spanSize) {
        new SectionHeaderModel_()
                .id("tracks header")
                .title(title)
                .spanSizeOverride((totalSpanCount, position, itemCount) -> spanSize)
                .addTo(this);
    }

    private void buildTrackList(List<Track> trackList, int spanSize) {
        Stream.of(trackList).forEachIndexed((i, track) -> {
            new ArtistTrackModel_()
                    .id("track", track.getId())
                    .trackName(track.getTitle())
                    .albumArtId(track.getAlbumArtId())
                    .spanSizeOverride((totalSpanCount, position, itemCount) -> spanSize)
                    .clickListener(new EpoxyTrackClickListener(trackList, i))
                    .addTo(this);
        });
    }
}
