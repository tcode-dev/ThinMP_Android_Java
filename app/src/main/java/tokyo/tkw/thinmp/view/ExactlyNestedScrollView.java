package tokyo.tkw.thinmp.view;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.View;

/**
 * NestedScrollViewにRecyclerViewを配置すると要素が全て展開されるので必要な個数のみ（displayの高さ分っぽい）展開する
 * MeasureSpec.EXACTLY : sizeで指定した値に幅（高さ）を設定すること。子Viewもこの範囲に収まるようにサイズを決める。
 * MeasureSpec.UNSPECIFIED : 特に指定なし。自由で構いません
 * MeasureSpec.AT_MOST : sizeで指定した値以下にする。子Viewもそれ前提でサイズを決める
 */
public class ExactlyNestedScrollView extends NestedScrollView {
    public ExactlyNestedScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void measureChildWithMargins(View child, int parentWidthMeasureSpec, int widthUsed,
                                           int parentHeightMeasureSpec, int heightUsed) {
        final MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

        final int childWidthMeasureSpec = getChildMeasureSpec(parentWidthMeasureSpec,
                getPaddingLeft() + getPaddingRight() + lp.leftMargin + lp.rightMargin
                        + widthUsed, lp.width);
        final int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(
                lp.topMargin + lp.bottomMargin, MeasureSpec.EXACTLY);

        child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
    }
}
