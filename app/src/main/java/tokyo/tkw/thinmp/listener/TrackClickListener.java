package tokyo.tkw.thinmp.listener;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;

import java.util.ArrayList;

import tokyo.tkw.thinmp.model.Track;
import tokyo.tkw.thinmp.player.MusicController;

public class TrackClickListener implements AdapterView.OnItemClickListener {
    private Activity mContext;
    private ArrayList<Track> mTrackList;

    public TrackClickListener(Activity context, ArrayList<Track> trackList) {
        mContext = context;
        mTrackList = trackList;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MusicController.set(mContext, mTrackList, position);
        MusicController.start();
    }
}
