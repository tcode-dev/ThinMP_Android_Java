package tokyo.tkw.thinmp.viewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import tokyo.tkw.thinmp.R;

public class ArtistViewHolder extends RecyclerView.ViewHolder {
    public ImageView albumArt;
    public TextView artistName;

    public ArtistViewHolder(View view) {
        super(view);

        albumArt = view.findViewById(R.id.albumArt);
        artistName = view.findViewById(R.id.primaryText);
    }
}
