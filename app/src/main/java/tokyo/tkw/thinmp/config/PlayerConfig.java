package tokyo.tkw.thinmp.config;

import android.content.Context;

public class PlayerConfig extends Config {
    private static final String KEY_REPEAT = "repeat";
    private static final String KEY_SHUFFLE = "shuffle";
    private static final int DEFAULT_VALUE_REPEAT = 0;
    private static final boolean DEFAULT_VALUE_SHUFFLE = false;

    private PlayerConfig(Context context) {
        super(context);
    }

    public static PlayerConfig createInstance(Context context) {
        return new PlayerConfig(context);
    }

    public int getRepeat() {
        return sharedPreferences.getInt(KEY_REPEAT, DEFAULT_VALUE_REPEAT);
    }

    public void setRepeat(int repeat) {
        sharedPreferences.edit().putInt(KEY_REPEAT, repeat).apply();
    }

    public boolean getShuffle() {
        return sharedPreferences.getBoolean(KEY_SHUFFLE, DEFAULT_VALUE_SHUFFLE);
    }

    public void setShuffle(boolean shuffle) {
        sharedPreferences.edit().putBoolean(KEY_SHUFFLE, shuffle).apply();
    }
}
