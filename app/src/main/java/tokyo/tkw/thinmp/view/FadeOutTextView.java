package tokyo.tkw.thinmp.view;

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
    private AppBarLayout.OnOffsetChangedListener mOnOffsetChangedListener;
    private float mStart;
    private float mEnd;
    private float mDiff;

    /**
     * XMLからViewをinflateした際のコンストラクタ
     *
     * @param context
     * @param attrs
     */
    public FadeOutTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        initSet(context, attrs);
    }

    private void initSet(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.Fade);
        mStart = typedArray.getFloat(R.styleable.Fade_fadeStart, DEFAULT_START);
        mEnd = typedArray.getFloat(R.styleable.Fade_fadeEnd, DEFAULT_END);

        if (mStart > mEnd) {
            throw new IllegalArgumentException("start < end になるように設定してください");
        }

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
            mOnOffsetChangedListener = new FadeOutOffsetUpdateListener();
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

    private class FadeOutOffsetUpdateListener implements AppBarLayout.OnOffsetChangedListener {
        FadeOutOffsetUpdateListener() {
        }

        @Override
        public void onOffsetChanged(AppBarLayout layout, int verticalOffset) {
            float scrollRate = (float) -verticalOffset / layout.getTotalScrollRange();

            if (scrollRate < mStart) {
                fade(1.f);
            } else if (mStart <= scrollRate && scrollRate <= mEnd) {
                float alpha = 1 - ((scrollRate - mStart) / mDiff);

                fade(alpha);
            } else {
                fade(0.f);
            }
        }
    }
}
