package tokyo.tkw.thinmp.config;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

class Config {
    SharedPreferences sharedPreferences;

    Config(Context context) {
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }
}
