package tokyo.tkw.thinmp.logic;

import android.content.Context;

import tokyo.tkw.thinmp.dto.FavoriteSongsEditDto;
import tokyo.tkw.thinmp.favorite.FavoriteSongs;

public class FavoriteSongsEditLogic {
    private FavoriteSongs favoriteSongs;

    private FavoriteSongsEditLogic(Context context) {
        favoriteSongs = new FavoriteSongs(context);
    }

    public static FavoriteSongsEditLogic createInstance(Context context) {
        return new FavoriteSongsEditLogic(context);
    }

    public FavoriteSongsEditDto createDto() {
        FavoriteSongsEditDto dto = new FavoriteSongsEditDto();

        dto.trackList = favoriteSongs.getSortedTrackList();

        return dto;
    }
}
