package tokyo.tkw.thinmp.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import tokyo.tkw.thinmp.R;

public class AlbumTrackListViewHolder extends RecyclerView.ViewHolder {
    public TextView mTitleView;
    public View mPopupMenu;


    public AlbumTrackListViewHolder(View view) {
        super(view);

        mTitleView = view.findViewById(R.id.title);
        mPopupMenu = view.findViewById(R.id.popup_album_menu);
    }
}