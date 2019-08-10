package tokyo.tkw.thinmp.viewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import tokyo.tkw.thinmp.R;

public class TrackViewHolder extends RecyclerView.ViewHolder {
    public ImageView albumArt;
    public TextView primaryText;
    public TextView secondaryText;

    public TrackViewHolder(View view) {
        super(view);

        albumArt = view.findViewById(R.id.albumArt);
        primaryText = view.findViewById(R.id.primaryText);
        secondaryText = view.findViewById(R.id.secondaryText);
    }
}