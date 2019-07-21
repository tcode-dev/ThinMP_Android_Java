package tokyo.tkw.thinmp.logic;

import android.content.Context;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.dto.PlaylistsDto;
import tokyo.tkw.thinmp.playlist.Playlists;

public class PlaylistsLogic {
    private Context context;
    private Playlists playlists;

    private PlaylistsLogic(Context context) {
        this.context = context;
        this.playlists = Playlists.createInstance(context);
    }

    public static PlaylistsLogic createInstance(Context context) {
        return new PlaylistsLogic(context);
    }

    public PlaylistsDto createDto() {
        PlaylistsDto dto = new PlaylistsDto();

        dto.title = context.getResources().getString(R.string.playlists);
        dto.playlists = playlists.getPlaylists();
        dto.titleSpanSize = 2;
        dto.listSpanSize = 1;

        return dto;
    }
}
