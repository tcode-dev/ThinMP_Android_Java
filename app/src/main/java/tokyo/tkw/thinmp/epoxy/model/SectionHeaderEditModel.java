package tokyo.tkw.thinmp.epoxy.model;

import android.view.View;
import android.widget.CheckBox;

import androidx.annotation.NonNull;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyHolder;
import com.airbnb.epoxy.EpoxyModelClass;
import com.airbnb.epoxy.EpoxyModelWithHolder;

import tokyo.tkw.thinmp.R;

@EpoxyModelClass(layout = R.layout.header_section_edit)
public abstract class SectionHeaderEditModel extends EpoxyModelWithHolder<SectionHeaderEditModel.Holder> {
    @EpoxyAttribute
    String title;

    @Override
    public void bind(@NonNull Holder holder) {
        holder.title.setText(title);
    }

    static class Holder extends EpoxyHolder {
        CheckBox title;

        @Override
        protected void bindView(@NonNull View itemView) {
            title = itemView.findViewById(R.id.title);
        }
    }
}
