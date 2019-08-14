package tokyo.tkw.thinmp.epoxy.model;

import androidx.annotation.NonNull;

import com.airbnb.epoxy.EpoxyModelClass;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.util.GlideUtil;

@EpoxyModelClass(layout = R.layout.list_item_grid_shortcut_album)
public abstract class ShortcutAlbumModel extends ShortcutModel {

    @Override
    public void bind(@NonNull ShortcutModel.Holder holder) {
        GlideUtil.bitmap(albumArtId, holder.albumArt, GlideUtil.ALBUM_RESOURCE_ID);
        holder.primaryText.setText(primaryText);
        holder.secondaryText.setText(secondaryText);
        holder.parent.setOnClickListener(clickListener);
    }
}
