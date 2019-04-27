package tokyo.tkw.thinmp.view;

import android.content.Context;
import android.graphics.Outline;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewOutlineProvider;

public class CircleImageView extends android.support.v7.widget.AppCompatImageView {
    public CircleImageView(Context context) {
        super(context);

        initSet();
    }

    public CircleImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        initSet();
    }

    public CircleImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initSet();
    }

    private void initSet() {
        this.setOutlineProvider(new CircleOutlineProvider());
        this.setClipToOutline(true);
    }

    public static class CircleOutlineProvider extends ViewOutlineProvider {

        @Override
        public void getOutline(View view, Outline outline) {
            outline.setOval(0, 0, view.getWidth(), view.getHeight());
        }
    }
}
