package tokyo.tkw.thinmp.epoxy.model;

import android.graphics.Bitmap;
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
import com.squareup.picasso.Picasso;

import tokyo.tkw.thinmp.R;

import static android.net.Uri.parse;
import static com.airbnb.epoxy.EpoxyAttribute.Option.DoNotHash;

@EpoxyModelClass(layout = R.layout.panel)
public abstract class AlbumListItemModel extends EpoxyModelWithHolder<AlbumListItemModel.Holder> {
    @EpoxyAttribute
    String thumbnailId;
    @EpoxyAttribute
    String albumName;
    @EpoxyAttribute(DoNotHash)
    OnClickListener clickListener;

    @Override
    public void bind(@NonNull Holder holder) {
        holder.parent.setOnClickListener(clickListener);
        Uri uri = parse("content://media/external/audio/albumart/" + thumbnailId);
        Picasso.get().load(uri).into(holder.thumbnail);
        holder.albumName.setText(albumName);
    }

    static class Holder extends EpoxyHolder {
        View parent;
        ImageView thumbnail;
        TextView albumName;

        @Override
        protected void bindView(@NonNull View itemView) {
            parent = itemView;
            thumbnail = itemView.findViewById(R.id.thumbnail);
            albumName = itemView.findViewById(R.id.primaryText);
        }
    }
}
