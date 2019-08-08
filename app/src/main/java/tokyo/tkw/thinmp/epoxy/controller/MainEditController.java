package tokyo.tkw.thinmp.epoxy.controller;

import com.airbnb.epoxy.TypedEpoxyController;
import com.annimon.stream.Stream;

import java.util.List;

import tokyo.tkw.thinmp.constant.MainMenuEnum;
import tokyo.tkw.thinmp.dto.MainEditDto;
import tokyo.tkw.thinmp.epoxy.model.MainMenuEditModel_;
import tokyo.tkw.thinmp.epoxy.model.PageHeaderEditModel_;
import tokyo.tkw.thinmp.epoxy.model.SectionHeaderEditModel_;
import tokyo.tkw.thinmp.epoxy.model.ShortcutLinearAlbumModel_;
import tokyo.tkw.thinmp.epoxy.model.ShortcutLinearArtistModel_;
import tokyo.tkw.thinmp.epoxy.model.ShortcutLinearPlaylistModel_;
import tokyo.tkw.thinmp.listener.ApplyClickListener;
import tokyo.tkw.thinmp.listener.CancelClickListener;
import tokyo.tkw.thinmp.realm.ShortcutRealm;
import tokyo.tkw.thinmp.shortcut.Shortcut;

public class MainEditController extends TypedEpoxyController<MainEditDto> {

    @Override
    protected void buildModels(MainEditDto dto) {
        buildPageHeader(dto.pageTitle, dto.shortcutList);
        buildMenu(dto.menuList);

        buildShortcutHeader(dto.shortcutTitle);

        if (!dto.shortcutList.isEmpty()) {
            buildShortcut(dto.shortcutList);
        }

        buildRecentlyAddedHeader(dto.recentlyAddedTitle);
    }

    private void buildPageHeader(String title, List<Shortcut> shortcutList) {
        new PageHeaderEditModel_()
                .id("page header")
                .title(title)
                .applyClickListener(new ApplyClickListener(shortcutList))
                .cancelClickListener(new CancelClickListener())
                .addTo(this);
    }

    private void buildMenu(List<MainMenuEnum> menuList) {
        Stream.of(menuList).forEachIndexed((i, menu) -> {
            new MainMenuEditModel_()
                    .id("menu", menu.label())
                    .primaryText(menu.label())
                    .addTo(this);
        });
    }

    private void buildShortcutHeader(String title) {
        new SectionHeaderEditModel_()
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
        new SectionHeaderEditModel_()
                .id("recently added header")
                .title(title)
                .addTo(this);
    }
}
