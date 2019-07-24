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
import tokyo.tkw.thinmp.shortcut.ShortcutRegister;

public class AlbumMenu {
    private Context context;
    private View view;
    private String albumId;
    private ShortcutRegister shortcutRegister;

    public AlbumMenu(View view, String albumId) {
        // CollapsingToolbarLayoutの中だとContextThemeWrapperが返ってくる
        this.context = (((ContextThemeWrapper) view.getContext()).getBaseContext());
        this.view = view;
        this.albumId = albumId;
        this.shortcutRegister = ShortcutRegister.createInstance();
    }

    /**
     * メニューを生成して表示する
     */
    @SuppressLint("ResourceType")
    public void show() {
        int hiddenShortcut = shortcutRegister.exists(albumId, ShortcutRealm.TYPE_ALBUM) ?
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
                    shortcutRegister.add(albumId, ShortcutRealm.TYPE_ALBUM);

                    return true;
                case R.id.del_shortcut:
                    shortcutRegister.delete(albumId, ShortcutRealm.TYPE_ALBUM);

                    return true;
                default:
                    break;
            }

            return false;
        };
    }
}
