package tokyo.tkw.thinmp.config;

import android.content.Context;

public class MainSectionConfig extends Config {
    public static final String KEY_SHORTCUT = "shortcut";
    public static final String KEY_RECENTLY_ADDED = "recentlyAdded";
    private static final boolean DEFAULT_VISIBILITY = true;

    private MainSectionConfig(Context context) {
        super(context);
    }

    public static MainSectionConfig createInstance(Context context) {
        return new MainSectionConfig(context);
    }

    public boolean getShortcutVisibility() {
        return sharedPreferences.getBoolean(KEY_SHORTCUT, DEFAULT_VISIBILITY);
    }

    public void setShortcutVisibility(boolean visibility) {
        sharedPreferences.edit().putBoolean(KEY_SHORTCUT, visibility).apply();
    }

    public boolean getRecentlyAddedVisibility() {
        return sharedPreferences.getBoolean(KEY_RECENTLY_ADDED, DEFAULT_VISIBILITY);
    }

    public void setRecentlyAddedVisibility(boolean visibility) {
        sharedPreferences.edit().putBoolean(KEY_RECENTLY_ADDED, visibility).apply();
    }
}