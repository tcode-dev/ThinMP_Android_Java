package tokyo.tkw.thinmp.view;

import android.annotation.SuppressLint;
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
    private AppBarLayout.OnOffsetChangedListener offsetChangedListener;
    private float collapseScale;
    private float offsetX;
    private float offsetY;
    private boolean isTextAlignCenter;
    private TextPaint textPaint;
    private String text;
    private float ascent;
    private float scrollRate;

    public ResponsiveTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        @SuppressLint("CustomViewStyleable") TypedArray responsiveTypedArray = context.obtainStyledAttributes(
                attrs,
                R.styleable.Responsive
        );

        collapseScale = 1 - responsiveTypedArray.getFloat(R.styleable.Responsive_collapseScale, DEFAULT_COLLAPSE_SCALE);

        offsetX = ViewUtil.dpToDimensionPx(
                context,
                responsiveTypedArray.getInt(R.styleable.Responsive_collapseOffsetX, DEFAULT_OFFSET_X)
        );

        offsetY = ViewUtil.dpToDimensionPx(
                context,
                responsiveTypedArray.getInt(R.styleable.Responsive_collapseOffsetY, DEFAULT_OFFSET_Y)
        );

        responsiveTypedArray.recycle();

        TypedArray responsiveTextViewTypedArray = context.obtainStyledAttributes(attrs, R.styleable.ResponsiveTextView);

        int textSize = responsiveTextViewTypedArray.getInt(R.styleable.ResponsiveTextView_textSize, DEFAULT_TEXT_SIZE);

        String textColor = responsiveTextViewTypedArray.getString(R.styleable.ResponsiveTextView_textColor);

        isTextAlignCenter = responsiveTextViewTypedArray.getBoolean(
                R.styleable.ResponsiveTextView_textAlignCenter,
                DEFAULT_IS_TEXT_ALIGN_CENTER
        );

        responsiveTextViewTypedArray.recycle();

        textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.SUBPIXEL_TEXT_FLAG);
        textPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        textPaint.setStrokeWidth(STROKE_WIDTH);
        textPaint.setTextSize(ViewUtil.spToDimensionPx(context, textSize));
        textPaint.setColor(Color.parseColor(textColor));

        ascent = -textPaint.getFontMetrics().ascent;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        float scale = 1 - (scrollRate * collapseScale);
        int canvasWidth = getWidth();
        int textWidth = (int) Layout.getDesiredWidth(text, textPaint);
        int currentWidth = (int) ((float) textWidth * scale);
        final CharSequence text = (currentWidth <= canvasWidth) ? this.text :
                TextUtils.ellipsize(
                        this.text, textPaint,
                        canvasWidth * (1 + (1 - scale)),
                        TextUtils.TruncateAt.END
                );
        float offsetX = (isTextAlignCenter && (currentWidth < canvasWidth)) ?
                scrollRate * this.offsetX + (((float) (canvasWidth - currentWidth)) / 2) :
                scrollRate * this.offsetX;
        float offsetY = scrollRate * this.offsetY;

        canvas.scale(scale, scale);
        canvas.drawText(text, 0, text.length(), 0, ascent, textPaint);
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

        if (offsetChangedListener == null) {
            offsetChangedListener = new OffsetUpdateListener();
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

    @Override
    public void setLayoutParams(ViewGroup.LayoutParams params) {
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        params.height = (int) (-fontMetrics.top + fontMetrics.bottom);

        super.setLayoutParams(params);
    }

    public void setText(String text) {
        this.text = text;
    }

    private class OffsetUpdateListener implements AppBarLayout.OnOffsetChangedListener {
        OffsetUpdateListener() {
        }

        @Override
        public void onOffsetChanged(AppBarLayout layout, int verticalOffset) {
            scrollRate = (float) -verticalOffset / layout.getTotalScrollRange();
            invalidate();
        }
    }
}
