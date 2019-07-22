package tokyo.tkw.thinmp.shortcut;

import tokyo.tkw.thinmp.realm.RealmRegister;
import tokyo.tkw.thinmp.realm.ShortcutRealm;

public class ShortcutRegister extends RealmRegister {
    public static ShortcutRegister createInstance() {
        return new ShortcutRegister();
    }

    public boolean exists(String itemId, int type) {
        return findFirst(itemId, type) != null;
    }

    /**
     * 追加
     */
    public void add(String itemId, int type) {
        beginTransaction();

        realm.createObject(ShortcutRealm.class, nextId()).set(itemId, type);

        commitTransaction();
    }

    /**
     * 削除
     */
    public void remove(String itemId, int type) {
        beginTransaction();

        ShortcutRealm shortcutRealm = findFirst(itemId, type);
        shortcutRealm.deleteFromRealm();

        commitTransaction();
    }

    /**
     * 仮削除
     */
    public void temporaryRemove(String itemId, int type) {
        ShortcutRealm shortcutRealm = findFirst(itemId, type);
        shortcutRealm.deleteFromRealm();
    }

    private ShortcutRealm findFirst(String itemId, int type) {
        return realm.where(ShortcutRealm.class)
                .equalTo(ShortcutRealm.ITEM_ID, itemId)
                .equalTo(ShortcutRealm.TYPE, type)
                .findFirst();
    }

    private int nextId() {
        Number maxId = realm.where(ShortcutRealm.class).max("id");

        return (maxId != null) ? maxId.intValue() + 1 : 1;
    }
}
