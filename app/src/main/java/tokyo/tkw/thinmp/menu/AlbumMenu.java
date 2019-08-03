package tokyo.tkw.thinmp.menu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.PopupMenu;

import androidx.fragment.app.FragmentActivity;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.fragment.PlaylistDialogFragment;
import tokyo.tkw.thinmp.music.Music;
import tokyo.tkw.thinmp.realm.ShortcutRealm;
import tokyo.tkw.thinmp.register.add.ShortcutAdder;
import tokyo.tkw.thinmp.register.delete.ShortcutDeleter;
import tokyo.tkw.thinmp.register.exists.ShortcutExists;

public class AlbumMenu {
    private Context context;
    private View view;
    private String albumId;

    public AlbumMenu(View view, String albumId) {
        // CollapsingToolbarLayoutの中だとContextThemeWrapperが返ってくる
        this.context = (((ContextThemeWrapper) view.getContext()).getBaseContext());
        this.view = view;
        this.albumId = albumId;
    }

    /**
     * メニューを生成して表示する
     */
    @SuppressLint("ResourceType")
    public void show() {
        ShortcutExists shortcutExists = ShortcutExists.createInstance();
        int hiddenShortcut = shortcutExists.exists(albumId, ShortcutRealm.TYPE_ALBUM) ?
                R.id.add_shortcut :
                R.id.del_shortcut;
        PopupMenu popupMenu = new PopupMenu(context, view);
        popupMenu.getMenuInflater().inflate(R.layout.popup_menu_album, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(createMenuItemClickListener());
        popupMenu.getMenu().findItem(hiddenShortcut).setVisible(false);
        popupMenu.show();
    }

    /**
     * プレイリスト登録
     */
    private void playlist() {
        Bundle bundle = new Bundle();
        bundle.putString(Music.ID, albumId);
        bundle.putInt(Music.TYPE, Music.TYPE_ALBUM);

        FragmentActivity activity = (FragmentActivity) context;
        PlaylistDialogFragment playlistDialogFragment = new PlaylistDialogFragment();
        playlistDialogFragment.setArguments(bundle);
        playlistDialogFragment.show(activity.getSupportFragmentManager(), PlaylistDialogFragment.class.getName());
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
                case R.id.add_shortcut:
                    addShortcut();

                    return true;
                case R.id.del_shortcut:
                    deleteShortcut();

                    return true;
                default:
                    break;
            }

            return false;
        };
    }

    private void addShortcut() {
        ShortcutAdder shortcutAdder = ShortcutAdder.createInstance();
        shortcutAdder.add(albumId, ShortcutRealm.TYPE_ALBUM);
    }

    private void deleteShortcut() {
        ShortcutDeleter shortcutDeleter = ShortcutDeleter.createInstance();
        shortcutDeleter.delete(albumId, ShortcutRealm.TYPE_ALBUM);
    }
}
