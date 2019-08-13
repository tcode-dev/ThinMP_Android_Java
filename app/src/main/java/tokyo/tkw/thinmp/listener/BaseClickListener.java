package tokyo.tkw.thinmp.listener;

import android.app.Activity;
import android.content.Context;
import android.view.ContextThemeWrapper;
import android.view.View;

abstract class BaseClickListener implements View.OnClickListener {
    Activity getActivity(View view) {
        return getActivity(view.getContext());
    }

    Activity getActivity(Context context) {
        if (context instanceof Activity) {
            return (Activity) context;
        } else if (context instanceof ContextThemeWrapper) {
            // CollapsingToolbarLayoutの中だとContextThemeWrapperが返ってくる
            return (Activity) ((ContextThemeWrapper) context).getBaseContext();
        } else {
            throw new ClassCastException();
        }
    }
}
