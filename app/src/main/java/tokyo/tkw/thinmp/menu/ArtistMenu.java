package tokyo.tkw.thinmp.menu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.PopupMenu;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.realm.ShortcutRealm;
import tokyo.tkw.thinmp.shortcut.ShortcutRegister;

public class ArtistMenu {
    private Context context;
    private View view;
    private String artistId;
    private ShortcutRegister shortcutRegister;

    public ArtistMenu(View view, String artistId) {
        this.context = view.getContext();
        this.view = view;
        this.artistId = artistId;
        this.shortcutRegister = ShortcutRegister.createInstance();
    }

    /**
     * メニューを生成して表示する
     */
    @SuppressLint("ResourceType")
    public void show() {
        int hiddenShortcut = shortcutRegister.exists(artistId, ShortcutRealm.TYPE_ARTIST) ?
                R.id.add_shortcut :
                R.id.del_shortcut;
        PopupMenu popupMenu = new PopupMenu(context, view);
        popupMenu.getMenuInflater().inflate(R.layout.popup_menu_artist, popupMenu.getMenu());
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
                case R.id.add_shortcut:
                    shortcutRegister.add(artistId, ShortcutRealm.TYPE_ARTIST);

                    return true;
                case R.id.del_shortcut:
                    shortcutRegister.remove(artistId, ShortcutRealm.TYPE_ARTIST);

                    return true;
                default:
                    break;
            }

            return false;
        };
    }
}
