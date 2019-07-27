package tokyo.tkw.thinmp.epoxy.model;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyHolder;
import com.airbnb.epoxy.EpoxyModelWithHolder;
import com.annimon.stream.Optional;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.util.GlideUtil;

import static com.airbnb.epoxy.EpoxyAttribute.Option.DoNotHash;

public abstract class ShortcutModel extends EpoxyModelWithHolder<ShortcutModel.Holder> {
    @EpoxyAttribute
    String albumArtId;
    @EpoxyAttribute
    String primaryText;
    @EpoxyAttribute
    String secondaryText;
    @EpoxyAttribute(DoNotHash)
    View.OnClickListener clickListener;

    @Override
    public void bind(@NonNull ShortcutModel.Holder holder) {
        GlideUtil.bitmap(albumArtId, holder.albumArt);
        holder.primaryText.setText(primaryText);
        holder.secondaryText.setText(secondaryText);
        holder.parent.setOnClickListener(clickListener);
    }

    static class Holder extends EpoxyHolder {
        View parent;
        ImageView albumArt;
        TextView primaryText;
        TextView secondaryText;

        @Override
        protected void bindView(@NonNull View itemView) {
            parent = itemView;
            primaryText = itemView.findViewById(R.id.primaryText);
            secondaryText = itemView.findViewById(R.id.secondaryText);
            albumArt = itemView.findViewById(R.id.albumArt);
        }
    }
}
