package tokyo.tkw.thinmp.listener;

import android.app.Activity;
import android.view.View;

import java.util.ArrayList;

import tokyo.tkw.thinmp.adapter.TrackListAdapter;
import tokyo.tkw.thinmp.music.Track;

public class TrackClickListener implements View.OnClickListener {
    private Activity mContext;
    private ArrayList<Track> mTrackList;
    private int mPosition;
    private TrackListAdapter.OnTrackListItemClickListener mListener;

    public TrackClickListener(Activity context, ArrayList<Track> trackList, int position, TrackListAdapter.OnTrackListItemClickListener listener) {
        mContext = context;
        mTrackList = trackList;
        mPosition = position;
        mListener = listener;
    }

    @Override
    public void onClick(View view) {
        mListener.OnClickItem(mPosition);
    }
}
