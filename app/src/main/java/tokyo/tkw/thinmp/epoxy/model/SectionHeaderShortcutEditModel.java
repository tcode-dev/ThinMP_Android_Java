package tokyo.tkw.thinmp.epoxy.model;

import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyHolder;
import com.airbnb.epoxy.EpoxyModelClass;
import com.airbnb.epoxy.EpoxyModelWithHolder;

import tokyo.tkw.thinmp.R;

import static com.airbnb.epoxy.EpoxyAttribute.Option.DoNotHash;

@EpoxyModelClass(layout = R.layout.header_section_edit_shortcut)
public abstract class SectionHeaderShortcutEditModel extends EpoxyModelWithHolder<SectionHeaderShortcutEditModel.Holder> {
    @EpoxyAttribute
    String title;
    @EpoxyAttribute
    boolean visibility;
    @EpoxyAttribute(DoNotHash)
    CompoundButton.OnCheckedChangeListener changeListener;

    @Override
    public void bind(@NonNull Holder holder) {
        holder.checkBox.setText(title);
        holder.checkBox.setOnCheckedChangeListener(changeListener);
    }

    /**
     * bindではチェック状態が復元されなかったのでbuildViewで復元
     *
     * @param parent
     * @return
     */
    @Override
    protected View buildView(@NonNull ViewGroup parent) {
        View view = super.buildView(parent);
        ((CheckBox) view.findViewById(R.id.shortcutCheckBox)).setChecked(visibility);
        return view;
    }

    @Override
    public boolean shouldSaveViewState() {
        return true;
    }

    static class Holder extends EpoxyHolder {
        CheckBox checkBox;

        @Override
        protected void bindView(@NonNull View itemView) {
            checkBox = itemView.findViewById(R.id.shortcutCheckBox);
        }
    }
}
