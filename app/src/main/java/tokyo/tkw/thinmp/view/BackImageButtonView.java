package tokyo.tkw.thinmp.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;

import androidx.appcompat.widget.AppCompatImageButton;

public class BackImageButtonView extends AppCompatImageButton {
    public BackImageButtonView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        this.setOnClickListener(v -> {
            Context context = getContext();

            if (context instanceof Activity) {
                ((Activity) context).finish();
            } else if (context instanceof ContextThemeWrapper) {
                // CollapsingToolbarLayoutの中だとContextThemeWrapperが返ってくる
                ((Activity) ((ContextThemeWrapper) context).getBaseContext()).finish();
            }
        });
    }
}
