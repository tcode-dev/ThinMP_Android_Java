package tokyo.tkw.thinmp.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageButton;

import tokyo.tkw.thinmp.R;

public class ColorChangeableImageButtonView extends AppCompatImageButton {
    public ColorChangeableImageButtonView(Context context, AttributeSet attrs) {
        super(context, attrs);

        @SuppressLint({"Recycle", "CustomViewStyleable"}) TypedArray typedArray =
                context.obtainStyledAttributes(attrs, R.styleable.Icon);
        String value = typedArray.getString(R.styleable.Icon_iconColor);

        if (value != null) {
            setColorFilter(Color.parseColor(value), PorterDuff.Mode.SRC_IN);
        } else {
            int color = getContext().getColor(R.color.colorIconMain);
            setColorFilter(color, PorterDuff.Mode.SRC_IN);
        }
    }
}
