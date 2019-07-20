package tokyo.tkw.thinmp.epoxy.model;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyHolder;
import com.airbnb.epoxy.EpoxyModelClass;
import com.airbnb.epoxy.EpoxyModelWithHolder;

import tokyo.tkw.thinmp.R;

import static com.airbnb.epoxy.EpoxyAttribute.Option.DoNotHash;

@EpoxyModelClass(layout = R.layout.list_item_linear_main_menu)
public abstract class MainMenuModel extends EpoxyModelWithHolder<MainMenuModel.Holder> {
    @EpoxyAttribute
    String primaryText;
    @EpoxyAttribute(DoNotHash)
    View.OnClickListener clickListener;

    @Override
    public void bind(@NonNull MainMenuModel.Holder holder) {
        holder.primaryText.setText(primaryText);
        holder.parent.setOnClickListener(clickListener);
    }

    static class Holder extends EpoxyHolder {
        View parent;
        TextView primaryText;

        @Override
        protected void bindView(@NonNull View itemView) {
            parent = itemView;
            primaryText = itemView.findViewById(R.id.primaryText);
        }
    }
}
