package tokyo.tkw.thinmp.logic;

import android.content.Context;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.dto.PlaylistDetailDto;
import tokyo.tkw.thinmp.playlist.PlaylistTrack;
import tokyo.tkw.thinmp.realm.PlaylistRealm;

public class PlaylistDetailLogic {
    private Context context;
    private PlaylistRealm playlistRealm;
    private PlaylistTrack playlistTrack;

    private PlaylistDetailLogic(Context context, int playlistId) {
        this.context = context;
        this.playlistRealm = PlaylistRealm.createInstance(playlistId);
        this.playlistTrack = new PlaylistTrack(context, playlistId);
    }

    public static PlaylistDetailLogic createInstance(Context context, int playlistId) {
        return new PlaylistDetailLogic(context, playlistId);
    }

    public PlaylistDetailDto createDto() {
        PlaylistDetailDto dto = new PlaylistDetailDto();

        dto.playlistName = playlistRealm.getName();
        dto.typeName = context.getString(R.string.playlist);
        dto.albumArtId = playlistTrack.getFirstTrackAlbumArtId();
        dto.playlistTrackRealms = playlistRealm.getTracks();
        dto.trackMap = playlistTrack.getTrackMap();

        return dto;
    }
}
