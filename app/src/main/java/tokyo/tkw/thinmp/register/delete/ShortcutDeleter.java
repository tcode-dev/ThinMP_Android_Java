package tokyo.tkw.thinmp.register.delete;

import java.util.List;

import tokyo.tkw.thinmp.realm.ShortcutRealm;
import tokyo.tkw.thinmp.register.RealmRegister;

public class ShortcutDeleter extends RealmRegister {
    public static ShortcutDeleter createInstance() {
        return new ShortcutDeleter();
    }

    public void delete(String itemId, int type) {
        beginTransaction();

        ShortcutRealm shortcutRealm = findFirst(itemId, type);
        shortcutRealm.deleteFromRealm();

        commitTransaction();
    }

    public void delete(List<String> itemIdList, int type) {
        beginTransaction();

        realm.where(ShortcutRealm.class)
                .in(ShortcutRealm.ITEM_ID, itemIdList.toArray(new String[0]))
                .equalTo(ShortcutRealm.TYPE, type)
                .findAll()
                .deleteAllFromRealm();

        commitTransaction();
    }

    void temporaryDelete(String itemId, int type) {
        ShortcutRealm shortcutRealm = findFirst(itemId, type);
        shortcutRealm.deleteFromRealm();
    }

    private ShortcutRealm findFirst(String itemId, int type) {
        return ShortcutRealm.createInstance(itemId, type);
    }
}
