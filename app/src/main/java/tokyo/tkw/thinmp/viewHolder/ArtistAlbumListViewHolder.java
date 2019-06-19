package tokyo.tkw.thinmp.viewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import tokyo.tkw.thinmp.R;

public class ArtistAlbumListViewHolder extends RecyclerView.ViewHolder {
    public ImageView albumArt;
    public TextView albumName;


    public ArtistAlbumListViewHolder(View view) {
        super(view);

        albumArt = view.findViewById(R.id.albumArt);
        albumName = view.findViewById(R.id.primaryText);
    }
}
