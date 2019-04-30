package tokyo.tkw.thinmp.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import tokyo.tkw.thinmp.R;

public class ResponsiveTextView extends View {
    private final int STROKE_WIDTH = 1;
    private final float DEFAULT_COLLAPSE_SCALE = 0.5f;
    private final int DEFAULT_TEXT_SIZE = 50;
    private final int DEFAULT_OFFSET_X = 0;
    private AppBarLayout.OnOffsetChangedListener mOnOffsetChangedListener;
    private TextPaint mTextPaint;
    private String mText;
    private float mScale = 1;
    private float mOffsetX;
    private float mOffsetY;
    private int mTextSize;
    private String mTextColor;
    private float mCollapseScale;
    private float mScaleRate;
    private OffsetYCalc mOffsetYCalc;
    private float mActualScrollRange;
    private float mScrollRate;

    public ResponsiveTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ResponsiveTextView);
        mCollapseScale = typedArray.getFloat(R.styleable.ResponsiveTextView_collapseScale, DEFAULT_COLLAPSE_SCALE);
        mTextSize = typedArray.getInt(R.styleable.ResponsiveTextView_textSize, DEFAULT_TEXT_SIZE);
        mTextColor = typedArray.getString(R.styleable.ResponsiveTextView_textColor);
        mOffsetX = toDimensionTextSize(getContext(),
                typedArray.getInt(R.styleable.ResponsiveTextView_collapseOffsetX,
                        DEFAULT_OFFSET_X));
        mOffsetYCalc =
                offsetYCalcFactory(typedArray.getString(R.styleable.ResponsiveTextView_collapseVerticalAlign));
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        float offsetX = mScrollRate * mOffsetX;
        int canvasWidth = canvas.getWidth() - (int) offsetX;
        int textWidth = (int) Layout.getDesiredWidth(mText, mTextPaint);
        int currentWidth = (int) ((float) textWidth * mScale);
        final CharSequence text = (currentWidth <= canvasWidth) ? mText :
                TextUtils.ellipsize(mText, mTextPaint, canvasWidth * (1 + (1 - mScale)), TextUtils.TruncateAt.END);

        canvas.scale(mScale, mScale);
        canvas.drawText(text, 0, text.length(), offsetX, mOffsetYCalc.calc(), mTextPaint);
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

        ViewParent parentAppbar = getRootView().findViewById(R.id.app_bar);

        if (!(parentAppbar instanceof AppBarLayout)) {
            return;
        }

        ViewParent parentToolbar = getRootView().findViewById(R.id.toolbar);

        if (!(parentToolbar instanceof Toolbar)) {
            return;
        }

        mActualScrollRange =
                ((AppBarLayout) parentAppbar).getTotalScrollRange() - ((Toolbar) parentToolbar).getHeight();

        mScaleRate = (1 - mCollapseScale) / mActualScrollRange;

        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.SUBPIXEL_TEXT_FLAG);
        mTextPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mTextPaint.setStrokeWidth(STROKE_WIDTH);
        mTextPaint.setTextSize(toDimensionTextSize(getContext(), mTextSize));
        mTextPaint.setColor(Color.parseColor(mTextColor));

        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        mOffsetY = - fontMetrics.ascent;

        ViewGroup.LayoutParams params = getLayoutParams();
        params.height = (int) (- fontMetrics.top + fontMetrics.bottom);
        setLayoutParams(params);
    }

    public void setText(String text) {
        mText = text;
    }

    /**
     * TextPaint#setTextSizeに単位を指定できないので端末に適したサイズに変換する
     * @see android.widget.TextView#setTextSize
     * @param context
     * @param size
     * @return float
     */
    private float toDimensionTextSize(Context context, int size) {
        Resources r;
        if (context == null) {
            r = Resources.getSystem();
        } else {
            r = context.getResources();
        }
        return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, (float) size, r.getDisplayMetrics());
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
            mScrollRate = (float) -verticalOffset / mActualScrollRange;

            invalidate();
        }
    }
}
