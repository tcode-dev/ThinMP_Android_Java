package tokyo.tkw.thinmp.logic;

import android.content.Context;

import com.annimon.stream.Optional;

import tokyo.tkw.thinmp.dto.PlaylistDetailEditDto;
import tokyo.tkw.thinmp.playlist.Playlist;

public class PlaylistDetailEditLogic {
    private Optional<Playlist> playlist;

    private PlaylistDetailEditLogic(Context context, String playlistId) {
        playlist = Playlist.createInstance(context, playlistId);
    }

    public static PlaylistDetailEditLogic createInstance(Context context, String playlistId) {
        return new PlaylistDetailEditLogic(context, playlistId);
    }

    public Optional<PlaylistDetailEditDto> createDto() {
        return playlist.map(playlist -> {
            PlaylistDetailEditDto dto = new PlaylistDetailEditDto();

            dto.playlistName = new StringBuffer(playlist.getName());
            dto.trackList = playlist.getSortedTrackList();
            dto.trackIdList = playlist.getTrackIdList();
            dto.startPosition = 1;

            return dto;
        });
    }
}
