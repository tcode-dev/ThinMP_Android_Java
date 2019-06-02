package tokyo.tkw.thinmp.view;

import android.content.Context;
import android.graphics.Outline;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewOutlineProvider;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

public class CircleImageView extends AppCompatImageView {
    public CircleImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

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
