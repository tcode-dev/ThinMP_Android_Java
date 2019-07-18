package tokyo.tkw.thinmp.logic;

import android.content.Context;

import tokyo.tkw.thinmp.dto.FavoriteSongsDto;
import tokyo.tkw.thinmp.favorite.FavoriteSongs;

public class FavoriteSongsLogic {
    private FavoriteSongs favoriteSongs;

    private FavoriteSongsLogic(Context context) {
        favoriteSongs = new FavoriteSongs(context);
    }

    public static FavoriteSongsLogic createInstance(Context context) {
        return new FavoriteSongsLogic(context);
    }

    public FavoriteSongsDto createDto() {
        FavoriteSongsDto dto = new FavoriteSongsDto();

        dto.realmResults = favoriteSongs.getRealmResults();
        dto.trackMap = favoriteSongs.getTrackMap();

        return dto;
    }
}
