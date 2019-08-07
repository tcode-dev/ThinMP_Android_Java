package tokyo.tkw.thinmp.logic;

import android.content.Context;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.constant.MainMenuEnum;
import tokyo.tkw.thinmp.dto.MainEditDto;
import tokyo.tkw.thinmp.shortcut.Shortcuts;

public class MainEditLogic {
    private Context context;
    private Shortcuts shortcuts;

    private MainEditLogic(Context context) {
        this.context = context;
        this.shortcuts = Shortcuts.createInstance(context);
    }

    public static MainEditLogic createInstance(Context context) {
        return new MainEditLogic(context);
    }

    public MainEditDto createDto() {
        MainEditDto dto = new MainEditDto();

        dto.pageTitle = context.getString(R.string.library);
        dto.shortcutTitle = context.getString(R.string.shortcut);
        dto.recentlyAddedTitle = context.getString(R.string.recently_added);
        dto.menuList = MainMenuEnum.values();
        dto.shortcutList = shortcuts.getList();
        dto.shortcutStartIndex = dto.menuList.length + 2;

        return dto;
    }
}
