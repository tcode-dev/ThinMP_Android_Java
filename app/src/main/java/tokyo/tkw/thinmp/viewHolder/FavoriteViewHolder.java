package tokyo.tkw.thinmp.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import tokyo.tkw.thinmp.R;

public class FavoriteViewHolder extends RecyclerView.ViewHolder {
    public ImageView thumbnail;
    public TextView track;
    public TextView artist;

    public FavoriteViewHolder(View itemView) {
        super(itemView);

        thumbnail = itemView.findViewById(R.id.thumbnail);
        track = itemView.findViewById(R.id.track);
        artist = itemView.findViewById(R.id.artist);
    }
}