package tokyo.tkw.thinmp.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import tokyo.tkw.thinmp.R;

public class ResponsiveTextView extends View {
    private final int STROKE_WIDTH = 1;
    private final float DEFAULT_SCALE_RATE = 0.5f;
    private final int DEFAULT_TEXT_SIZE = 50;
    private AppBarLayout.OnOffsetChangedListener mOnOffsetChangedListener;
    private TextPaint mTextPaint;
    private String mText;
    private float mScale = 1;
    private float mOffsetY;
    private int mTextSize;
    private String mTextColor;
    private float mMinScale;
    private float mScaleRate;
    private OffsetYCalc mOffsetYCalc;

    public ResponsiveTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ResponsiveTextView);
        mMinScale = typedArray.getFloat(R.styleable.ResponsiveTextView_minScale, DEFAULT_SCALE_RATE);
        mTextSize = typedArray.getInt(R.styleable.ResponsiveTextView_textSize, DEFAULT_TEXT_SIZE);
        mTextColor = typedArray.getString(R.styleable.ResponsiveTextView_textColor);
        String position = typedArray.getString(R.styleable.ResponsiveTextView_offsetY);
        mOffsetYCalc = offsetYCalcFactory(position);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        canvas.scale(mScale, mScale);
        canvas.drawText(mText, 0, mText.length(), 0, mOffsetYCalc.calc(), mTextPaint);
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

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        final ViewParent parent = getRootView().findViewById(R.id.app_bar);

        if (!(parent instanceof AppBarLayout)) {
            return;
        }

        mScaleRate = (1 - mMinScale) / (float) ((AppBarLayout) parent).getTotalScrollRange();

        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.SUBPIXEL_TEXT_FLAG);
        mTextPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mTextPaint.setStrokeWidth(STROKE_WIDTH);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(Color.parseColor(mTextColor));

        mOffsetY = -mTextPaint.ascent();
        int height = (int) (-mTextPaint.ascent() + mTextPaint.descent());
        ViewGroup.LayoutParams params = getLayoutParams();
        params.height = (int) ((float) height * mScale);
        setLayoutParams(params);
    }


    public void setText(String text) {
        mText = text;
    }

    private OffsetYCalc offsetYCalcFactory(String position) {
        switch (position) {
            case "top":
                return new OffsetYTopCalc();
            case "middle":
                return new OffsetYMiddleCalc();
            case "bottom":
                return new OffsetYBottomCalc();
            default:
                return new OffsetYMiddleCalc();
        }
    }

    private interface OffsetYCalc {
        float calc();
    }

    private class OffsetYTopCalc implements OffsetYCalc {
        @Override
        public float calc() {
            return mOffsetY;
        }
    }

    private class OffsetYMiddleCalc implements OffsetYCalc {
        @Override
        public float calc() {
            return mOffsetY + (mOffsetY * (1 - mScale));
        }
    }

    private class OffsetYBottomCalc implements OffsetYCalc {
        @Override
        public float calc() {
            return mOffsetY + (mOffsetY * (1 - mScale) * 2);
        }
    }

    private class OffsetUpdateListener implements AppBarLayout.OnOffsetChangedListener {
        OffsetUpdateListener() {
        }

        @Override
        public void onOffsetChanged(AppBarLayout layout, int verticalOffset) {
            mScale = 1 + (((float) verticalOffset) * mScaleRate);

            invalidate();
        }
    }
}
