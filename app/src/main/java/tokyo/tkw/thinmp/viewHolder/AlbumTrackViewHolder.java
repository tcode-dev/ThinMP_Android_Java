package tokyo.tkw.thinmp.viewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import tokyo.tkw.thinmp.R;

public class AlbumTrackViewHolder extends RecyclerView.ViewHolder {
    public TextView primaryText;
    public View menu;


    public AlbumTrackViewHolder(View view) {
        super(view);

        primaryText = view.findViewById(R.id.primaryText);
        menu = view.findViewById(R.id.menu);
    }
}