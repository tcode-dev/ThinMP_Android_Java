package tokyo.tkw.thinmp.epoxy.model;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyHolder;
import com.airbnb.epoxy.EpoxyModelWithHolder;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.util.GlideUtil;

import static com.airbnb.epoxy.EpoxyAttribute.Option.DoNotHash;

public abstract class ShortcutModel extends EpoxyModelWithHolder<ShortcutModel.Holder> {
    @EpoxyAttribute
    String name;
    @EpoxyAttribute
    String type;
    @EpoxyAttribute
    String albumArtId;
    @EpoxyAttribute(DoNotHash)
    View.OnClickListener clickListener;

    @Override
    public void bind(@NonNull ShortcutModel.Holder holder) {
        holder.name.setText(name);
        holder.type.setText(type);
        GlideUtil.bitmap(albumArtId, holder.albumArt);
        holder.parent.setOnClickListener(clickListener);
    }

    static class Holder extends EpoxyHolder {
        View parent;
        TextView name;
        TextView type;
        ImageView albumArt;

        @Override
        protected void bindView(@NonNull View itemView) {
            parent = itemView;
            name = itemView.findViewById(R.id.primaryText);
            type = itemView.findViewById(R.id.secondaryText);
            albumArt = itemView.findViewById(R.id.albumArt);
        }
    }
}
