package tokyo.tkw.thinmp.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.renderscript.RenderScript;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.plugin.RSBlurProcessor;

/**
 * 画像をぼかす
 */
public class BlurImageView extends AppCompatImageView {
    private static final float RADIUS = 20f;
    private static final int REPEAT = 3;

    private float radius;
    private int repeat;

    public BlurImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        @SuppressLint("CustomViewStyleable") TypedArray typedArray = context.obtainStyledAttributes(
                attrs,
                R.styleable.Blur
        );

        radius = typedArray.getFloat(R.styleable.Blur_blurRadius, RADIUS);
        repeat = typedArray.getInt(R.styleable.Blur_blurRepeat, REPEAT);

        typedArray.recycle();
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        if (bm == null) {
            return;
        }

        RenderScript rs = RenderScript.create(getContext());
        RSBlurProcessor rsBlurProcessor = new RSBlurProcessor(rs);
        Bitmap blurBitMap = rsBlurProcessor.blur(bm.copy(Bitmap.Config.ARGB_8888, true), radius, repeat);

        super.setImageBitmap(blurBitMap);
    }
}
