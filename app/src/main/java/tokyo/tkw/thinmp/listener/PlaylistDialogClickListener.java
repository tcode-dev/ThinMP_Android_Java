package tokyo.tkw.thinmp.listener;

import android.app.Activity;
import android.view.View;

import androidx.appcompat.app.AlertDialog;

import tokyo.tkw.thinmp.music.Music;
import tokyo.tkw.thinmp.register.add.PlaylistAdder;

public class PlaylistDialogClickListener implements View.OnClickListener {
    private String playlistId;
    private Music music;
    private AlertDialog dialog;

    public PlaylistDialogClickListener(String playlistId, Music music, AlertDialog dialog) {
        this.playlistId = playlistId;
        this.music = music;
        this.dialog = dialog;
    }

    @Override
    public void onClick(View view) {
        PlaylistAdder playlistAdder = PlaylistAdder.createInstance();
        playlistAdder.add(playlistId, music);

        Activity activity = ((Activity) view.getContext());

        if (activity instanceof ScreenUpdateListener) {
            ((ScreenUpdateListener) activity).screenUpdate();
        }

        dialog.dismiss();
    }
}
