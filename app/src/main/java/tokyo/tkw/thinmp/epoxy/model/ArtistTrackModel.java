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

@EpoxyModelClass(layout = R.layout.list_item_row_artist_track)
public abstract class ArtistTrackModel extends EpoxyModelWithHolder<ArtistTrackModel.Holder> {
    @EpoxyAttribute
    Optional<String> albumArtId;
    @EpoxyAttribute
    String trackName;
    @EpoxyAttribute(DoNotHash)
    OnClickListener trackClickListener;
    @EpoxyAttribute(DoNotHash)
    OnClickListener menuClickListener;

    @Override
    public void bind(@NonNull Holder holder) {
        GlideUtil.bitmap(albumArtId, holder.albumArt);
        holder.trackName.setText(trackName);
        holder.parent.setOnClickListener(trackClickListener);
        holder.menu.setOnClickListener(menuClickListener);
    }

    static class Holder extends EpoxyHolder {
        View parent;
        ImageView albumArt;
        TextView trackName;
        ImageView menu;

        @Override
        protected void bindView(@NonNull View itemView) {
            parent = itemView;
            albumArt = itemView.findViewById(R.id.albumArt);
            trackName = itemView.findViewById(R.id.primaryText);
            menu = itemView.findViewById(R.id.menu);
        }
    }
}
