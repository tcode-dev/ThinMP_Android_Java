package tokyo.tkw.thinmp.listener;

import android.view.View;

public class CancelClickListener extends BaseClickListener {
    @Override
    public void onClick(View view) {
        getActivity(view).finish();
    }
}
