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

@EpoxyModelClass(layout = R.layout.list_item_col_artist_album)
public abstract class ArtistAlbumModel extends EpoxyModelWithHolder<ArtistAlbumModel.Holder> {
    @EpoxyAttribute
    Optional<String> albumArtId;
    @EpoxyAttribute
    String albumName;
    @EpoxyAttribute(DoNotHash)
    OnClickListener albumClickListener;

    @Override
    public void bind(@NonNull Holder holder) {
        holder.parent.setOnClickListener(albumClickListener);
        GlideUtil.bitmap(albumArtId, holder.albumArt);
        holder.primaryText.setText(albumName);
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
