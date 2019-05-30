package tokyo.tkw.thinmp.epoxy.model;

import android.graphics.Bitmap;
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

import static com.airbnb.epoxy.EpoxyAttribute.Option.DoNotHash;

@EpoxyModelClass(layout = R.layout.artist_list_item)
public abstract class ArtistListItemModel extends EpoxyModelWithHolder<ArtistListItemModel.Holder> {
    @EpoxyAttribute Bitmap thumbnail;
    @EpoxyAttribute String artistName;
    @EpoxyAttribute(DoNotHash) OnClickListener clickListener;

    @Override
    public void bind(@NonNull Holder holder) {
        holder.parent.setOnClickListener(clickListener);
        holder.thumbnail.setImageBitmap(thumbnail);
        holder.artistName.setText(artistName);
    }

    static class Holder extends EpoxyHolder {
        View parent;
        ImageView thumbnail;
        TextView artistName;

        @Override
        protected void bindView(@NonNull View itemView) {
            parent = itemView;
            thumbnail = itemView.findViewById(R.id.thumbnail);
            artistName = itemView.findViewById(R.id.artistName);
        }
    }
}
