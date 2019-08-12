package tokyo.tkw.thinmp.epoxy.model;

import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyHolder;
import com.airbnb.epoxy.EpoxyModelClass;
import com.airbnb.epoxy.EpoxyModelWithHolder;

import tokyo.tkw.thinmp.R;

@EpoxyModelClass(layout = R.layout.edit_playlist_name)
public abstract class PlaylistNameEditModel extends EpoxyModelWithHolder<PlaylistNameEditModel.Holder> {
    @EpoxyAttribute
    String playlistName;
    @Override
    public void bind(@NonNull Holder holder) {
        holder.playlistName.setText(playlistName);
    }

    static class Holder extends EpoxyHolder {
        EditText playlistName;
        @Override
        protected void bindView(@NonNull View itemView) {
            playlistName = itemView.findViewById(R.id.playlistName);
        }
    }
}
