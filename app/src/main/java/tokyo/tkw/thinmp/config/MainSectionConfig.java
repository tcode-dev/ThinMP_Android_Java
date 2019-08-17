package tokyo.tkw.thinmp.config;

import android.content.Context;

public class MainSectionConfig extends Config {
    public static final String KEY_SHORTCUT = "shortcut";
    public static final String KEY_RECENTLY_ADDED = "recentlyAdded";
    private static final String KEY_RECENTLY_ADDED_COUNT = "recentlyAddedCount";
    private static final boolean DEFAULT_VISIBILITY = true;
    private static final int DEFAULT_RECENTLY_ADDED_COUNT = 10;

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

    public int getRecentlyAddedCount() {
        return sharedPreferences.getInt(KEY_RECENTLY_ADDED_COUNT, DEFAULT_RECENTLY_ADDED_COUNT);
    }

    public void setRecentlyAddedCount(int count) {
        sharedPreferences.edit().putInt(KEY_RECENTLY_ADDED_COUNT, count).apply();
    }
}