package tokyo.tkw.thinmp.music;

import android.content.Context;

import java.util.List;

import tokyo.tkw.thinmp.provider.TrackContentProvider;

public class Tracks {
    private TrackContentProvider provider;

    private Tracks(Context context) {
        provider = new TrackContentProvider(context);
    }

    public static Tracks createInstance(Context context) {
        return new Tracks(context);
    }

    public List<Track> getTrackList() {
        return provider.findAll();
    }
}
