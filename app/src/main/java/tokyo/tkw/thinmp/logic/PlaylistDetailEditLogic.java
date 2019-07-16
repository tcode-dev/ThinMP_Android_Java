package tokyo.tkw.thinmp.logic;

import android.content.Context;

import tokyo.tkw.thinmp.dto.PlaylistDetailEditDto;
import tokyo.tkw.thinmp.playlist.Playlist;

public class PlaylistDetailEditLogic {
    private Playlist playlist;

    private PlaylistDetailEditLogic(Context context, String playlistId) {
        playlist = Playlist.createInstance(context, playlistId);
    }

    public static PlaylistDetailEditLogic createInstance(Context context, String playlistId) {
        return new PlaylistDetailEditLogic(context, playlistId);
    }

    public PlaylistDetailEditDto createDto() {
        PlaylistDetailEditDto dto = new PlaylistDetailEditDto();

        dto.playlistRealm = playlist.getPlaylistRealm();
        dto.playlistName = playlist.getName();
        dto.trackRealmList = playlist.getTrackRealmList();
        dto.trackMap = playlist.getTrackMap();

        return dto;
    }
}
