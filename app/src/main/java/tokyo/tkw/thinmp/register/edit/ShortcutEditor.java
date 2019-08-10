package tokyo.tkw.thinmp.register.edit;

import com.annimon.stream.Collectors;
import com.annimon.stream.Optional;
import com.annimon.stream.Stream;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import io.realm.RealmResults;
import tokyo.tkw.thinmp.realm.ShortcutRealm;
import tokyo.tkw.thinmp.register.RealmRegister;
import tokyo.tkw.thinmp.register.delete.ShortcutDeleter;
import tokyo.tkw.thinmp.shortcut.Shortcut;

public class ShortcutEditor extends RealmRegister {
    public static ShortcutEditor createInstance() {
        return new ShortcutEditor();
    }

    public void update(List<Shortcut> fromList, List<Shortcut> toList) {
        beginTransaction();

        List<Shortcut> deleteList = subtract(fromList, toList);
        delete(deleteList);
        sort(toList);

        commitTransaction();
    }

    private List<Shortcut> subtract(List<Shortcut> list1, List<Shortcut> list2) {

        HashSet<String> set = new HashSet<>(Stream.of(list2).map(Shortcut::getId).toList());

        return Stream.of(list1)
                .filter(shortcut -> (!set.contains(shortcut.getId())))
                .toList();
    }

    private void delete(List<Shortcut> list) {
        if (list.isEmpty()) return;

        ShortcutDeleter shortcutDeleter = ShortcutDeleter.createInstance();
        Map<String, Integer> typeMap = new HashMap<String, Integer>() {
            {
                put(ShortcutRealm.ARTIST, ShortcutRealm.TYPE_ARTIST);
                put(ShortcutRealm.ALBUM, ShortcutRealm.TYPE_ALBUM);
                put(ShortcutRealm.PLAYLIST, ShortcutRealm.TYPE_PLAYLIST);
            }
        };
        Stream.of(list).forEach(shortcut -> {
            shortcutDeleter.temporaryDelete(shortcut.getItemId(), typeMap.get(shortcut.getType()));
        });
    }

    private void sort(List<Shortcut> list) {
        List<String> idList = Stream.of(list).map(Shortcut::getId).toList();

        RealmResults<ShortcutRealm> realmResults = realm.where(ShortcutRealm.class)
                .in(ShortcutRealm.ID, idList.toArray(new String[0]))
                .findAll();

        Map<String, ShortcutRealm> map = Stream.of(realmResults)
                .collect(Collectors.toMap(ShortcutRealm::getId, shortcutRealm -> shortcutRealm));

        // 最新を上に持ってきたいので逆順にする
        Collections.reverse(idList);

        Stream.of(idList).forEachIndexed((i, id) -> {
            Optional.ofNullable(map.get(id)).ifPresent(shortcutRealm -> shortcutRealm.setOrder(i + 1));
        });
    }
}
