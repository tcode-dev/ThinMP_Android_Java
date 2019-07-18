package tokyo.tkw.thinmp.logic;

import android.content.Context;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.dto.TracksDto;
import tokyo.tkw.thinmp.track.Tracks;

public class TracksLogic {
    private Context context;
    private Tracks tracks;

    private TracksLogic(Context context) {
        this.context = context;
        this.tracks = Tracks.createInstance(context);
    }

    public static TracksLogic createInstance(Context context) {
        return new TracksLogic(context);
    }

    public TracksDto createDto() {
        TracksDto dto = new TracksDto();

        dto.title = context.getResources().getString(R.string.songs);
        dto.trackList = tracks.getTrackList();

        return dto;
    }
}
