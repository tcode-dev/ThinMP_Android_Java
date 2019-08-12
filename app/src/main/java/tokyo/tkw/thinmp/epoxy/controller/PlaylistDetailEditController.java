package tokyo.tkw.thinmp.epoxy.controller;

import com.airbnb.epoxy.TypedEpoxyController;
import com.annimon.stream.Stream;

import java.util.List;

import tokyo.tkw.thinmp.dto.PlaylistDetailEditDto;
import tokyo.tkw.thinmp.epoxy.model.PlaylistNameEditModel_;
import tokyo.tkw.thinmp.epoxy.model.TrackEditModel_;
import tokyo.tkw.thinmp.listener.TrackClickListener;
import tokyo.tkw.thinmp.listener.TrackMenuClickListener;
import tokyo.tkw.thinmp.track.Track;

public class PlaylistDetailEditController extends TypedEpoxyController<PlaylistDetailEditDto> {

    @Override
    protected void buildModels(PlaylistDetailEditDto dto) {
        buildEdit(dto.playlistName);
        buildTrackList(dto.trackList);
    }

    private void buildEdit(String playlistName) {
        new PlaylistNameEditModel_()
                .id("edit")
                .playlistName(playlistName)
                .addTo(this);
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
