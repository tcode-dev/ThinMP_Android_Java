package tokyo.tkw.thinmp.viewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import tokyo.tkw.thinmp.R;

public class TitleViewHolder extends RecyclerView.ViewHolder {
    public TextView title;

    public TitleViewHolder(View view) {
        super(view);

        title = view.findViewById(R.id.title);
    }
}