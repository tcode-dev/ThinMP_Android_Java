package tokyo.tkw.thinmp.logic;

import android.content.Context;

import tokyo.tkw.thinmp.dto.PlaylistsEditDto;
import tokyo.tkw.thinmp.playlist.Playlists;

public class PlaylistsEditLogic {
    private Playlists playlists;

    private PlaylistsEditLogic(Context context) {
        playlists = Playlists.createInstance(context);
    }

    public static PlaylistsEditLogic createInstance(Context context) {
        return new PlaylistsEditLogic(context);
    }

    public PlaylistsEditDto createDto() {
        PlaylistsEditDto dto = new PlaylistsEditDto();

        dto.playlists = playlists.getPlaylists();
        dto.playlistIdList = playlists.getPlaylistIds();

        return dto;
    }
}
