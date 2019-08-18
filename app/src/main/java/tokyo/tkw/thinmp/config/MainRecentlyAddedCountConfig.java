package tokyo.tkw.thinmp.config;

import android.annotation.SuppressLint;
import android.content.Context;

import java.util.Arrays;

import tokyo.tkw.thinmp.R;

public class MainRecentlyAddedCountConfig extends Config {
    private static final String KEY_RECENTLY_ADDED_COUNT = "recentlyAddedCount";
    private static final int DEFAULT_RECENTLY_ADDED_COUNT = 10;
    private Context context;

    private MainRecentlyAddedCountConfig(Context context) {
        super(context);
        this.context = context;
    }

    public static MainRecentlyAddedCountConfig createInstance(Context context) {
        return new MainRecentlyAddedCountConfig(context);
    }

    public int getRecentlyAddedCount() {
        return sharedPreferences.getInt(KEY_RECENTLY_ADDED_COUNT, DEFAULT_RECENTLY_ADDED_COUNT);
    }

    public void setRecentlyAddedCount(int count) {
        sharedPreferences.edit().putInt(KEY_RECENTLY_ADDED_COUNT, count).apply();
    }

    public int getRecentlyAddedPosition() {
        @SuppressLint("ResourceType") String[] recentlyAddedCountArray =
                context.getResources().getStringArray(R.array.recently_added_count_array);

        return Arrays.asList(recentlyAddedCountArray).indexOf(String.valueOf(getRecentlyAddedCount()));
    }
}
