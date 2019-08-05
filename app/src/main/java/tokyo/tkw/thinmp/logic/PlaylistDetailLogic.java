package tokyo.tkw.thinmp.logic;

import android.content.Context;

import com.annimon.stream.Optional;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.dto.PlaylistDetailDto;
import tokyo.tkw.thinmp.playlist.Playlist;

public class PlaylistDetailLogic {
    private Context context;
    private Optional<Playlist> playlist;

    private PlaylistDetailLogic(Context context, String playlistId) {
        this.context = context;
        this.playlist = Playlist.createInstance(context, playlistId);
    }

    public static PlaylistDetailLogic createInstance(Context context, String playlistId) {
        return new PlaylistDetailLogic(context, playlistId);
    }

    public Optional<PlaylistDetailDto> createDto() {
        return playlist.map(playlist -> {
            PlaylistDetailDto dto = new PlaylistDetailDto();

            dto.playlistName = playlist.getName();
            dto.typeName = context.getString(R.string.playlist);
            dto.albumArtId = playlist.getAlbumArtId();
            dto.trackList = playlist.getSortedTrackList();

            return dto;
        });
    }
}
