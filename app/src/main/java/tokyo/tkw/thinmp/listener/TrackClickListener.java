package tokyo.tkw.thinmp.listener;

import android.content.Context;

import java.util.ArrayList;

import tokyo.tkw.thinmp.music.Track;

public class TrackClickListener extends BaseTrackClickListener {
    private ArrayList<Track> mTrackList;

    public TrackClickListener(ArrayList<Track> trackList) {
        mTrackList = trackList;
    }

    @Override
    public ArrayList<Track> getTrackList(Context context) {
        return mTrackList;
    }
}
