package tokyo.tkw.thinmp.listener;

import android.content.Context;
import android.view.View;

import com.annimon.stream.Optional;
import com.annimon.stream.Stream;

import java.util.HashMap;
import java.util.List;

import tokyo.tkw.thinmp.config.MainMenuConfig;
import tokyo.tkw.thinmp.config.MainSectionConfig;
import tokyo.tkw.thinmp.constant.MainMenuEnum;
import tokyo.tkw.thinmp.dto.MainEditDto;
import tokyo.tkw.thinmp.register.edit.ShortcutEditor;
import tokyo.tkw.thinmp.shortcut.Shortcut;

public class MainEditApplyClickListener extends BaseClickListener {
    private MainEditDto dto;

    public MainEditApplyClickListener(MainEditDto dto) {
        this.dto = dto;
    }

    @Override
    public void onClick(View view) {
        Context context = view.getContext();

        ShortcutEditor shortcutEditor = ShortcutEditor.createInstance();
        shortcutEditor.update(dto.fromShortcutList, dto.shortcutList);

        saveConfig(context);
        getActivity(context).finish();
    }

    private void saveConfig(Context context) {
        saveMenuConfig(context);
        saveSectionConfig(context);
    }

    private void saveMenuConfig(Context context) {
        MainMenuConfig config = MainMenuConfig.createInstance(context);

        Stream.of(MainMenuEnum.values())
                .filter(menu -> dto.stateMap.containsKey(menu.key()))
                .forEach(menu -> {
                    Optional.ofNullable(dto.stateMap.get(menu.key())).ifPresent(visibility -> {
                        config.setVisibility(menu.key(), visibility);
                    });
                });

        config.setOrder(Stream.ofNullable(dto.menuList).map(MainMenuEnum::key).toList());
    }

    private void saveSectionConfig(Context context) {
        MainSectionConfig config = MainSectionConfig.createInstance(context);

        Optional.ofNullable(dto.stateMap.get(MainSectionConfig.KEY_SHORTCUT)).ifPresent(config::setShortcutVisibility);
        Optional.ofNullable(dto.stateMap.get(MainSectionConfig.KEY_RECENTLY_ADDED)).ifPresent(config::setRecentlyAddedVisibility);
        config.setRecentlyAddedCount(dto.recentlyAddedCount);
    }
}
