package tokyo.tkw.thinmp.menu;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import androidx.fragment.app.FragmentActivity;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.favorite.FavoriteSongRegister;
import tokyo.tkw.thinmp.fragment.PlaylistDialogFragment;
import tokyo.tkw.thinmp.music.Track;

/**
 * メニュー
 */
public class TrackMenu {
    private Context mContext;
    private View mView;
    private String mTrackId;
    private String mDefaultPlaylistName;

    /**
     * メニューのイベント
     *
     * @return
     */
    private PopupMenu.OnMenuItemClickListener onMenuItemClickListener = new PopupMenu.OnMenuItemClickListener() {
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

    public TrackMenu(Context context, View view, Track track) {
        mContext = context;
        mView = view;
        mTrackId = track.getId();
        mDefaultPlaylistName = track.getTitle();
    }

    /**
     * メニューを生成して表示する
     */
    public void show() {
        int hiddenFavorite = FavoriteSongRegister.exists(mTrackId) ? R.id.add_favorite : R.id.del_favorite;
        PopupMenu popupMenu = new PopupMenu(mContext, mView);
        popupMenu.getMenuInflater().inflate(R.menu.track_popup_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(onMenuItemClickListener);
        popupMenu.getMenu().findItem(hiddenFavorite).setVisible(false);
        popupMenu.show();
    }

    /**
     * プレイリスト登録
     */
    private void playlist() {
        // データの受け渡し
        Bundle bundle = new Bundle();
        bundle.putString("trackId", mTrackId);
        bundle.putString("defaultPlaylistName", mDefaultPlaylistName);

        FragmentActivity activity = (FragmentActivity) mContext;
        PlaylistDialogFragment playlistDialogFragment = new PlaylistDialogFragment();
        playlistDialogFragment.setArguments(bundle);
        playlistDialogFragment.show(activity.getSupportFragmentManager(), "PlaylistDialogFragment");
    }

    /**
     * お気に入り登録
     */
    private void favorite() {
        FavoriteSongRegister.set(mTrackId);
    }
}
