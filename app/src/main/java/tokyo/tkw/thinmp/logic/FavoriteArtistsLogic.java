package tokyo.tkw.thinmp.logic;

import android.content.Context;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.dto.FavoriteArtistsDto;
import tokyo.tkw.thinmp.favorite.FavoriteArtists;

public class FavoriteArtistsLogic {
    private Context context;
    private FavoriteArtists favoriteArtists;

    private FavoriteArtistsLogic(Context context) {
        this.context = context;
        this.favoriteArtists = FavoriteArtists.createInstance(context);
    }

    public static FavoriteArtistsLogic createInstance(Context context) {
        return new FavoriteArtistsLogic(context);
    }

    public FavoriteArtistsDto createDto() {
        FavoriteArtistsDto dto = new FavoriteArtistsDto();

        dto.title = context.getResources().getString(R.string.favorite_artists);
        dto.artistList = favoriteArtists.getSortedArtistList();

        return dto;
    }
}
