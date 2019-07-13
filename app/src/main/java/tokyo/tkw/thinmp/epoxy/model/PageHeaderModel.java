package tokyo.tkw.thinmp.epoxy.model;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyHolder;
import com.airbnb.epoxy.EpoxyModelClass;
import com.airbnb.epoxy.EpoxyModelWithHolder;

import tokyo.tkw.thinmp.R;

@EpoxyModelClass(layout = R.layout.page_header)
public abstract class PageHeaderModel extends EpoxyModelWithHolder<PageHeaderModel.Holder> {
    @EpoxyAttribute
    String title;

    @Override
    public void bind(@NonNull Holder holder) {
        holder.title.setText(title);
    }

    static class Holder extends EpoxyHolder {
        TextView title;

        @Override
        protected void bindView(@NonNull View itemView) {
            title = itemView.findViewById(R.id.title);
        }
    }
}
