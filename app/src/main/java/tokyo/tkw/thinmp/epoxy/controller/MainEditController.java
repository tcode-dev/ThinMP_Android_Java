package tokyo.tkw.thinmp.epoxy.controller;

import com.airbnb.epoxy.TypedEpoxyController;
import com.annimon.stream.Stream;

import java.util.List;

import tokyo.tkw.thinmp.constant.MainMenuEnum;
import tokyo.tkw.thinmp.dto.MainEditDto;
import tokyo.tkw.thinmp.epoxy.model.MainMenuModel_;
import tokyo.tkw.thinmp.epoxy.model.PageHeaderModel_;
import tokyo.tkw.thinmp.epoxy.model.SectionHeaderForGridModel_;
import tokyo.tkw.thinmp.epoxy.model.ShortcutLinearAlbumModel_;
import tokyo.tkw.thinmp.epoxy.model.ShortcutLinearArtistModel_;
import tokyo.tkw.thinmp.epoxy.model.ShortcutLinearPlaylistModel_;
import tokyo.tkw.thinmp.realm.ShortcutRealm;
import tokyo.tkw.thinmp.shortcut.Shortcut;

public class MainEditController extends TypedEpoxyController<MainEditDto> {

    @Override
    protected void buildModels(MainEditDto dto) {
        buildPageHeader(dto.pageTitle);
        buildMenu(dto.menuList);

        if (!dto.shortcutList.isEmpty()) {
            buildShortcutHeader(dto.shortcutTitle);
            buildShortcut(dto.shortcutList);
        }

        buildRecentlyAddedHeader(dto.recentlyAddedTitle);
    }

    private void buildPageHeader(String title) {
        new PageHeaderModel_()
                .id("page header")
                .title(title)
                .addTo(this);
    }

    private void buildMenu(MainMenuEnum[] menuList) {
        Stream.of(menuList).forEachIndexed((i, menu) -> {
            new MainMenuModel_()
                    .id("menu", i)
                    .primaryText(menu.label())
                    .addTo(this);
        });
    }

    private void buildShortcutHeader(String title) {
        new SectionHeaderForGridModel_()
                .id("shortcut header")
                .title(title)
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

    private void buildRecentlyAddedHeader(String title) {
        new SectionHeaderForGridModel_()
                .id("recently added header")
                .title(title)
                .addTo(this);
    }
}
