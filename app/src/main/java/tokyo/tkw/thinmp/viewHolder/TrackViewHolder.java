package tokyo.tkw.thinmp.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import tokyo.tkw.thinmp.R;

public class TrackViewHolder extends RecyclerView.ViewHolder {
    public ImageView thumbnail;
    public TextView track;
    public TextView artist;
    public ImageView menu;

    public TrackViewHolder(View view) {
        super(view);

        thumbnail = view.findViewById(R.id.thumbnail);
        track = view.findViewById(R.id.track);
        artist = view.findViewById(R.id.artist);
        menu = view.findViewById(R.id.menu);
    }
}