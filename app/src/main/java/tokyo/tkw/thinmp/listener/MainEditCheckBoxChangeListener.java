package tokyo.tkw.thinmp.listener;

import android.widget.CompoundButton;

import java.util.HashMap;

public class MainEditCheckBoxChangeListener implements CompoundButton.OnCheckedChangeListener {
    private String key;
    private HashMap<String, Boolean> stateMap;

    public MainEditCheckBoxChangeListener(String key, HashMap<String, Boolean> stateMap) {
        this.key = key;
        this.stateMap = stateMap;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        stateMap.put(key, isChecked);
    }
}