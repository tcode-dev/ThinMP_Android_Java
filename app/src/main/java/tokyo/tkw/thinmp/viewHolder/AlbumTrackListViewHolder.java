package tokyo.tkw.thinmp.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import tokyo.tkw.thinmp.R;

public class AlbumTrackListViewHolder extends RecyclerView.ViewHolder {
    public TextView title;
    public View menu;


    public AlbumTrackListViewHolder(View view) {
        super(view);

        title = view.findViewById(R.id.title);
        menu = view.findViewById(R.id.menu);
    }
}