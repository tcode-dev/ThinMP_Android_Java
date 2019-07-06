package tokyo.tkw.thinmp.listener;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.List;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.fragment.MiniPlayerFragment;
import tokyo.tkw.thinmp.music.Track;

public class EpoxyTrackClickListener implements View.OnClickListener {
    private List<Track> mTrackList;
    private int mPosition;

    public EpoxyTrackClickListener(List<Track> trackList, int position) {
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
