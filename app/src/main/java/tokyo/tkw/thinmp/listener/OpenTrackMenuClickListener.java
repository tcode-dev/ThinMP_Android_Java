package tokyo.tkw.thinmp.listener;

import android.app.Activity;
import android.view.View;

import tokyo.tkw.thinmp.Menu.TrackMenu;

public class OpenTrackMenuClickListener implements View.OnClickListener {
    private Activity mContext;
    String mTrackId;

    public OpenTrackMenuClickListener(Activity context, String trackId) {
        mContext = context;
        mTrackId = trackId;
    }

    @Override
    public void onClick(View view) {
        TrackMenu menu = new TrackMenu(mContext, view, mTrackId);
        menu.show();
    }
}
