package tokyo.tkw.thinmp;

import android.app.Application;

import io.realm.Realm;
import tokyo.tkw.thinmp.util.GlideUtil;

public class ThinMPApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // アプリケーション内で一度だけ初期化する
        Realm.init(this);
        GlideUtil.init(this);
    }
}
