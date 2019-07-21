package tokyo.tkw.thinmp.epoxy.model;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyHolder;
import com.airbnb.epoxy.EpoxyModelWithHolder;

import tokyo.tkw.thinmp.R;

import static com.airbnb.epoxy.EpoxyAttribute.Option.DoNotHash;

public abstract class PageHeaderWithButtonModel extends EpoxyModelWithHolder<PageHeaderWithButtonModel.Holder> {
    @EpoxyAttribute
    String title;
    @EpoxyAttribute(DoNotHash)
    View.OnClickListener clickListener;

    @Override
    public void bind(@NonNull Holder holder) {
        holder.title.setText(title);
        holder.edit.setOnClickListener(clickListener);
    }

    static class Holder extends EpoxyHolder {
        TextView title;
        Button edit;

        @Override
        protected void bindView(@NonNull View itemView) {
            title = itemView.findViewById(R.id.title);
            edit = itemView.findViewById(R.id.edit);
        }
    }
}