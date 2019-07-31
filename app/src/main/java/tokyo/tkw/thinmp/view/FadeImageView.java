package tokyo.tkw.thinmp.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.ViewParent;

import androidx.annotation.Nullable;

import com.google.android.material.appbar.AppBarLayout;

import tokyo.tkw.thinmp.R;

/**
 * CollapsingToolbarLayoutの状態(スクロール位置)によって画像をぼかし具合を変える
 */
public class FadeImageView extends BlurImageView {
    private static final float DEFAULT_START = 0;
    private static final float DEFAULT_END = 1;
    private static final float DEFAULT_MIN = 0;
    private static final float DEFAULT_MAX = 1;

    private AppBarLayout.OnOffsetChangedListener onOffsetChangedListener;
    private float start;
    private float end;
    private float scrollDiff;
    private float min;
    private float max;
    private float alphaDiff;

    public FadeImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        @SuppressLint({"Recycle", "CustomViewStyleable"}) TypedArray scrollTypedArray =
                context.obtainStyledAttributes(attrs, R.styleable.Scroll);
        start = scrollTypedArray.getFloat(R.styleable.Scroll_scrollStart, DEFAULT_START);
        end = scrollTypedArray.getFloat(R.styleable.Scroll_scrollEnd, DEFAULT_END);

        if (start > end) {
            throw new IllegalArgumentException("start < end になるように設定してください");
        }

        @SuppressLint({"Recycle", "CustomViewStyleable"}) TypedArray alphaTypedArray =
                context.obtainStyledAttributes(attrs, R.styleable.Alpha);
        min = alphaTypedArray.getFloat(R.styleable.Alpha_alphaMin, DEFAULT_MIN);
        max = alphaTypedArray.getFloat(R.styleable.Alpha_alphaMax, DEFAULT_MAX);

        if (min > max) {
            throw new IllegalArgumentException("min < max になるように設定してください");
        }

        scrollDiff = end - start;
        alphaDiff = max - min;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        final ViewParent parent = getRootView().findViewById(R.id.app_bar);

        if (!(parent instanceof AppBarLayout)) {
            return;
        }

        if (onOffsetChangedListener == null) {
            onOffsetChangedListener = new FadeImageView.FadeImageOffsetUpdateListener();
        }

        ((AppBarLayout) parent).addOnOffsetChangedListener(onOffsetChangedListener);
    }

    @Override
    protected void onDetachedFromWindow() {
        final ViewParent parent = getRootView().findViewById(R.id.app_bar);

        if (onOffsetChangedListener != null && parent instanceof AppBarLayout) {
            ((AppBarLayout) parent).removeOnOffsetChangedListener(onOffsetChangedListener);
        }

        super.onDetachedFromWindow();
    }

    private class FadeImageOffsetUpdateListener implements AppBarLayout.OnOffsetChangedListener {
        FadeImageOffsetUpdateListener() {
        }

        @Override
        public void onOffsetChanged(AppBarLayout layout, int verticalOffset) {
            float scrollRate = (float) -verticalOffset / layout.getTotalScrollRange();

            if (scrollRate < start) {
                setAlpha(max);
            } else if (start <= scrollRate && scrollRate <= end) {
                float alpha = (alphaDiff * (1 - ((scrollRate - start) / scrollDiff))) + min;
                setAlpha(alpha);
            } else {
                setAlpha(min);
            }
        }
    }
}
