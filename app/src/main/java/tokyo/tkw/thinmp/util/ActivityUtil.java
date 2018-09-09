package tokyo.tkw.thinmp.util;

import android.app.Application;
import android.content.Context;

/**
 * Activity以外のclassからActivityを取得する
 */
public class ActivityUtil extends Application {
    private static Context context;
    private Integer mStatusbarHeight;
    private Integer mDisplayWidth;
    private Integer mDisplayHeight;

    public void onCreate(){
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }

    public Integer getStatusbarHeight() {
        return mStatusbarHeight;
    }

    public void setStatusbarHeight(Integer height) {

        mStatusbarHeight = height;
    }

    public Integer getDisplayWidth() {
        return mDisplayWidth;
    }

    public void setDisplayWidth(Integer width) {
        this.mDisplayWidth = width;
    }

    public Integer getDisplayHeight() {
        return mDisplayHeight;
    }

    public void setDisplayHeight(Integer height) {
        this.mDisplayHeight = height;
    }
}
