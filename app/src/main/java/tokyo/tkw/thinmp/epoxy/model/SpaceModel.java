package tokyo.tkw.thinmp.epoxy.model;

import android.view.View;

import androidx.annotation.NonNull;

import com.airbnb.epoxy.EpoxyHolder;
import com.airbnb.epoxy.EpoxyModelClass;
import com.airbnb.epoxy.EpoxyModelWithHolder;

import tokyo.tkw.thinmp.R;

@EpoxyModelClass(layout = R.layout.space)
public abstract class SpaceModel extends EpoxyModelWithHolder<SpaceModel.Holder> {

    @Override
    public void bind(@NonNull Holder holder) {
    }

    static class Holder extends EpoxyHolder {

        @Override
        protected void bindView(@NonNull View itemView) {
        }
    }
}
