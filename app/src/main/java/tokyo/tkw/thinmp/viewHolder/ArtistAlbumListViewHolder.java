package tokyo.tkw.thinmp.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import tokyo.tkw.thinmp.R;

public class ArtistAlbumListViewHolder extends RecyclerView.ViewHolder {
    public ImageView thumbnail;
    public TextView albumName;


    public ArtistAlbumListViewHolder(View view) {
        super(view);

        thumbnail = view.findViewById(R.id.thumbnail);
        albumName = view.findViewById(R.id.albumName);
    }
}
