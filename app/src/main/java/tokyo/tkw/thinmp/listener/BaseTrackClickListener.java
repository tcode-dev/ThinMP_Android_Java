package tokyo.tkw.thinmp.listener;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.fragment.MiniPlayerFragment;
import tokyo.tkw.thinmp.menu.TrackMenu;

public abstract class BaseTrackClickListener implements ITrackClickListener {
    @Override
    public void onClickTrack(View view, int position) {
        Fragment fragment = ((AppCompatActivity) view.getContext())
                .getSupportFragmentManager()
                .findFragmentById(R.id.includeMiniPlayer);

        if (fragment instanceof MiniPlayerFragment) {
            ((MiniPlayerFragment) fragment).start(getTrackList(view.getContext()), position);
        }
    }

    @Override
    public void onClickMenu(View view, int position) {
        String trackId = getTrackList(view.getContext()).get(position).getId();
        TrackMenu trackMenu = new TrackMenu(view, trackId);
        trackMenu.show();
    }
}
