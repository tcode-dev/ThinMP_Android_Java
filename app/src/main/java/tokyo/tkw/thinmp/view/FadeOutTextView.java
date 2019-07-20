package tokyo.tkw.thinmp.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.ViewParent;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.google.android.material.appbar.AppBarLayout;

import tokyo.tkw.thinmp.R;

public class FadeOutTextView extends AppCompatTextView {
    private static final float DEFAULT_START = 0;
    private static final float DEFAULT_END = 1;
    private AppBarLayout.OnOffsetChangedListener offsetChangedListener;
    private float start;
    private float end;
    private float diff;

    public FadeOutTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        initSet(context, attrs);
    }

    private void initSet(Context context, AttributeSet attrs) {
        @SuppressLint({"Recycle", "CustomViewStyleable"}) TypedArray typedArray =
                context.obtainStyledAttributes(attrs, R.styleable.Fade);
        start = typedArray.getFloat(R.styleable.Fade_fadeStart, DEFAULT_START);
        end = typedArray.getFloat(R.styleable.Fade_fadeEnd, DEFAULT_END);

        if (start > end) {
            throw new IllegalArgumentException("start < end になるように設定してください");
        }

        diff = end - start;
    }

    private void fade(float alpha) {
        this.setAlpha(alpha);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        final ViewParent parent = getRootView().findViewById(R.id.app_bar);

        if (!(parent instanceof AppBarLayout)) {
            return;
        }

        if (offsetChangedListener == null) {
            offsetChangedListener = new FadeOutOffsetUpdateListener();
        }

        ((AppBarLayout) parent).addOnOffsetChangedListener(offsetChangedListener);
    }

    @Override
    protected void onDetachedFromWindow() {
        final ViewParent parent = getRootView().findViewById(R.id.app_bar);

        if (offsetChangedListener != null && parent instanceof AppBarLayout) {
            ((AppBarLayout) parent).removeOnOffsetChangedListener(offsetChangedListener);
        }

        super.onDetachedFromWindow();
    }

    private class FadeOutOffsetUpdateListener implements AppBarLayout.OnOffsetChangedListener {
        FadeOutOffsetUpdateListener() {
        }

        @Override
        public void onOffsetChanged(AppBarLayout layout, int verticalOffset) {
            float scrollRate = (float) -verticalOffset / layout.getTotalScrollRange();

            if (scrollRate < start) {
                fade(1.f);
            } else if (start <= scrollRate && scrollRate <= end) {
                float alpha = 1 - ((scrollRate - start) / diff);

                fade(alpha);
            } else {
                fade(0.f);
            }
        }
    }
}
