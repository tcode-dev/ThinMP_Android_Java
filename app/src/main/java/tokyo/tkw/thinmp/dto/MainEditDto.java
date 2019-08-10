package tokyo.tkw.thinmp.dto;

import java.util.List;

import tokyo.tkw.thinmp.constant.MainMenuEnum;
import tokyo.tkw.thinmp.shortcut.Shortcut;

public class MainEditDto {
    public String pageTitle;
    public String shortcutTitle;
    public String recentlyAddedTitle;
    public List<MainMenuEnum> menuList;
    public List<Shortcut> fromShortcutList;
    public List<Shortcut> shortcutList;
    public int menuStartPosition;
    public int shortcutStartPosition;
}
