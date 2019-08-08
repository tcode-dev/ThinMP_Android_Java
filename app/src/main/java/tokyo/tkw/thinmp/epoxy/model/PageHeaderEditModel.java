package tokyo.tkw.thinmp.epoxy.model;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyHolder;
import com.airbnb.epoxy.EpoxyModelClass;
import com.airbnb.epoxy.EpoxyModelWithHolder;

import tokyo.tkw.thinmp.R;

import static com.airbnb.epoxy.EpoxyAttribute.Option.DoNotHash;

@EpoxyModelClass(layout = R.layout.header_page_edit)
public abstract class PageHeaderEditModel extends EpoxyModelWithHolder<PageHeaderEditModel.Holder> {
    @EpoxyAttribute
    String title;
    @EpoxyAttribute(DoNotHash)
    View.OnClickListener applyClickListener;
    @EpoxyAttribute(DoNotHash)
    View.OnClickListener cancelClickListener;

    @Override
    public void bind(@NonNull Holder holder) {
        holder.title.setText(title);
        holder.apply.setOnClickListener(applyClickListener);
        holder.cancel.setOnClickListener(cancelClickListener);
    }

    static class Holder extends EpoxyHolder {
        TextView title;
        Button apply;
        Button cancel;

        @Override
        protected void bindView(@NonNull View itemView) {
            title = itemView.findViewById(R.id.title);
            apply = itemView.findViewById(R.id.apply);
            cancel = itemView.findViewById(R.id.cancel);
        }
    }
}
