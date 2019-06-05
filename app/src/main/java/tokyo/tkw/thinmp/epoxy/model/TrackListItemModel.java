package tokyo.tkw.thinmp.epoxy.model;

import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyHolder;
import com.airbnb.epoxy.EpoxyModelClass;
import com.airbnb.epoxy.EpoxyModelWithHolder;
import com.bumptech.glide.Glide;

import tokyo.tkw.thinmp.R;

import static android.net.Uri.parse;
import static com.airbnb.epoxy.EpoxyAttribute.Option.DoNotHash;

@EpoxyModelClass(layout = R.layout.artist_track_list_item)
public abstract class TrackListItemModel extends EpoxyModelWithHolder<TrackListItemModel.Holder> {
    @EpoxyAttribute
    String thumbnailId;
    @EpoxyAttribute
    String trackName;
    @EpoxyAttribute(DoNotHash)
    OnClickListener clickListener;

    @Override
    public void bind(@NonNull Holder holder) {
        holder.parent.setOnClickListener(clickListener);
        Uri uri = parse("content://media/external/audio/albumart/" + thumbnailId);
        Glide.with(holder.thumbnail).load(uri).into(holder.thumbnail);
        holder.trackName.setText(trackName);
    }

    static class Holder extends EpoxyHolder {
        View parent;
        ImageView thumbnail;
        TextView trackName;

        @Override
        protected void bindView(@NonNull View itemView) {
            parent = itemView;
            thumbnail = itemView.findViewById(R.id.thumbnail);
            trackName = itemView.findViewById(R.id.primaryText);
        }
    }
}
