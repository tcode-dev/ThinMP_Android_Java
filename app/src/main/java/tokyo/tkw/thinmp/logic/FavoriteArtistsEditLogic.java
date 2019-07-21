package tokyo.tkw.thinmp.logic;

import android.content.Context;

import tokyo.tkw.thinmp.dto.FavoriteArtistsEditDto;
import tokyo.tkw.thinmp.favorite.FavoriteArtists;

public class FavoriteArtistsEditLogic {
    private FavoriteArtists favoriteArtists;

    private FavoriteArtistsEditLogic(Context context) {
        favoriteArtists = new FavoriteArtists(context);
    }

    public static FavoriteArtistsEditLogic createInstance(Context context) {
        return new FavoriteArtistsEditLogic(context);
    }

    public FavoriteArtistsEditDto createDto() {
        FavoriteArtistsEditDto dto = new FavoriteArtistsEditDto();

        dto.artistList = favoriteArtists.getArtistList();

        return dto;
    }
}
