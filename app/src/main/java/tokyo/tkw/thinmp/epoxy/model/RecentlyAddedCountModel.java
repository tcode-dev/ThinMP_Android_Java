package tokyo.tkw.thinmp.epoxy.model;

import android.view.View;
import android.view.ViewGroup;
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
        holder.spinner.setOnItemSelectedListener(spinnerListener);
    }

    /**
     * bindでは状態が復元されなかったのでbuildViewで復元
     *
     * @param parent
     * @return
     */
    @Override
    protected View buildView(@NonNull ViewGroup parent) {
        View view = super.buildView(parent);
        ((Spinner) view.findViewById(R.id.recently_added_count)).setSelection(position);
        return view;
    }


    @Override
    public boolean shouldSaveViewState() {
        return true;
    }

    static class Holder extends EpoxyHolder {
        Spinner spinner;

        @Override
        protected void bindView(@NonNull View itemView) {
            spinner = itemView.findViewById(R.id.recently_added_count);
        }
    }
}
