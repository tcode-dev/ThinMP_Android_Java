package tokyo.tkw.thinmp.listener;

import android.content.Context;
import android.view.View;

import java.util.List;

import tokyo.tkw.thinmp.track.Track;

public interface ITrackClickListener {
    List<Track> getTrackList(Context context);

    void onClickTrack(View view, int position);

    void onClickMenu(View view, int position);
}
