package tokyo.tkw.thinmp.logic;

import android.content.Context;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.dto.MainDto;
import tokyo.tkw.thinmp.provider.AlbumContentProvider;
import tokyo.tkw.thinmp.shortcut.ShortcutCollection;

public class MainLogic {
    private Context context;
    private AlbumContentProvider albumContentProvider;
    private ShortcutCollection shortcutCollection;

    private MainLogic(Context context) {
        this.context = context;
        this.albumContentProvider = new AlbumContentProvider(context);
        this.shortcutCollection = new ShortcutCollection(context);
    }

    public static MainLogic createInstance(Context context) {
        return new MainLogic(context);
    }

    public MainDto createDto() {
        MainDto dto = new MainDto();

        dto.menuLabelList = context.getResources().getStringArray(R.array.library_menu);
        dto.shortcutList = shortcutCollection.getList();
        dto.albumList = albumContentProvider.findAll();

        return dto;
    }
}
