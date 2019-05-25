package tokyo.tkw.thinmp.viewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import tokyo.tkw.thinmp.R;

public class LibraryViewHolder extends RecyclerView.ViewHolder {
    public TextView libraryItem;

    public LibraryViewHolder(View view) {
        super(view);

        libraryItem = view.findViewById(R.id.primaryText);
    }
}
