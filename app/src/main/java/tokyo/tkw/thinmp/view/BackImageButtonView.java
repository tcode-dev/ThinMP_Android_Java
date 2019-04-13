package tokyo.tkw.thinmp.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

public class BackImageButtonView extends android.support.v7.widget.AppCompatImageButton {
    public BackImageButtonView(Context context) {
        super(context);
    }

    public BackImageButtonView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BackImageButtonView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        this.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                ((Activity) getContext()).finish();
            }
        });
    }
}