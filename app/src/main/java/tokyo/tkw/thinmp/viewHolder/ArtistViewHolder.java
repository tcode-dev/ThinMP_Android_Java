package tokyo.tkw.thinmp.viewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import tokyo.tkw.thinmp.R;

public class ArtistViewHolder extends RecyclerView.ViewHolder {
    public ImageView thumbnail;
    public TextView artistName;

    public ArtistViewHolder(View view) {
        super(view);

        thumbnail = view.findViewById(R.id.thumbnail);
        artistName = view.findViewById(R.id.artistName);
    }
}
