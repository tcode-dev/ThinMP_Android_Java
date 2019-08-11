package tokyo.tkw.thinmp.constant;

import android.content.Context;

import com.annimon.stream.Optional;
import com.annimon.stream.Stream;

import java.util.List;

import tokyo.tkw.thinmp.activity.AlbumsActivity;
import tokyo.tkw.thinmp.activity.ArtistsActivity;
import tokyo.tkw.thinmp.activity.FavoriteArtistsActivity;
import tokyo.tkw.thinmp.activity.FavoriteSongsActivity;
import tokyo.tkw.thinmp.activity.PlaylistsActivity;
import tokyo.tkw.thinmp.activity.TracksActivity;
import tokyo.tkw.thinmp.config.MainMenuConfig;

public enum MainMenuEnum {
    ARTISTS("artists", "Artists", ArtistsActivity.class),
    ALBUMS("albums", "Albums", AlbumsActivity.class),
    SONGS("songs", "Songs", TracksActivity.class),
    FAVORITE_ARTISTS("favoriteArtists", "Favorite Artists", FavoriteArtistsActivity.class),
    FAVORITE_SONGS("favoriteSongs", "Favorite Songs", FavoriteSongsActivity.class),
    PLAYLISTS("playlists", "Playlists", PlaylistsActivity.class);

    private final String key;
    private final String label;
    private final Class<?> link;
    private boolean visibility;

    MainMenuEnum(String key, String label, Class<?> link) {
        this.key = key;
        this.label = label;
        this.link = link;
    }

    public String key() {
        return key;
    }

    public String label() {
        return label;
    }

    public Class<?> link() {
        return link;
    }

    public boolean visibility() {
        return visibility;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }

    public static List<MainMenuEnum> getValues(Context context) {
        MainMenuConfig config = MainMenuConfig.createInstance(context);

        List<MainMenuEnum> list = Stream.ofNullable(MainMenuEnum.values()).map(menu -> {
            menu.setVisibility(config.getVisibility(menu.key()));
            return menu;
        }).toList();

        Optional<List<String>> sortOptional = config.getOrder();

        if (sortOptional.isEmpty()) return list;

        return Stream.ofNullable(sortOptional.get())
                .map(key -> Stream.ofNullable(list)
                        .filter(menu -> menu.key().equals(key))
                        .findFirst().get()
                ).toList();
    }
}
