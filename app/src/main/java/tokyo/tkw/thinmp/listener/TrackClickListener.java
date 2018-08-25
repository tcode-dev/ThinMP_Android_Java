package tokyo.tkw.thinmp.listener;

import android.app.Activity;
import android.view.View;

import java.util.ArrayList;

import tokyo.tkw.thinmp.music.Track;
import tokyo.tkw.thinmp.player.MusicManager;

public class TrackClickListener implements View.OnClickListener {
    private Activity mContext;
    private ArrayList<Track> mTrackList;
    private int mPosition;

    public TrackClickListener(Activity context, ArrayList<Track> trackList, int position) {
        mContext = context;
        mTrackList = trackList;
        mPosition = position;
    }

    @Override
    public void onClick(View view) {
        MusicManager.set(mContext, mTrackList, mPosition);
        MusicManager.start();
    }
}
