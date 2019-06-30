package tokyo.tkw.thinmp.config;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Config {
    private static final String KEY_REPEAT = "repeat";
    private static final String KEY_SHUFFLE = "shuffle";
    private static final int DEFAULT_VALUE_REPEAT = 0;
    private static final boolean DEFAULT_VALUE_SHUFFLE = false;
    private SharedPreferences mSharedPreferences;

    public Config(Context context) {
        this.mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setRepeat(int repeat) {
        mSharedPreferences.edit().putInt(KEY_REPEAT, repeat).apply();
    }

    public int getRepeat() {
        return mSharedPreferences.getInt(KEY_REPEAT, DEFAULT_VALUE_REPEAT);
    }

    public void setShuffle(boolean shuffle) {
        mSharedPreferences.edit().putBoolean(KEY_SHUFFLE, shuffle).apply();
    }

    public boolean getShuffle() {
        return mSharedPreferences.getBoolean(KEY_SHUFFLE, DEFAULT_VALUE_SHUFFLE);
    }
}
