package tokyo.tkw.thinmp.logic;

import android.content.Context;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.constant.MainMenuEnum;
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

        dto.pageTitle = context.getString(R.string.library);
        dto.shortcutTitle = context.getString(R.string.shortcut);
        dto.recentlyAddedTitle = context.getString(R.string.recently_added);
        dto.menuList = MainMenuEnum.values();
        dto.shortcutList = shortcutCollection.getList();
        dto.recentlyAddedList = albumContentProvider.findAll();
        dto.pageTitleSpanSize = 2;
        dto.shortcutTitleSpanSize = 2;
        dto.recentlyAddedTitleSpanSize = 2;
        dto.mainMenuSpanSize = 2;
        dto.recentlyAddedListSpanSize = 1;
        dto.shortcutListSpanSize = 1;

        return dto;
    }
}
