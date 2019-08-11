package tokyo.tkw.thinmp.listener;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.annimon.stream.Stream;

import java.util.HashMap;
import java.util.List;

import tokyo.tkw.thinmp.config.MainMenuConfig;
import tokyo.tkw.thinmp.constant.MainMenuEnum;
import tokyo.tkw.thinmp.register.edit.ShortcutEditor;
import tokyo.tkw.thinmp.shortcut.Shortcut;

public class MainEditApplyClickListener implements View.OnClickListener {
    private List<Shortcut> fromList;
    private List<Shortcut> toList;
    private HashMap<String, Boolean> stateMap;

    public MainEditApplyClickListener(List<Shortcut> fromList, List<Shortcut> toList,
                                      HashMap<String, Boolean> stateMap) {
        this.fromList = fromList;
        this.toList = toList;
        this.stateMap = stateMap;
    }

    @Override
    public void onClick(View view) {
        Context context = view.getContext();

        ShortcutEditor shortcutEditor = ShortcutEditor.createInstance();
        shortcutEditor.update(fromList, toList);

        saveConfig(context);

        ((Activity) context).finish();
    }

    private void saveConfig(Context context) {
        MainMenuConfig mainMenuConfig = MainMenuConfig.createInstance(context);

        Stream.of(MainMenuEnum.values())
                .filter(menu -> stateMap.containsKey(menu.key()))
                .forEach(menu -> {
                    mainMenuConfig.setVisibility(menu.key(), stateMap.get(menu.key()));
                });
    }
}
