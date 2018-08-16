package tokyo.tkw.thinmp.Menu;

import android.app.Activity;
import android.view.View;
import android.widget.PopupMenu;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.favorite.FavoriteRegister;
import tokyo.tkw.thinmp.listener.TrackMenuClickListener;

public class TrackMenu {
    private Activity mContext;
    private View mView;
    private String mTrackId;

    public TrackMenu(Activity context, View view, String trackId) {
        mContext = context;
        mView = view;
        mTrackId = trackId;
    }

    public void show() {
        int hiddenFavorite = FavoriteRegister.exists(mTrackId) ? R.id.add_favorite : R.id.del_favorite;
        PopupMenu popupMenu = new PopupMenu(mContext, mView);
        popupMenu.getMenuInflater().inflate(R.menu.track_popup_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new TrackMenuClickListener(mContext, mTrackId));
        popupMenu.getMenu().findItem(hiddenFavorite).setVisible(false);
        popupMenu.show();
    }
}

