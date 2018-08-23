package tokyo.tkw.thinmp.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import tokyo.tkw.thinmp.R;

public class ImageRowViewHolder extends RecyclerView.ViewHolder {
    public ImageView thumbnail;
    public TextView primaryText;

    public ImageRowViewHolder(View view) {
        super(view);

        thumbnail = view.findViewById(R.id.thumbnail);
        primaryText = view.findViewById(R.id.primaryText);
    }
}
