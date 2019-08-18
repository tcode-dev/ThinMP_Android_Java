package tokyo.tkw.thinmp.epoxy.model;

import android.view.View;
import android.widget.Spinner;

import androidx.annotation.NonNull;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyHolder;
import com.airbnb.epoxy.EpoxyModelClass;
import com.airbnb.epoxy.EpoxyModelWithHolder;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.listener.RecentlyAddedCountSelectedListener;

@EpoxyModelClass(layout = R.layout.list_item_linear_recently_added_count)
public abstract class RecentlyAddedCountModel extends EpoxyModelWithHolder<RecentlyAddedCountModel.Holder> {
    @EpoxyAttribute
    int position;
    @EpoxyAttribute
    RecentlyAddedCountSelectedListener spinnerListener;

    @Override
    public void bind(@NonNull RecentlyAddedCountModel.Holder holder) {
        holder.spinner.setSelection(position);
        holder.spinner.setOnItemSelectedListener(spinnerListener);
    }

    static class Holder extends EpoxyHolder {
        Spinner spinner;

        @Override
        protected void bindView(@NonNull View itemView) {
            spinner = itemView.findViewById(R.id.recently_added_count);
        }
    }
}
