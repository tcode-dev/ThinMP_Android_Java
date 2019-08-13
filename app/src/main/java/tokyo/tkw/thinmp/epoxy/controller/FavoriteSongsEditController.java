package tokyo.tkw.thinmp.epoxy.controller;

import com.airbnb.epoxy.TypedEpoxyController;
import com.annimon.stream.Stream;

import java.util.List;

import tokyo.tkw.thinmp.dto.FavoriteSongsEditDto;
import tokyo.tkw.thinmp.epoxy.model.TrackEditModel_;
import tokyo.tkw.thinmp.track.Track;

public class FavoriteSongsEditController extends TypedEpoxyController<FavoriteSongsEditDto> {

    @Override
    protected void buildModels(FavoriteSongsEditDto dto) {
        buildTrackList(dto.trackList);
    }

    private void buildTrackList(List<Track> trackList) {
        Stream.of(trackList).forEachIndexed((i, track) -> {
            new TrackEditModel_()
                    .id(track.getId())
                    .albumArtId(track.getAlbumArtId())
                    .primaryText(track.getName())
                    .secondaryText(track.getArtistName())
                    .addTo(this);
        });
    }
}
