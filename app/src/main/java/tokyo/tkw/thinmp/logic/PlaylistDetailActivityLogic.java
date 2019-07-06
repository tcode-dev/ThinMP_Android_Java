package tokyo.tkw.thinmp.logic;

import android.content.Context;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.dto.PlaylistDetailActivityDto;
import tokyo.tkw.thinmp.playlist.PlaylistTrack;
import tokyo.tkw.thinmp.realm.PlaylistRealm;

public class PlaylistDetailActivityLogic {
    private Context context;
    private PlaylistRealm playlistRealm;
    private PlaylistTrack playlistTrack;

    private PlaylistDetailActivityLogic(Context context, int playlistId) {
        this.context = context;
        this.playlistRealm = PlaylistRealm.createInstance(playlistId);
        this.playlistTrack = new PlaylistTrack(context, playlistId);
    }

    public static PlaylistDetailActivityLogic createInstance(Context context, int playlistId) {
        return new PlaylistDetailActivityLogic(context, playlistId);
    }

    public PlaylistDetailActivityDto createDto() {
        PlaylistDetailActivityDto dto = new PlaylistDetailActivityDto();

        dto.playlistName = playlistRealm.getName();
        dto.typeName = context.getString(R.string.playlist);
        dto.albumArt = playlistTrack.getFirstTrackAlbumArt();
        dto.playlistTrackRealms = playlistRealm.getTracks();
        dto.trackMap = playlistTrack.getTrackMap();

        return dto;
    }
}
