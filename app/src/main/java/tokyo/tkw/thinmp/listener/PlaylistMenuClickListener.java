package tokyo.tkw.thinmp.listener;

import android.view.View;

import tokyo.tkw.thinmp.menu.PlaylistMenu;

public class PlaylistMenuClickListener implements View.OnClickListener {
    private String playlistId;

    public PlaylistMenuClickListener(int playlistId) {
        this.playlistId = Integer.toString(playlistId);
    }

    @Override
    public void onClick(View view) {
        PlaylistMenu playlistMenu = new PlaylistMenu(view, playlistId);
        playlistMenu.show();
    }
}
