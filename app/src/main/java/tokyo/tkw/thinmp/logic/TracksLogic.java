package tokyo.tkw.thinmp.logic;

import android.content.Context;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.dto.TracksDto;
import tokyo.tkw.thinmp.music.TrackCollection;

public class TracksLogic {
    private Context context;
    private TrackCollection trackCollection;

    private TracksLogic(Context context) {
        this.context = context;
        this.trackCollection = TrackCollection.createAllTrackCollectionInstance(context);
    }

    public static TracksLogic createInstance(Context context) {
        return new TracksLogic(context);
    }

    public TracksDto createDto() {
        TracksDto dto = new TracksDto();

        dto.title =context.getResources().getString(R.string.songs);
        dto.trackList = trackCollection.getList();

        return dto;
    }
}
