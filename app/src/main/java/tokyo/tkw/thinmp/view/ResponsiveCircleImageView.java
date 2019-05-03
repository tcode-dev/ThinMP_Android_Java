package tokyo.tkw.thinmp.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.util.AttributeSet;
import android.view.ViewParent;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.util.ViewUtil;

public class ResponsiveCircleImageView extends CircleImageView {
    private static final float DEFAULT_COLLAPSE_SCALE = 0.8f;
    private static final int DEFAULT_OFFSET_X = 0;
    private static final int DEFAULT_OFFSET_Y = 0;
    private AppBarLayout.OnOffsetChangedListener mOnOffsetChangedListener;
    private float mCollapseScale;
    private float mOffsetX;
    private float mOffsetY;
    private float mScrollRate;

    public ResponsiveCircleImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.Responsive);
        mCollapseScale = 1 - typedArray.getFloat(R.styleable.Responsive_collapseScale,
                DEFAULT_COLLAPSE_SCALE);
        mOffsetX = ViewUtil.dpToDimensionPx(context,
                typedArray.getInt(R.styleable.Responsive_collapseOffsetX, DEFAULT_OFFSET_X));
        mOffsetY = ViewUtil.dpToDimensionPx(context,
                typedArray.getInt(R.styleable.Responsive_collapseOffsetY, DEFAULT_OFFSET_Y));
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        float offsetX = mScrollRate * mOffsetX;
        float offsetY = mScrollRate * mOffsetY;
        float scale = 1 - (mScrollRate * mCollapseScale);

        this.setScaleX(scale);
        this.setScaleY(scale);
        this.setTranslationX(offsetX);
        this.setTranslationY(offsetY);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        final ViewParent parent = getRootView().findViewById(R.id.app_bar);

        if (!(parent instanceof AppBarLayout)) {
            return;
        }

        if (mOnOffsetChangedListener == null) {
            mOnOffsetChangedListener = new OffsetUpdateListener();
        }

        ((AppBarLayout) parent).addOnOffsetChangedListener(mOnOffsetChangedListener);
    }

    @Override
    protected void onDetachedFromWindow() {
        final ViewParent parent = getRootView().findViewById(R.id.app_bar);

        if (mOnOffsetChangedListener != null && parent instanceof AppBarLayout) {
            ((AppBarLayout) parent).removeOnOffsetChangedListener(mOnOffsetChangedListener);
        }

        super.onDetachedFromWindow();
    }

    private class OffsetUpdateListener implements AppBarLayout.OnOffsetChangedListener {
        OffsetUpdateListener() {
        }

        @Override
        public void onOffsetChanged(AppBarLayout layout, int verticalOffset) {
            mScrollRate = (float) -verticalOffset / layout.getTotalScrollRange();

            invalidate();
        }
    }
}
