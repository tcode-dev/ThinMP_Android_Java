package tokyo.tkw.thinmp.epoxy.model;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyHolder;
import com.airbnb.epoxy.EpoxyModelClass;
import com.airbnb.epoxy.EpoxyModelWithHolder;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.util.GlideUtil;

@EpoxyModelClass(layout = R.layout.list_item_linear_edit_track)
public abstract class TrackEditModel extends EpoxyModelWithHolder<TrackEditModel.Holder> {
    @EpoxyAttribute
    String albumArtId;
    @EpoxyAttribute
    String primaryText;
    @EpoxyAttribute
    String secondaryText;

    @Override
    public void bind(@NonNull Holder holder) {
        GlideUtil.bitmap(albumArtId, holder.albumArt);
        holder.primaryText.setText(primaryText);
        holder.secondaryText.setText(secondaryText);
    }

    static class Holder extends EpoxyHolder {
        ImageView albumArt;
        TextView primaryText;
        TextView secondaryText;

        @Override
        protected void bindView(@NonNull View itemView) {
            albumArt = itemView.findViewById(R.id.albumArt);
            primaryText = itemView.findViewById(R.id.primaryText);
            secondaryText = itemView.findViewById(R.id.secondaryText);
        }
    }
}
