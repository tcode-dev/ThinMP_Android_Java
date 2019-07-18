package tokyo.tkw.thinmp.music;

import android.content.Context;

import java.util.List;

import tokyo.tkw.thinmp.provider.TrackContentProvider;

public class TrackCollection {
    private List<Track> mTrackList;

    private TrackCollection(List<Track> trackList) {
        mTrackList = trackList;
    }

    public static TrackCollection createAllTrackCollectionInstance(Context context) {
        TrackContentProvider provider = new TrackContentProvider(context);

        return new TrackCollection(provider.findAll());
    }

    public List<Track> getList() {
        return mTrackList;
    }
}
