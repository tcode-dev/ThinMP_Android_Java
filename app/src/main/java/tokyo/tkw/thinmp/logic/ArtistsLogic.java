package tokyo.tkw.thinmp.logic;

import android.content.Context;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.dto.ArtistsDto;
import tokyo.tkw.thinmp.music.Artists;

public class ArtistsLogic {
    private Context context;
    private Artists artists;

    private ArtistsLogic(Context context) {
        this.context = context;
        this.artists = Artists.createInstance(context);
    }

    public static ArtistsLogic createInstance(Context context) {
        return new ArtistsLogic(context);
    }

    public ArtistsDto createDto() {
        ArtistsDto dto = new ArtistsDto();

        dto.title = context.getResources().getString(R.string.artists);
        dto.artistList = artists.getArtistList();

        return dto;
    }
}
