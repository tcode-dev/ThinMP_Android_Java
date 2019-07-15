package tokyo.tkw.thinmp.logic;

import android.content.Context;

import tokyo.tkw.thinmp.dto.PlaylistsDto;
import tokyo.tkw.thinmp.playlist.Playlists;

public class PlaylistsLogic {
    private Playlists playlists;

    private PlaylistsLogic(Context context) {
        playlists = Playlists.createInstance(context);
    }

    public static PlaylistsLogic createInstance(Context context) {
        return new PlaylistsLogic(context);
    }

    public PlaylistsDto createDto() {
        PlaylistsDto dto = new PlaylistsDto();

        dto.realmResults = playlists.getRealmResults();
        dto.playlistMap = playlists.getPlaylistMap();

        return dto;
    }
}
