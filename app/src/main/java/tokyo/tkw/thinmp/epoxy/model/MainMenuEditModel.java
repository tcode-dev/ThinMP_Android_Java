package tokyo.tkw.thinmp.epoxy.model;

import android.view.View;
import android.widget.CheckBox;

import androidx.annotation.NonNull;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyHolder;
import com.airbnb.epoxy.EpoxyModelClass;
import com.airbnb.epoxy.EpoxyModelWithHolder;

import tokyo.tkw.thinmp.R;

import static com.airbnb.epoxy.EpoxyAttribute.Option.DoNotHash;

@EpoxyModelClass(layout = R.layout.list_item_linear_edit_main_menu)
public abstract class MainMenuEditModel extends EpoxyModelWithHolder<MainMenuEditModel.Holder> {
    @EpoxyAttribute
    String primaryText;
    @EpoxyAttribute(DoNotHash)
    View.OnClickListener clickListener;

    @Override
    public void bind(@NonNull MainMenuEditModel.Holder holder) {
        holder.primaryText.setText(primaryText);
        holder.parent.setOnClickListener(clickListener);
    }

    static class Holder extends EpoxyHolder {
        View parent;
        CheckBox primaryText;

        @Override
        protected void bindView(@NonNull View itemView) {
            parent = itemView;
            primaryText = itemView.findViewById(R.id.primaryText);
        }
    }
}
