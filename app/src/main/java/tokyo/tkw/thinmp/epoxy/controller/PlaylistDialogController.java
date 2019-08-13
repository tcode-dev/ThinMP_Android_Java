package tokyo.tkw.thinmp.epoxy.controller;

import androidx.appcompat.app.AlertDialog;

import com.airbnb.epoxy.TypedEpoxyController;
import com.annimon.stream.Stream;

import java.util.List;

import tokyo.tkw.thinmp.dto.PlaylistDialogDto;
import tokyo.tkw.thinmp.epoxy.model.PlaylistDialogModel_;
import tokyo.tkw.thinmp.listener.PlaylistDialogClickListener;
import tokyo.tkw.thinmp.music.Music;
import tokyo.tkw.thinmp.playlist.Playlist;

public class PlaylistDialogController extends TypedEpoxyController<PlaylistDialogDto> {

    @Override
    protected void buildModels(PlaylistDialogDto dto) {
        buildList(dto.playlists, dto.music, dto.dialog);
    }

    private void buildList(List<Playlist> playlists, Music music, AlertDialog dialog) {
        Stream.of(playlists).forEachIndexed((i, playlist) -> {
            new PlaylistDialogModel_()
                    .id(playlist.getId())
                    .albumArtId(playlist.getAlbumArtId())
                    .primaryText(playlist.getName())
                    .clickListener(new PlaylistDialogClickListener(playlist.getId(), music, dialog))
                    .addTo(this);
        });
    }
}
