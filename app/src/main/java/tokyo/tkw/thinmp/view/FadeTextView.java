package tokyo.tkw.thinmp.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.AppBarLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewParent;

import tokyo.tkw.thinmp.R;

public class FadeTextView extends android.support.v7.widget.AppCompatTextView {
    private static final float DEFAULT_START = 0;
    private static final boolean DEFAULT_FADE_IN = true;
    private AppBarLayout.OnOffsetChangedListener mOnOffsetChangedListener;
    private float mStart;
    private float mEnd;
    private float mDiff;
    private boolean mIsFadeIn;

    public FadeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        intSet(context, attrs);
    }

    public FadeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        intSet(context, attrs);
    }

    private void intSet(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.Fade);
        mStart = typedArray.getFloat(R.styleable.Fade_fadeStart, DEFAULT_START);
        mEnd = typedArray.getFloat(R.styleable.Fade_fadeEnd, DEFAULT_START);

        if (mStart > mEnd) {
            throw new IllegalArgumentException();
        }

        mIsFadeIn = typedArray.getBoolean(R.styleable.Fade_fadeIn, DEFAULT_FADE_IN);
        mDiff = mEnd - mStart;
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

        if (mOnOffsetChangedListener == null) {
            mOnOffsetChangedListener = fadeFactory();
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

    private AppBarLayout.OnOffsetChangedListener fadeFactory() {
        return mIsFadeIn ? new FadeInOffsetUpdateListener() : new FadeOutOffsetUpdateListener();
    }

    private class FadeInOffsetUpdateListener implements AppBarLayout.OnOffsetChangedListener {
        FadeInOffsetUpdateListener() {
        }

        @Override
        public void onOffsetChanged(AppBarLayout layout, int verticalOffset) {
            float scrollRate = (float) -verticalOffset / layout.getTotalScrollRange();

            if (scrollRate < mStart) {
                fade(0.f);
            } else if (mStart <= scrollRate && scrollRate <= mEnd ) {
                float alpha = ((scrollRate - mStart) / mDiff);

                fade(alpha);
            } else {
                fade(1.f);
            }
        }
    }

    private class FadeOutOffsetUpdateListener implements AppBarLayout.OnOffsetChangedListener {
        FadeOutOffsetUpdateListener() {
        }

        @Override
        public void onOffsetChanged(AppBarLayout layout, int verticalOffset) {
            float scrollRate = (float) -verticalOffset / layout.getTotalScrollRange();

            if (scrollRate < mStart) {
                fade(1.f);
            } else if (mStart <= scrollRate && scrollRate <= mEnd ) {
                float alpha = 1 - ((scrollRate - mStart) / mDiff);

                fade(alpha);
            } else {
                fade(0.f);
            }
        }
    }
}
