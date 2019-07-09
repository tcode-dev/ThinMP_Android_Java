package tokyo.tkw.thinmp.logic;

import android.content.Context;

import tokyo.tkw.thinmp.dto.PlaylistDetailEditDto;
import tokyo.tkw.thinmp.playlist.PlaylistCollection;
import tokyo.tkw.thinmp.playlist.PlaylistTrack;
import tokyo.tkw.thinmp.realm.PlaylistRealm;

public class PlaylistDetailEditLogic {
    private PlaylistRealm playlistRealm;
    private PlaylistTrack playlistTrack;

    private PlaylistDetailEditLogic(Context context, int playlistId) {
        PlaylistCollection playlistCollection = PlaylistCollection.createInstance(context);

        playlistRealm = playlistCollection.findById(playlistId);
        playlistTrack = new PlaylistTrack(context, playlistId);
    }

    public static PlaylistDetailEditLogic createInstance(Context context, int playlistId) {
        return new PlaylistDetailEditLogic(context, playlistId);
    }

    public PlaylistDetailEditDto createDto() {
        PlaylistDetailEditDto dto = new PlaylistDetailEditDto();

        dto.playlistRealm = playlistRealm;
        dto.playlistName = playlistRealm.getName();
        dto.trackRealmList = playlistRealm.getTrackRealmList();
        dto.trackMap = playlistTrack.getTrackMap();

        return dto;
    }
}
