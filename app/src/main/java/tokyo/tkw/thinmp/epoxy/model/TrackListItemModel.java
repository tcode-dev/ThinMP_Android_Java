package tokyo.tkw.thinmp.epoxy.model;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyHolder;
import com.airbnb.epoxy.EpoxyModelClass;
import com.airbnb.epoxy.EpoxyModelWithHolder;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.util.GlideUtil;

import static com.airbnb.epoxy.EpoxyAttribute.Option.DoNotHash;

@EpoxyModelClass(layout = R.layout.artist_track_list_item)
public abstract class TrackListItemModel extends EpoxyModelWithHolder<TrackListItemModel.Holder> {
    @EpoxyAttribute
    String albumArtId;
    @EpoxyAttribute
    String trackName;
    @EpoxyAttribute(DoNotHash)
    OnClickListener clickListener;

    @Override
    public void bind(@NonNull Holder holder) {
        holder.parent.setOnClickListener(clickListener);
        GlideUtil.bitmap(albumArtId, holder.albumArt);
        holder.trackName.setText(trackName);
    }

    static class Holder extends EpoxyHolder {
        View parent;
        ImageView albumArt;
        TextView trackName;

        @Override
        protected void bindView(@NonNull View itemView) {
            parent = itemView;
            albumArt = itemView.findViewById(R.id.albumArt);
            trackName = itemView.findViewById(R.id.primaryText);
        }
    }
}
