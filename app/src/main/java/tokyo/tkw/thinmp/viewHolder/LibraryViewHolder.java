package tokyo.tkw.thinmp.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import tokyo.tkw.thinmp.R;

public class LibraryViewHolder extends RecyclerView.ViewHolder {
    public TextView libraryItem;

    public LibraryViewHolder(View view) {
        super(view);

        libraryItem = view.findViewById(R.id.primaryText);
    }
}
