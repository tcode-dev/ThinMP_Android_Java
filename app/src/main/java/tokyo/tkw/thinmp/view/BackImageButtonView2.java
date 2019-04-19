package tokyo.tkw.thinmp.view;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewParent;

import tokyo.tkw.thinmp.R;

/**
 * 戻るボタン
 * CollapsingToolbarLayoutで折りたたみ後に表示する
 */
public class BackImageButtonView2 extends BackImageButtonView {
    private AppBarLayout.OnOffsetChangedListener mOnOffsetChangedListener;
    private int mActualScrollRange;

    public BackImageButtonView2(Context context) {
        super(context);
    }

    public BackImageButtonView2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BackImageButtonView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        final ViewParent parent = getRootView().findViewById(R.id.app_bar);

        if (!(parent instanceof AppBarLayout)) {
            return;
        }

        if (mOnOffsetChangedListener == null) {
            mOnOffsetChangedListener = new BackImageButtonView2.OffsetUpdateListener();
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
    }

    private void updateVisibility(int visibility) {
        this.setVisibility(visibility);
    }

    private class OffsetUpdateListener implements AppBarLayout.OnOffsetChangedListener {
        OffsetUpdateListener() {
        }

        @Override
        public void onOffsetChanged(AppBarLayout layout, int verticalOffset) {
            if (-verticalOffset == mActualScrollRange) {
                updateVisibility(View.VISIBLE);
            } else {
                updateVisibility(View.INVISIBLE);
            }
        }
    }
}
