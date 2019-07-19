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
import com.annimon.stream.Optional;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.util.GlideUtil;

import static com.airbnb.epoxy.EpoxyAttribute.Option.DoNotHash;

@EpoxyModelClass(layout = R.layout.list_item_row_artist)
public abstract class ArtistListItemModel extends EpoxyModelWithHolder<ArtistListItemModel.Holder> {
    @EpoxyAttribute
    Optional<String> albumArtId;
    @EpoxyAttribute
    String artistName;
    @EpoxyAttribute(DoNotHash)
    OnClickListener clickListener;

    @Override
    public void bind(@NonNull Holder holder) {
        holder.parent.setOnClickListener(clickListener);
        GlideUtil.bitmap(albumArtId, holder.albumArt, GlideUtil.ARTIST_RESOURCE_ID);
        holder.primaryText.setText(artistName);
    }

    static class Holder extends EpoxyHolder {
        View parent;
        ImageView albumArt;
        TextView primaryText;

        @Override
        protected void bindView(@NonNull View itemView) {
            parent = itemView;
            albumArt = itemView.findViewById(R.id.albumArt);
            primaryText = itemView.findViewById(R.id.primaryText);
        }
    }
}
