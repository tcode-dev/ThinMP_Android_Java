package tokyo.tkw.thinmp.listener;

import android.app.Activity;
import android.content.Context;
import android.view.ContextThemeWrapper;
import android.view.View;

public class CancelClickListener implements View.OnClickListener {
    @Override
    public void onClick(View view) {
        Context context = view.getContext();

        if (context instanceof Activity) {
            ((Activity) context).finish();
        } else if (context instanceof ContextThemeWrapper) {
            // CollapsingToolbarLayoutの中だとContextThemeWrapperが返ってくる
            ((Activity) ((ContextThemeWrapper) context).getBaseContext()).finish();
        }
    }
}
