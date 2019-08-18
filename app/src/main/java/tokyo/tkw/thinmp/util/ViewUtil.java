package tokyo.tkw.thinmp.util;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

public class ViewUtil {
    /**
     * spをpxに変換する
     *
     * @param context
     * @param size
     * @return float
     */
    public static float spToDimensionPx(Context context, int size) {
        return toDimensionUnit(context, size, TypedValue.COMPLEX_UNIT_SP);
    }

    /**
     * dpをpxに変換する
     *
     * @param context
     * @param size
     * @return float
     */
    public static float dpToDimensionPx(Context context, int size) {
        return toDimensionUnit(context, size, TypedValue.COMPLEX_UNIT_DIP);
    }

    /**
     * pxに変換する
     *
     * @param context
     * @param size
     * @param unit
     * @return float
     * @see android.widget.TextView#setTextSize
     */
    private static float toDimensionUnit(Context context, int size, int unit) {
        Resources r;
        if (context == null) {
            r = Resources.getSystem();
        } else {
            r = context.getResources();
        }

        return TypedValue.applyDimension(unit, (float) size, r.getDisplayMetrics());
    }
}
