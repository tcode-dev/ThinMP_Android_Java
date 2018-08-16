package tokyo.tkw.thinmp.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import tokyo.tkw.thinmp.R;

public class AlbumListViewHolder extends RecyclerView.ViewHolder {
    public ImageView thumbnail;
    public TextView albumName;
    public TextView artistName;


    public AlbumListViewHolder(View view) {
        super(view);

        thumbnail = view.findViewById(R.id.thumbnail);
        albumName = view.findViewById(R.id.albumName);
        artistName = view.findViewById(R.id.artistName);
    }
}
