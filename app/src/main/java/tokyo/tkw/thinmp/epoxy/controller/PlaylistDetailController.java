package tokyo.tkw.thinmp.epoxy.controller;

import com.airbnb.epoxy.TypedEpoxyController;
import com.annimon.stream.Stream;

import java.util.List;

import tokyo.tkw.thinmp.epoxy.model.TrackModel_;
import tokyo.tkw.thinmp.listener.TrackClickListener;
import tokyo.tkw.thinmp.listener.TrackMenuClickListener;
import tokyo.tkw.thinmp.track.Track;

public class PlaylistDetailController extends TypedEpoxyController<List<Track>> {

    @Override
    protected void buildModels(List<Track> trackList) {
        buildTrackList(trackList);
    }

    private void buildTrackList(List<Track> trackList) {
        Stream.of(trackList).forEachIndexed((i, track) -> {
            new TrackModel_()
                    .id(track.getId())
                    .albumArtId(track.getAlbumArtId())
                    .primaryText(track.getName())
                    .secondaryText(track.getArtistName())
                    .trackClickListener(new TrackClickListener(trackList, i))
                    .menuClickListener(new TrackMenuClickListener(track.getId()))
                    .addTo(this);
        });
    }
}
