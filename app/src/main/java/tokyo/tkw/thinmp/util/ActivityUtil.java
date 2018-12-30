package tokyo.tkw.thinmp.util;

import android.app.Application;
import android.content.Context;

/**
 * Activity以外のclassからActivityを取得する
 */
public class ActivityUtil extends Application {
    private static Context context;

    public static Context getContext() {
        return context;
    }

    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }
}
