package tokyo.tkw.thinmp.logic;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;

import tokyo.tkw.thinmp.dto.PlaylistDialogDto;
import tokyo.tkw.thinmp.music.Music;
import tokyo.tkw.thinmp.playlist.Playlists;

public class PlaylistDialogLogic {
    private Playlists playlists;
    private Music music;
    private AlertDialog dialog;

    private PlaylistDialogLogic(Context context, Music music, AlertDialog dialog) {
        this.playlists = Playlists.createInstance(context);
        this.music = music;
        this.dialog = dialog;
    }

    public static PlaylistDialogLogic createInstance(Context context, Music music, AlertDialog dialog) {
        return new PlaylistDialogLogic(context, music, dialog);
    }

    public PlaylistDialogDto createDto() {
        PlaylistDialogDto dto = new PlaylistDialogDto();

        dto.playlists = playlists.getPlaylists();
        dto.music = music;
        dto.dialog = dialog;

        return dto;
    }
}
