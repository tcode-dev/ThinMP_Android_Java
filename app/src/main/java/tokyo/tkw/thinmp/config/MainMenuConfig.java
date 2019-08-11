package tokyo.tkw.thinmp.config;

import android.content.Context;

public class MainMenuConfig extends Config {
    private static final boolean DEFAULT_VALUE = true;

    private MainMenuConfig(Context context) {
        super(context);
    }

    public static MainMenuConfig createInstance(Context context) {
        return new MainMenuConfig(context);
    }

    public boolean getVisibility(String key) {
        return sharedPreferences.getBoolean(key, DEFAULT_VALUE);
    }

    public void setVisibility(String key, boolean visibility) {
        sharedPreferences.edit().putBoolean(key, visibility).apply();
    }
}
