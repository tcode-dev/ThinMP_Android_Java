package tokyo.tkw.thinmp.Menu;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.favorite.FavoriteRegister;
import tokyo.tkw.thinmp.fragment.PlaylistDialogFragment;
import tokyo.tkw.thinmp.music.Track;

public class TrackMenu<T> {
    private Activity mContext;
    private View mView;
    private String mDefaultPlaylistName;
    private Track mTrack;

    public TrackMenu(Activity context, View view, String defaultPlaylistName, Track track) {
        mContext = context;
        mView = view;
        mDefaultPlaylistName = defaultPlaylistName;
        mTrack = track;
    }

    private void show() {
        int hiddenFavorite = FavoriteRegister.exists(mTrack.getId()) ? R.id.add_favorite : R.id.del_favorite;
        PopupMenu popupMenu = new PopupMenu(mContext, mView);
        popupMenu.getMenuInflater().inflate(R.menu.track_popup_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(onMenuItemClickListener());
        popupMenu.getMenu().findItem(hiddenFavorite).setVisible(false);
        popupMenu.show();
    }

    private void playlist() {
        // データの受け渡し
        Bundle bundle = new Bundle();
        bundle.putString("defaultPlaylistName", mDefaultPlaylistName);
        bundle.putSerializable("track", mTrack);

        FragmentActivity activity = (FragmentActivity) mContext;
        PlaylistDialogFragment playlistDialogFragment = new PlaylistDialogFragment();
        playlistDialogFragment.setArguments(bundle);
        playlistDialogFragment.show(activity.getSupportFragmentManager(), "PlaylistDialogFragment");
    }

    private void favorite() {
        FavoriteRegister.set(mTrack.getId());
    }

    /**
     * メニューオープンのイベント
     * @return
     */
    public View.OnClickListener onOpenMenuButtonClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show();
            }
        };
    }

    /**
     * メニューのイベント
     * @return
     */
    private PopupMenu.OnMenuItemClickListener onMenuItemClickListener() {
        return new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.playlist:
                        playlist();

                        return true;
                    case R.id.add_favorite:
                        favorite();

                        return true;
                    case R.id.del_favorite:
                        favorite();

                        return true;
                    default:
                        break;
                }

                return false;
            }
        };
    }
}
