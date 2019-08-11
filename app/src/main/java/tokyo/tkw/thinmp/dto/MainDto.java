package tokyo.tkw.thinmp.dto;

import com.annimon.stream.Optional;

import java.util.List;

import tokyo.tkw.thinmp.album.Album;
import tokyo.tkw.thinmp.constant.MainMenuEnum;
import tokyo.tkw.thinmp.shortcut.Shortcut;

public class MainDto {
    public String pageTitle;
    public String shortcutTitle;
    public String recentlyAddedTitle;
    public List<MainMenuEnum> menuList;
    public Optional<List<Shortcut>> shortcutList;
    public Optional<List<Album>> recentlyAddedList;
    public int layoutSpanSize;
    public int headerSpanSize;
    public int mainMenuSpanSize;
    public int shortcutListSpanSize;
    public int recentlyAddedListSpanSize;
}
