package tokyo.tkw.thinmp.menu;

import android.content.Context;
import android.os.Bundle;
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

    public TrackMenu(View view, String trackId) {
        mContext = view.getContext();
        mView = view;
        mTrackId = trackId;
    }

    /**
     * メニューを生成して表示する
     */
    public void show() {
        int hiddenFavorite = FavoriteSongRegister.exists(mTrackId) ? R.id.add_favorite :
                R.id.del_favorite;
        PopupMenu popupMenu = new PopupMenu(mContext, mView);
        popupMenu.getMenuInflater().inflate(R.menu.track_popup_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(createMenuItemClickListener());
        popupMenu.getMenu().findItem(hiddenFavorite).setVisible(false);
        popupMenu.show();
    }

    /**
     * プレイリスト登録
     */
    private void playlist() {
        // データの受け渡し
        Bundle bundle = new Bundle();
        bundle.putString(Track.TRACK_ID, mTrackId);

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

    /**
     * メニューのイベント
     *
     * @return
     */
    private PopupMenu.OnMenuItemClickListener createMenuItemClickListener() {
        return item -> {
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
        };
    }
}
