package tokyo.tkw.thinmp.logic;

import android.content.Context;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.dto.FavoriteSongsDto;
import tokyo.tkw.thinmp.favorite.FavoriteSongs;

public class FavoriteSongsLogic {
    private Context context;
    private FavoriteSongs favoriteSongs;

    private FavoriteSongsLogic(Context context) {
        this.context = context;
        this.favoriteSongs = FavoriteSongs.createInstance(context);
    }

    public static FavoriteSongsLogic createInstance(Context context) {
        return new FavoriteSongsLogic(context);
    }

    public FavoriteSongsDto createDto() {
        FavoriteSongsDto dto = new FavoriteSongsDto();

        dto.title = context.getResources().getString(R.string.favorite_songs);
        dto.trackList = favoriteSongs.getSortedTrackList();

        return dto;
    }
}
