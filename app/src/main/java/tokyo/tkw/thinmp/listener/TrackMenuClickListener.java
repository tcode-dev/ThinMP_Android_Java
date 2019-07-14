package tokyo.tkw.thinmp.listener;

import android.view.View;

import tokyo.tkw.thinmp.menu.TrackMenu;

public class TrackMenuClickListener implements View.OnClickListener {
    private String trackId;

    public TrackMenuClickListener(String trackId) {
        this.trackId = trackId;
    }

    @Override
    public void onClick(View view) {
        TrackMenu trackMenu = new TrackMenu(view, trackId);
        trackMenu.show();
    }
}
