package tokyo.tkw.thinmp.listener;

import android.app.Activity;
import android.view.MenuItem;
import android.widget.PopupMenu;
import android.widget.Toast;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.favorite.FavoriteRegister;

public class TrackMenuClickListener implements PopupMenu.OnMenuItemClickListener{
    private Activity mContext;
    private String mTrackId;

    public TrackMenuClickListener(Activity context, String trackId) {
        mContext = context;
        mTrackId = trackId;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.playlist:
                Toast.makeText(
                        mContext,
                        mTrackId + " の選択",
                        Toast.LENGTH_SHORT).show();
                return true;
            case R.id.add_favorite:
                FavoriteRegister.set(mTrackId);

                return true;
            case R.id.del_favorite:
                FavoriteRegister.set(mTrackId);

                return true;
            default:
                break;
        }

        return false;
    }
}
