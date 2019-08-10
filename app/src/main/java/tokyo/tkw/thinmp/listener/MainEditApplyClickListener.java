package tokyo.tkw.thinmp.listener;

import android.app.Activity;
import android.view.View;

import java.util.List;

import tokyo.tkw.thinmp.register.edit.ShortcutEditor;
import tokyo.tkw.thinmp.shortcut.Shortcut;

public class MainEditApplyClickListener implements View.OnClickListener {
    private List<Shortcut> fromList;
    private List<Shortcut> toList;

    public MainEditApplyClickListener(List<Shortcut> fromList, List<Shortcut> toList) {
        this.fromList = fromList;
        this.toList = toList;
    }

    @Override
    public void onClick(View view) {
        ShortcutEditor shortcutEditor = ShortcutEditor.createInstance();
        shortcutEditor.update(fromList, toList);

        ((Activity) view.getContext()).finish();
    }
}
