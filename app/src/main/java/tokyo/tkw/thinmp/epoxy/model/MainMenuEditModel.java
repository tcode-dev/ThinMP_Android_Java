package tokyo.tkw.thinmp.epoxy.model;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

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
    @EpoxyAttribute
    boolean visibility;
    @EpoxyAttribute(DoNotHash)
    CompoundButton.OnCheckedChangeListener changeListener;

    @Override
    public void bind(@NonNull MainMenuEditModel.Holder holder) {
        holder.checkBox.setText(primaryText);
        holder.checkBox.setChecked(visibility);
        holder.checkBox.setOnCheckedChangeListener(changeListener);
    }

    static class Holder extends EpoxyHolder {
        CheckBox checkBox;

        @Override
        protected void bindView(@NonNull View itemView) {
            checkBox = itemView.findViewById(R.id.checkBox);
        }
    }
}
