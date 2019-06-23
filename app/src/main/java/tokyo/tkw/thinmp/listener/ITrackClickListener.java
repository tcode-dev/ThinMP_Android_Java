package tokyo.tkw.thinmp.listener;

import android.content.Context;
import android.view.View;

import java.util.ArrayList;

import tokyo.tkw.thinmp.music.Track;

public interface ITrackClickListener {
    ArrayList<Track> getTrackList(Context context);

    void onClickTrack(View view, int position);

    void onClickMenu(View view, int position);
}
