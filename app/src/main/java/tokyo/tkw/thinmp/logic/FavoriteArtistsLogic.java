package tokyo.tkw.thinmp.logic;

import android.content.Context;

import tokyo.tkw.thinmp.dto.FavoriteArtistsDto;
import tokyo.tkw.thinmp.favorite.FavoriteArtists;

public class FavoriteArtistsLogic {
    private FavoriteArtists favoriteArtists;

    private FavoriteArtistsLogic(Context context) {
        favoriteArtists = new FavoriteArtists(context);
    }

    public static FavoriteArtistsLogic createInstance(Context context) {
        return new FavoriteArtistsLogic(context);
    }

    public FavoriteArtistsDto createDto() {
        FavoriteArtistsDto dto = new FavoriteArtistsDto();

        dto.realmResults = favoriteArtists.getRealmResults();
        dto.artistMap = favoriteArtists.getArtistMap();

        return dto;
    }
}
