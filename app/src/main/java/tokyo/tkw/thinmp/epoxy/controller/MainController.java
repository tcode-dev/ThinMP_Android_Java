package tokyo.tkw.thinmp.epoxy.controller;

import com.airbnb.epoxy.TypedEpoxyController;
import com.annimon.stream.Stream;

import java.util.List;

import tokyo.tkw.thinmp.album.Album;
import tokyo.tkw.thinmp.constant.MainMenuEnum;
import tokyo.tkw.thinmp.dto.MainDto;
import tokyo.tkw.thinmp.epoxy.model.AlbumModel_;
import tokyo.tkw.thinmp.epoxy.model.MainMenuModel_;
import tokyo.tkw.thinmp.epoxy.model.PageHeaderMarginTopModel_;
import tokyo.tkw.thinmp.epoxy.model.SectionHeaderMarginBottomModel_;
import tokyo.tkw.thinmp.epoxy.model.SectionHeaderMarginTopBottomModel_;
import tokyo.tkw.thinmp.epoxy.model.ShortcutAlbumModel_;
import tokyo.tkw.thinmp.epoxy.model.ShortcutArtistModel_;
import tokyo.tkw.thinmp.epoxy.model.ShortcutPlaylistModel_;
import tokyo.tkw.thinmp.listener.AlbumClickListener;
import tokyo.tkw.thinmp.listener.ArtistClickListener;
import tokyo.tkw.thinmp.listener.MainMenuClickListener;
import tokyo.tkw.thinmp.listener.PlaylistClickListener;
import tokyo.tkw.thinmp.realm.ShortcutRealm;
import tokyo.tkw.thinmp.shortcut.Shortcut;

public class MainController extends TypedEpoxyController<MainDto> {

    @Override
    protected void buildModels(MainDto dto) {
        buildPageHeader(dto.pageTitle, dto.pageTitleSpanSize);
        buildMenu(dto.menuList, dto.mainMenuSpanSize);
        buildShortcutHeader(dto.shortcutTitle, dto.shortcutTitleSpanSize);
        buildShortcut(dto.shortcutList, dto.shortcutListSpanSize);
        buildRecentlyAddedHeader(dto.recentlyAddedTitle, dto.recentlyAddedTitleSpanSize);
        buildRecentlyAdded(dto.recentlyAddedList, dto.recentlyAddedListSpanSize);
    }

    private void buildPageHeader(String title, int spanSize) {
        new PageHeaderMarginTopModel_()
                .id("page header")
                .title(title)
                .spanSizeOverride((totalSpanCount, position, itemCount) -> spanSize)
                .addTo(this);
    }

    private void buildMenu(MainMenuEnum[] menuList, int spanSize) {
        Stream.of(menuList).forEachIndexed((i, menu) -> {
            new MainMenuModel_()
                    .id("menu", i)
                    .primaryText(menu.label())
                    .clickListener(new MainMenuClickListener(menu.link()))
                    .spanSizeOverride((totalSpanCount, position, itemCount) -> spanSize)
                    .addTo(this);
        });
    }

    private void buildShortcutHeader(String title, int spanSize) {
        new SectionHeaderMarginTopBottomModel_()
                .id("shortcut header")
                .title(title)
                .spanSizeOverride((totalSpanCount, position, itemCount) -> spanSize)
                .addTo(this);
    }

    private void buildShortcut(List<Shortcut> shortcutList, int spanSize) {
        Stream.of(shortcutList).forEach(shortcut -> {
            switch (shortcut.getType()) {
                case ShortcutRealm.ARTIST:
                    new ShortcutArtistModel_()
                            .id("shortcut", shortcut.getId())
                            .albumArtId(shortcut.getAlbumArtId())
                            .primaryText(shortcut.getName())
                            .secondaryText(shortcut.getType())
                            .clickListener(new ArtistClickListener(shortcut.getId()))
                            .spanSizeOverride((totalSpanCount, position, itemCount) -> spanSize)
                            .addTo(this);
                    break;
                case ShortcutRealm.ALBUM:
                    new ShortcutAlbumModel_()
                            .id("shortcut", shortcut.getId())
                            .albumArtId(shortcut.getAlbumArtId())
                            .primaryText(shortcut.getName())
                            .secondaryText(shortcut.getType())
                            .clickListener(new AlbumClickListener(shortcut.getId()))
                            .spanSizeOverride((totalSpanCount, position, itemCount) -> spanSize)
                            .addTo(this);
                    break;
                case ShortcutRealm.PLAYLIST:
                    new ShortcutPlaylistModel_()
                            .id("shortcut", shortcut.getId())
                            .albumArtId(shortcut.getAlbumArtId())
                            .primaryText(shortcut.getName())
                            .secondaryText(shortcut.getType())
                            .clickListener(new PlaylistClickListener(shortcut.getId()))
                            .spanSizeOverride((totalSpanCount, position, itemCount) -> spanSize)
                            .addTo(this);
                    break;
                default:
            }
        });
    }

    private void buildRecentlyAddedHeader(String title, int spanSize) {
        new SectionHeaderMarginBottomModel_()
                .id("recently added header")
                .title(title)
                .spanSizeOverride((totalSpanCount, position, itemCount) -> spanSize)
                .addTo(this);
    }

    private void buildRecentlyAdded(List<Album> albumList, int spanSize) {
        Stream.of(albumList).forEach(album -> {
            new AlbumModel_()
                    .id(album.getId())
                    .albumArtId(album.getAlbumArtId())
                    .primaryText(album.getName())
                    .secondaryText(album.getArtistName())
                    .clickListener(new AlbumClickListener(album.getId()))
                    .spanSizeOverride((totalSpanCount, position, itemCount) -> spanSize)
                    .addTo(this);
        });
    }
}
