package tokyo.tkw.thinmp.menu;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.PopupMenu;

import androidx.fragment.app.FragmentActivity;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.favorite.FavoriteSongRegister;
import tokyo.tkw.thinmp.fragment.PlaylistDialogFragment;
import tokyo.tkw.thinmp.listener.ScreenUpdateListener;
import tokyo.tkw.thinmp.music.Music;

public class TrackMenu {
    private Context context;
    private View view;
    private String trackId;

    public TrackMenu(View view, String trackId) {
        context = view.getContext();
        this.view = view;
        this.trackId = trackId;
    }

    /**
     * メニューを生成して表示する
     */
    @SuppressLint("ResourceType")
    public void show() {
        int hiddenFavorite = FavoriteSongRegister.exists(trackId) ? R.id.add_favorite :
                R.id.del_favorite;
        PopupMenu popupMenu = new PopupMenu(context, view);
        popupMenu.getMenuInflater().inflate(R.layout.popup_menu_track, popupMenu.getMenu());
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
        bundle.putString(Music.ID, trackId);
        bundle.putInt(Music.TYPE, Music.TYPE_TRACK);

        FragmentActivity activity = (FragmentActivity) context;
        PlaylistDialogFragment playlistDialogFragment = new PlaylistDialogFragment();
        playlistDialogFragment.setArguments(bundle);
        playlistDialogFragment.show(activity.getSupportFragmentManager(), PlaylistDialogFragment.class.getName());
    }

    /**
     * お気に入り登録
     */
    private void favorite() {
        FavoriteSongRegister.set(trackId);
    }

    /**
     * メニューのイベント
     */
    private PopupMenu.OnMenuItemClickListener createMenuItemClickListener() {
        return item -> {
            switch (item.getItemId()) {
                case R.id.playlist:
                    playlist();
                    return true;

                case R.id.add_favorite:
                    favorite();
                    break;

                case R.id.del_favorite:
                    favorite();
                    break;

                default:
                    return false;
            }

            screenUpdate();

            return true;
        };
    }

    private void screenUpdate() {
        Activity activity = ((Activity) context);

        if (activity instanceof ScreenUpdateListener) {
            ((ScreenUpdateListener) activity).screenUpdate();
        }
    }
}
