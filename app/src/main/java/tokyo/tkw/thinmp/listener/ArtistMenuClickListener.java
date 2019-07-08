package tokyo.tkw.thinmp.listener;

import android.view.View;

import tokyo.tkw.thinmp.menu.ArtistMenu;

public class ArtistMenuClickListener implements View.OnClickListener {
    private String artistId;

    public ArtistMenuClickListener(String artistId) {
        this.artistId = artistId;
    }

    @Override
    public void onClick(View view) {
        ArtistMenu artistMenu = new ArtistMenu(view, artistId);
        artistMenu.show();
    }
}
