package tokyo.tkw.thinmp.viewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import tokyo.tkw.thinmp.R;

public class ArtistViewHolder extends RecyclerView.ViewHolder {
    public ImageView thumbnail;
    public TextView artist;

    public ArtistViewHolder(View view) {
        super(view);

        thumbnail = view.findViewById(R.id.thumbnail);
        artist = view.findViewById(R.id.primaryText);
    }
}
