package tokyo.tkw.thinmp.viewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

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