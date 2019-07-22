package tokyo.tkw.thinmp.epoxy.controller;

import com.airbnb.epoxy.TypedEpoxyController;
import com.annimon.stream.Stream;

import java.util.List;

import tokyo.tkw.thinmp.dto.FavoriteSongsDto;
import tokyo.tkw.thinmp.epoxy.model.PageHeaderWithButtonModel_;
import tokyo.tkw.thinmp.epoxy.model.TrackModel_;
import tokyo.tkw.thinmp.listener.EpoxyTrackClickListener;
import tokyo.tkw.thinmp.listener.FavoriteSongsEditClickListener;
import tokyo.tkw.thinmp.listener.TrackMenuClickListener;
import tokyo.tkw.thinmp.track.Track;

public class FavoriteSongsController extends TypedEpoxyController<FavoriteSongsDto> {

    @Override
    protected void buildModels(FavoriteSongsDto dto) {
        buildHeader(dto.title);
        buildTrackList(dto.trackList);
    }

    private void buildHeader(String title) {
        new PageHeaderWithButtonModel_()
                .id("header")
                .title(title)
                .clickListener(new FavoriteSongsEditClickListener())
                .addTo(this);
    }

    private void buildTrackList(List<Track> trackList) {
        Stream.of(trackList).forEachIndexed((i, track) -> {
            new TrackModel_()
                    .id(track.getId())
                    .albumArtId(track.getAlbumArtId())
                    .primaryText(track.getName())
                    .secondaryText(track.getArtistName())
                    .trackClickListener(new EpoxyTrackClickListener(trackList, i))
                    .menuClickListener(new TrackMenuClickListener(track.getId()))
                    .addTo(this);
        });
    }
}
