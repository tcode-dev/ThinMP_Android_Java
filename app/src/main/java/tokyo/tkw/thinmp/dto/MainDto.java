package tokyo.tkw.thinmp.dto;

import java.util.List;

import tokyo.tkw.thinmp.constant.MainMenuEnum;
import tokyo.tkw.thinmp.album.Album;
import tokyo.tkw.thinmp.shortcut.Shortcut;

public class MainDto {
    public String pageTitle;
    public String shortcutTitle;
    public String recentlyAddedTitle;
    public MainMenuEnum[] menuList;
    public List<Shortcut> shortcutList;
    public List<Album> recentlyAddedList;
    public int pageTitleSpanSize;
    public int shortcutTitleSpanSize;
    public int recentlyAddedTitleSpanSize;
    public int mainMenuSpanSize;
    public int shortcutListSpanSize;
    public int recentlyAddedListSpanSize;
}
