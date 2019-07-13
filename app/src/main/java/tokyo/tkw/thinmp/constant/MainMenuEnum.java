package tokyo.tkw.thinmp.constant;

import tokyo.tkw.thinmp.activity.AlbumsActivity;
import tokyo.tkw.thinmp.activity.ArtistsActivity;
import tokyo.tkw.thinmp.activity.FavoriteArtistsActivity;
import tokyo.tkw.thinmp.activity.FavoriteSongsActivity;
import tokyo.tkw.thinmp.activity.PlaylistsActivity;
import tokyo.tkw.thinmp.activity.TracksActivity;

public enum MainMenuEnum {
    ARTISTS("Artists", ArtistsActivity.class),
    ALBUMS("Albums", AlbumsActivity.class),
    SONGS("Songs", TracksActivity.class),
    FAVORITE_ARTISTS("Favorite Artists", FavoriteArtistsActivity.class),
    FAVORITE_SONGS("Favorite Songs", FavoriteSongsActivity.class),
    PLAYLISTS("Playlists", PlaylistsActivity.class);

    private final Class<?> link;
    private final String label;

    MainMenuEnum(String label, Class<?> link) {
        this.label = label;
        this.link = link;
    }

    public String label() {
        return label;
    }

    public Class<?> link() {
        return link;
    }
}
