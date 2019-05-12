package tokyo.tkw.thinmp.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import tokyo.tkw.thinmp.R;

public class TitleViewHolder extends RecyclerView.ViewHolder {
    public TextView title;

    public TitleViewHolder(View view) {
        super(view);

        title = view.findViewById(R.id.title);
    }
}