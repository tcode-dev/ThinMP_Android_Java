package tokyo.tkw.thinmp.epoxy.controller;

import com.airbnb.epoxy.TypedEpoxyController;
import com.annimon.stream.Stream;

import java.util.HashMap;
import java.util.List;

import tokyo.tkw.thinmp.config.MainSectionConfig;
import tokyo.tkw.thinmp.constant.MainMenuEnum;
import tokyo.tkw.thinmp.dto.MainEditDto;
import tokyo.tkw.thinmp.epoxy.model.MainMenuEditModel_;
import tokyo.tkw.thinmp.epoxy.model.RecentlyAddedCountModel_;
import tokyo.tkw.thinmp.epoxy.model.SectionHeaderRecentlyAddedEditModel_;
import tokyo.tkw.thinmp.epoxy.model.SectionHeaderShortcutEditModel_;
import tokyo.tkw.thinmp.epoxy.model.ShortcutLinearAlbumModel_;
import tokyo.tkw.thinmp.epoxy.model.ShortcutLinearArtistModel_;
import tokyo.tkw.thinmp.epoxy.model.ShortcutLinearPlaylistModel_;
import tokyo.tkw.thinmp.listener.MainEditCheckBoxChangeListener;
import tokyo.tkw.thinmp.listener.RecentlyAddedCountSelectedListener;
import tokyo.tkw.thinmp.realm.ShortcutRealm;
import tokyo.tkw.thinmp.shortcut.Shortcut;

public class MainEditController extends TypedEpoxyController<MainEditDto> {

    @Override
    protected void buildModels(MainEditDto dto) {
        buildMenu(dto.menuList, dto.stateMap);

        buildShortcutHeader(dto.shortcutTitle, dto.shortcutVisibility, dto.stateMap);

        if (!dto.shortcutList.isEmpty()) {
            buildShortcut(dto.shortcutList);
        }

        buildRecentlyAddedHeader(dto.recentlyAddedTitle, MainSectionConfig.KEY_RECENTLY_ADDED,
                dto.recentlyAddedVisibility, dto.stateMap);

        buildRecentlyAddedCount(dto);
    }

    private void buildMenu(List<MainMenuEnum> menuList, HashMap<String, Boolean> stateMap) {
        Stream.of(menuList).forEach(menu -> {
            new MainMenuEditModel_()
                    .id("menu", menu.key())
                    .primaryText(menu.label())
                    .visibility(menu.visibility())
                    .changeListener(new MainEditCheckBoxChangeListener(menu.key(), stateMap))
                    .addTo(this);
        });
    }

    private void buildShortcutHeader(String title, boolean visibility, HashMap<String, Boolean> stateMap) {
        new SectionHeaderShortcutEditModel_()
                .id("shortcut header")
                .title(title)
                .visibility(visibility)
                .changeListener(new MainEditCheckBoxChangeListener(MainSectionConfig.KEY_SHORTCUT, stateMap))
                .addTo(this);
    }

    private void buildShortcut(List<Shortcut> shortcutList) {
        Stream.of(shortcutList).forEach(shortcut -> {
            switch (shortcut.getType()) {
                case ShortcutRealm.ARTIST:
                    new ShortcutLinearArtistModel_()
                            .id("shortcut", shortcut.getId())
                            .albumArtId(shortcut.getAlbumArtId())
                            .primaryText(shortcut.getName())
                            .secondaryText(shortcut.getType())
                            .addTo(this);
                    break;
                case ShortcutRealm.ALBUM:
                    new ShortcutLinearAlbumModel_()
                            .id("shortcut", shortcut.getId())
                            .albumArtId(shortcut.getAlbumArtId())
                            .primaryText(shortcut.getName())
                            .secondaryText(shortcut.getType())
                            .addTo(this);
                    break;
                case ShortcutRealm.PLAYLIST:
                    new ShortcutLinearPlaylistModel_()
                            .id("shortcut", shortcut.getId())
                            .albumArtId(shortcut.getAlbumArtId())
                            .primaryText(shortcut.getName())
                            .secondaryText(shortcut.getType())
                            .addTo(this);
                    break;
                default:
            }
        });
    }

    private void buildRecentlyAddedHeader(String title, String key, boolean visibility,
                                          HashMap<String, Boolean> stateMap) {
        new SectionHeaderRecentlyAddedEditModel_()
                .id("recently added header")
                .title(title)
                .visibility(visibility)
                .changeListener(new MainEditCheckBoxChangeListener(key, stateMap))
                .addTo(this);
    }

    private void buildRecentlyAddedCount(MainEditDto dto) {
        new RecentlyAddedCountModel_()
                .id("recently added count")
                .position(dto.recentlyAddedPosition)
                .spinnerListener(new RecentlyAddedCountSelectedListener(dto))
                .addTo(this);
    }
}
