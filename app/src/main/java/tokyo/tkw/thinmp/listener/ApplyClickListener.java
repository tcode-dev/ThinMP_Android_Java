package tokyo.tkw.thinmp.listener;

import android.app.Activity;
import android.view.View;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.List;

import tokyo.tkw.thinmp.shortcut.Shortcut;

public class ApplyClickListener implements View.OnClickListener {
    private List<Shortcut> shortcutList;

    public ApplyClickListener(List<Shortcut> shortcutList) {
        this.shortcutList = shortcutList;
    }

    @Override
    public void onClick(View view) {
//        ShortcutEditor shortcutEditor = ShortcutEditor.createInstance();
//        List<String> shortcutIdList = Stream.of(shortcutList).map(Shortcut::getId).collect(Collectors.toList());
//        shortcutEditor.update(shortcutIdList);

        ((Activity) view.getContext()).finish();
    }
}
