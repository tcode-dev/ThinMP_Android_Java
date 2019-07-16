package tokyo.tkw.thinmp.listener;

import android.view.View;

import tokyo.tkw.thinmp.menu.PlaylistMenu;

public class PlaylistMenuClickListener implements View.OnClickListener {
    private String playlistId;

    public PlaylistMenuClickListener(String playlistId) {
        this.playlistId = playlistId;
    }

    @Override
    public void onClick(View view) {
        PlaylistMenu playlistMenu = new PlaylistMenu(view, playlistId);
        playlistMenu.show();
    }
}
