package tokyo.tkw.thinmp.logic;

import android.content.Context;

import java.util.HashMap;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.config.MainSectionConfig;
import tokyo.tkw.thinmp.constant.MainMenuEnum;
import tokyo.tkw.thinmp.dto.MainEditDto;
import tokyo.tkw.thinmp.shortcut.Shortcuts;

public class MainEditLogic {
    private Context context;
    private Shortcuts shortcuts;
    private MainSectionConfig mainSectionConfig;

    private MainEditLogic(Context context) {
        this.context = context;
        this.shortcuts = Shortcuts.createInstance(context);
        this.mainSectionConfig = MainSectionConfig.createInstance(context);
    }

    public static MainEditLogic createInstance(Context context) {
        return new MainEditLogic(context);
    }

    public MainEditDto createDto() {
        MainEditDto dto = new MainEditDto();

        dto.pageTitle = context.getString(R.string.edit);
        dto.shortcutTitle = context.getString(R.string.shortcut);
        dto.recentlyAddedTitle = context.getString(R.string.recently_added);
        dto.menuList = MainMenuEnum.getValues(context);
        dto.fromShortcutList = shortcuts.getList();
        dto.shortcutList = shortcuts.getList();
        dto.menuStartPosition = 1;
        dto.shortcutStartPosition = dto.menuList.size() + 2;
        dto.stateMap = new HashMap<>();
        dto.shortcutVisibility = mainSectionConfig.getShortcutVisibility();
        dto.recentlyAddedVisibility = mainSectionConfig.getRecentlyAddedVisibility();

        return dto;
    }
}
