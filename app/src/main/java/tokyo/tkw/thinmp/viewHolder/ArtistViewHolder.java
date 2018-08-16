package tokyo.tkw.thinmp.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import tokyo.tkw.thinmp.R;

public class ArtistViewHolder extends RecyclerView.ViewHolder {
    public ImageView thumbnail;
    public TextView track;
    public TextView artist;

    public ArtistViewHolder(View view) {
        super(view);

        thumbnail = view.findViewById(R.id.thumbnail);
        artist = view.findViewById(R.id.artist);
    }
}
