package tokyo.tkw.thinmp.menu;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.PopupMenu;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.listener.ScreenUpdateListener;
import tokyo.tkw.thinmp.realm.ShortcutRealm;
import tokyo.tkw.thinmp.register.add.FavoriteArtistAdder;
import tokyo.tkw.thinmp.register.add.ShortcutAdder;
import tokyo.tkw.thinmp.register.delete.FavoriteArtistDeleter;
import tokyo.tkw.thinmp.register.delete.ShortcutDeleter;
import tokyo.tkw.thinmp.register.exists.FavoriteArtistExists;
import tokyo.tkw.thinmp.register.exists.ShortcutExists;

public class ArtistMenu {
    private Context context;
    private View view;
    private String artistId;

    public ArtistMenu(View view, String artistId) {
        this.context = view.getContext();
        this.view = view;
        this.artistId = artistId;
    }

    /**
     * メニューを生成して表示する
     */
    @SuppressLint("ResourceType")
    public void show() {
        ShortcutExists shortcutExists = ShortcutExists.createInstance();
        int hiddenShortcut = shortcutExists.exists(artistId, ShortcutRealm.TYPE_ARTIST) ?
                R.id.add_shortcut :
                R.id.del_shortcut;

        FavoriteArtistExists favoriteArtistExists = FavoriteArtistExists.createInstance();
        int hiddenFavorite = favoriteArtistExists.exists(artistId) ? R.id.add_favorite : R.id.del_favorite;

        PopupMenu popupMenu = new PopupMenu(context, view);
        popupMenu.getMenuInflater().inflate(R.layout.popup_menu_artist, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(createMenuItemClickListener());
        popupMenu.getMenu().findItem(hiddenShortcut).setVisible(false);
        popupMenu.getMenu().findItem(hiddenFavorite).setVisible(false);
        popupMenu.show();
    }

    /**
     * メニューのイベント
     */
    private PopupMenu.OnMenuItemClickListener createMenuItemClickListener() {
        return item -> {
            switch (item.getItemId()) {
                case R.id.add_shortcut:
                    addShortcut();
                    break;

                case R.id.del_shortcut:
                    deleteShortcut();
                    break;

                case R.id.add_favorite:
                    addFavoriteArtist();
                    break;

                case R.id.del_favorite:
                    deleteFavoriteArtist();
                    break;

                default:
                    return false;
            }

            screenUpdate();

            return true;
        };
    }

    private void addShortcut() {
        ShortcutAdder shortcutAdder = ShortcutAdder.createInstance();
        shortcutAdder.add(artistId, ShortcutRealm.TYPE_ARTIST);
    }

    private void deleteShortcut() {
        ShortcutDeleter shortcutDeleter = ShortcutDeleter.createInstance();
        shortcutDeleter.delete(artistId, ShortcutRealm.TYPE_ARTIST);
    }

    private void addFavoriteArtist() {
        FavoriteArtistAdder favoriteArtistAdder = FavoriteArtistAdder.createInstance();
        favoriteArtistAdder.add(artistId);
    }


    private void deleteFavoriteArtist() {
        FavoriteArtistDeleter favoriteArtistDeleter = FavoriteArtistDeleter.createInstance();
        favoriteArtistDeleter.delete(artistId);
    }

    private void screenUpdate() {
        Activity activity = ((Activity) context);

        if (activity instanceof ScreenUpdateListener) {
            ((ScreenUpdateListener) activity).screenUpdate();
        }
    }
}
