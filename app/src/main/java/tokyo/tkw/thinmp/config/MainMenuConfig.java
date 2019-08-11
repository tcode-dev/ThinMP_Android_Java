package tokyo.tkw.thinmp.config;

import android.content.Context;

import com.annimon.stream.Collectors;
import com.annimon.stream.Optional;
import com.annimon.stream.Stream;

import java.util.Arrays;
import java.util.List;

public class MainMenuConfig extends Config {
    private static final String KEY_SORT = "sort";
    private static final String DELIMITER = ",";
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

    public Optional<List<String>> getOrder() {
        Optional<String> sortOptional = Optional.ofNullable(sharedPreferences.getString(KEY_SORT, null));

        return sortOptional.map(sort -> Arrays.asList(sort.split(DELIMITER)));
    }

    public void setOrder(List<String> list) {
        sharedPreferences.edit().putString(KEY_SORT, Stream.of(list).collect(Collectors.joining(DELIMITER))).apply();
    }
}
