package tokyo.tkw.thinmp.logic;

import android.content.Context;

import tokyo.tkw.thinmp.dto.FavoriteArtistDto;
import tokyo.tkw.thinmp.dto.FavoriteArtistsDto;
import tokyo.tkw.thinmp.favorite.FavoriteArtists;

public class FavoriteArtistsEditLogic {
    private FavoriteArtists favoriteArtists;

    private FavoriteArtistsEditLogic(Context context) {
        favoriteArtists = new FavoriteArtists(context);
    }

    public static FavoriteArtistsEditLogic createInstance(Context context) {
        return new FavoriteArtistsEditLogic(context);
    }

    public FavoriteArtistDto createDto() {
        FavoriteArtistDto dto = new FavoriteArtistDto();

        dto.favoriteList = favoriteArtists.getFavoriteArtistRealmList();
        dto.artistMap = favoriteArtists.getArtistMap();

        return dto;
    }
}
