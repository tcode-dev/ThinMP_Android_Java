package tokyo.tkw.thinmp.listener;

import android.app.Activity;
import android.view.View;

public class CancelClickListener implements View.OnClickListener {
    @Override
    public void onClick(View view) {
        ((Activity) view.getContext()).finish();
    }
}
