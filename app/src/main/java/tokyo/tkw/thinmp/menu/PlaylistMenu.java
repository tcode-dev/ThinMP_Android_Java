package tokyo.tkw.thinmp.menu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.PopupMenu;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.activity.PlaylistDetailEditActivity;
import tokyo.tkw.thinmp.playlist.Playlist;
import tokyo.tkw.thinmp.realm.ShortcutRealm;
import tokyo.tkw.thinmp.register.add.ShortcutAdder;
import tokyo.tkw.thinmp.register.delete.ShortcutDeleter;
import tokyo.tkw.thinmp.register.exists.ShortcutExists;

public class PlaylistMenu {
    private Context context;
    private View view;
    private String playlistId;

    public PlaylistMenu(View view, String playlistId) {
        this.context = view.getContext();
        this.view = view;
        this.playlistId = playlistId;
    }

    /**
     * メニューを生成して表示する
     */
    @SuppressLint("ResourceType")
    public void show() {
        ShortcutExists shortcutExists = ShortcutExists.createInstance();
        int hiddenShortcut = shortcutExists.exists(playlistId, ShortcutRealm.TYPE_PLAYLIST) ?
                R.id.add_shortcut :
                R.id.del_shortcut;
        PopupMenu popupMenu = new PopupMenu(context, view);
        popupMenu.getMenuInflater().inflate(R.layout.popup_menu_playlist, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(createMenuItemClickListener());
        popupMenu.getMenu().findItem(hiddenShortcut).setVisible(false);
        popupMenu.show();
    }

    /**
     * メニューのイベント
     */
    private PopupMenu.OnMenuItemClickListener createMenuItemClickListener() {
        return item -> {
            switch (item.getItemId()) {
                case R.id.edit:
                    edit();

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

    private void edit() {
        Context context = view.getContext();
        Intent intent = new Intent(context, PlaylistDetailEditActivity.class);
        intent.putExtra(Playlist.PLAYLIST_ID, playlistId);
        context.startActivity(intent);
    }

    private void addShortcut() {
        ShortcutAdder shortcutAdder = ShortcutAdder.createInstance();
        shortcutAdder.add(playlistId, ShortcutRealm.TYPE_PLAYLIST);
    }

    private void deleteShortcut() {
        ShortcutDeleter shortcutDeleter = ShortcutDeleter.createInstance();
        shortcutDeleter.delete(playlistId, ShortcutRealm.TYPE_PLAYLIST);
    }
}
