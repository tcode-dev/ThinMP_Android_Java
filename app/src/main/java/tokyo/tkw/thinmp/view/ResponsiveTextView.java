package tokyo.tkw.thinmp.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.Layout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.annotation.Nullable;

import com.google.android.material.appbar.AppBarLayout;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.util.ViewUtil;

public class ResponsiveTextView extends View {
    private static final int STROKE_WIDTH = 1;
    private static final float DEFAULT_COLLAPSE_SCALE = 1f;
    private static final int DEFAULT_TEXT_SIZE = 24;
    private static final int DEFAULT_OFFSET_X = 0;
    private static final int DEFAULT_OFFSET_Y = 0;
    private static final boolean DEFAULT_IS_TEXT_ALIGN_CENTER = false;
    private AppBarLayout.OnOffsetChangedListener mOnOffsetChangedListener;
    private float mCollapseScale;
    private float mOffsetX;
    private float mOffsetY;
    private boolean mIsTextAlignCenter;
    private TextPaint mTextPaint;
    private String mText;
    private float mAscent;
    private float mScrollRate;

    public ResponsiveTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray responsiveTypedArray = context.obtainStyledAttributes(attrs,
                R.styleable.Responsive);

        mCollapseScale = 1 - responsiveTypedArray.getFloat(R.styleable.Responsive_collapseScale,
                DEFAULT_COLLAPSE_SCALE);

        mOffsetX = ViewUtil.dpToDimensionPx(context,
                responsiveTypedArray.getInt(R.styleable.Responsive_collapseOffsetX,
                        DEFAULT_OFFSET_X));

        mOffsetY = ViewUtil.dpToDimensionPx(context,
                responsiveTypedArray.getInt(R.styleable.Responsive_collapseOffsetY,
                        DEFAULT_OFFSET_Y));

        responsiveTypedArray.recycle();

        TypedArray responsiveTextViewTypedArray = context.obtainStyledAttributes(attrs,
                R.styleable.ResponsiveTextView);

        int textSize = responsiveTextViewTypedArray.getInt(R.styleable.ResponsiveTextView_textSize,
                DEFAULT_TEXT_SIZE);

        String textColor =
                responsiveTextViewTypedArray.getString(R.styleable.ResponsiveTextView_textColor);

        mIsTextAlignCenter =
                responsiveTextViewTypedArray.getBoolean(R.styleable.ResponsiveTextView_textAlignCenter, DEFAULT_IS_TEXT_ALIGN_CENTER);

        responsiveTextViewTypedArray.recycle();

        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.SUBPIXEL_TEXT_FLAG);
        mTextPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mTextPaint.setStrokeWidth(STROKE_WIDTH);
        mTextPaint.setTextSize(ViewUtil.spToDimensionPx(context, textSize));
        mTextPaint.setColor(Color.parseColor(textColor));

        mAscent = -mTextPaint.getFontMetrics().ascent;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        float scale = 1 - (mScrollRate * mCollapseScale);
        int canvasWidth = getWidth();
        int textWidth = (int) Layout.getDesiredWidth(mText, mTextPaint);
        int currentWidth = (int) ((float) textWidth * scale);
        final CharSequence text = (currentWidth <= canvasWidth) ? mText :
                TextUtils.ellipsize(mText, mTextPaint, canvasWidth * (1 + (1 - scale)),
                        TextUtils.TruncateAt.END);
        float offsetX = (mIsTextAlignCenter && (currentWidth < canvasWidth)) ?
                mScrollRate * mOffsetX + ((float) (canvasWidth - currentWidth) / 2) :
                mScrollRate * mOffsetX;
        float offsetY = mScrollRate * mOffsetY;

        canvas.scale(scale, scale);
        canvas.drawText(text, 0, text.length(), 0, mAscent, mTextPaint);
        setTranslationX(offsetX);
        setTranslationY(offsetY);
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
    public void setLayoutParams(ViewGroup.LayoutParams params) {
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        params.height = (int) (-fontMetrics.top + fontMetrics.bottom);

        super.setLayoutParams(params);
    }

    public void setText(String text) {
        mText = text;
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
