package tokyo.tkw.thinmp.listener;

import android.content.Context;

import java.util.List;

import tokyo.tkw.thinmp.track.Track;

public class TrackClickListener extends BaseTrackClickListener {
    private List<Track> mTrackList;

    public TrackClickListener(List<Track> trackList) {
        mTrackList = trackList;
    }

    @Override
    public List<Track> getTrackList(Context context) {
        return mTrackList;
    }
}
