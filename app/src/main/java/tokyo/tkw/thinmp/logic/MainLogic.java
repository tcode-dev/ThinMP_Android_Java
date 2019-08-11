package tokyo.tkw.thinmp.logic;

import android.content.Context;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.constant.LayoutSpanSizeEnum;
import tokyo.tkw.thinmp.constant.MainMenuEnum;
import tokyo.tkw.thinmp.dto.MainDto;
import tokyo.tkw.thinmp.recently.RecentlyAdded;
import tokyo.tkw.thinmp.shortcut.Shortcuts;

public class MainLogic {
    private Context context;
    private Shortcuts shortcuts;
    private RecentlyAdded recentlyAdded;

    private MainLogic(Context context) {
        this.context = context;
        this.shortcuts = Shortcuts.createInstance(context);
        this.recentlyAdded = RecentlyAdded.createInstance(context);
    }

    public static MainLogic createInstance(Context context) {
        return new MainLogic(context);
    }

    public MainDto createDto() {
        MainDto dto = new MainDto();

        dto.pageTitle = context.getString(R.string.library);
        dto.shortcutTitle = context.getString(R.string.shortcut);
        dto.recentlyAddedTitle = context.getString(R.string.recently_added);
        dto.menuList = MainMenuEnum.getValues(context);
        dto.shortcutList = shortcuts.getList();
        dto.recentlyAddedList = recentlyAdded.getRecentlyAddedList();
        dto.layoutSpanSize = LayoutSpanSizeEnum.LAYOUT.spanSize();
        dto.headerSpanSize = LayoutSpanSizeEnum.HEADER.spanSize();
        dto.mainMenuSpanSize = LayoutSpanSizeEnum.LIST_ITEM_LINEAR.spanSize();
        dto.recentlyAddedListSpanSize = LayoutSpanSizeEnum.LIST_ITEM_GRID.spanSize();
        dto.shortcutListSpanSize = LayoutSpanSizeEnum.LIST_ITEM_GRID.spanSize();

        return dto;
    }
}
