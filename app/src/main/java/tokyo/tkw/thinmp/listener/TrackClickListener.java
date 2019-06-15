package tokyo.tkw.thinmp.listener;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.fragment.MiniPlayerFragment;
import tokyo.tkw.thinmp.music.Track;

public class TrackClickListener implements View.OnClickListener {
    private ArrayList<Track> mTrackList;
    private int mPosition;

    public TrackClickListener(ArrayList<Track> trackList, int position) {
        mTrackList = trackList;
        mPosition = position;
    }

    @Override
    public void onClick(View v) {
        Fragment fragment =
                ((AppCompatActivity) v.getContext()).getSupportFragmentManager().findFragmentById(R.id.includeMiniPlayer);
        if (fragment instanceof MiniPlayerFragment) {
            ((MiniPlayerFragment) fragment).start(mTrackList, mPosition);
        }
    }
}
