package tokyo.tkw.thinmp.menu;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.PopupMenu;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.activity.PlaylistDetailEditActivity;
import tokyo.tkw.thinmp.playlist.PlaylistCollection;
import tokyo.tkw.thinmp.realm.ShortcutRealm;
import tokyo.tkw.thinmp.shortcut.ShortcutRegister;

/**
 * メニュー
 */
public class PlaylistMenu {
    private Context context;
    private View view;
    private String playlistId;
    private ShortcutRegister shortcutRegister;

    public PlaylistMenu(View view, String playlistId) {
        this.context = view.getContext();
        this.view = view;
        this.playlistId = playlistId;
        this.shortcutRegister = ShortcutRegister.createInstance();
    }

    /**
     * メニューを生成して表示する
     */
    public void show() {
        int hiddenShortcut = shortcutRegister.exists(playlistId, ShortcutRealm.TYPE_PLAYLIST) ?
                R.id.add_shortcut :
                R.id.del_shortcut;
        PopupMenu popupMenu = new PopupMenu(context, view);
        popupMenu.getMenuInflater().inflate(R.menu.playlist_popup_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(createMenuItemClickListener());
        popupMenu.getMenu().findItem(hiddenShortcut).setVisible(false);
        popupMenu.show();
    }

    /**
     * メニューのイベント
     *
     * @return
     */
    private PopupMenu.OnMenuItemClickListener createMenuItemClickListener() {
        return item -> {
            switch (item.getItemId()) {
                case R.id.add_shortcut:
                    shortcutRegister.add(playlistId, ShortcutRealm.TYPE_PLAYLIST);

                    return true;
                case R.id.del_shortcut:
                    shortcutRegister.remove(playlistId, ShortcutRealm.TYPE_PLAYLIST);

                    return true;

                case R.id.edit:
                    edit();

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
        intent.putExtra(PlaylistCollection.PLAYLIST_ID, playlistId);
        context.startActivity(intent);
    }
}
