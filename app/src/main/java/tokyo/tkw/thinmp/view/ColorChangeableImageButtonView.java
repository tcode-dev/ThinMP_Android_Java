package tokyo.tkw.thinmp.view;

import android.content.Context;
import android.graphics.PorterDuff;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageButton;

import tokyo.tkw.thinmp.R;

public class ColorChangeableImageButtonView extends AppCompatImageButton {
    public ColorChangeableImageButtonView(Context context, AttributeSet attrs) {
        super(context, attrs);

        int color = getContext().getColor(R.color.colorAccent);
        setColorFilter(color, PorterDuff.Mode.SRC_IN);
    }
}
