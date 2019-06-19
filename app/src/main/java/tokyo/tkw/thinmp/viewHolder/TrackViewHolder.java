package tokyo.tkw.thinmp.viewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import tokyo.tkw.thinmp.R;

public class TrackViewHolder extends RecyclerView.ViewHolder {
    public ImageView albumArt;
    public TextView track;
    public TextView artist;
    public ImageView menu;

    public TrackViewHolder(View view) {
        super(view);

        albumArt = view.findViewById(R.id.albumArt);
        track = view.findViewById(R.id.primaryText);
        artist = view.findViewById(R.id.secondaryText);
        menu = view.findViewById(R.id.menu);
    }
}