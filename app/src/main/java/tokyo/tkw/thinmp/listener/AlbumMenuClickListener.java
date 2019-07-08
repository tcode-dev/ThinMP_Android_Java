package tokyo.tkw.thinmp.listener;

import android.view.View;

import tokyo.tkw.thinmp.menu.AlbumMenu;

public class AlbumMenuClickListener implements View.OnClickListener {
    private String albumId;

    public AlbumMenuClickListener(String albumId) {
        this.albumId = albumId;
    }

    @Override
    public void onClick(View view) {
        AlbumMenu albumMenu = new AlbumMenu(view, albumId);
        albumMenu.show();
    }
}
