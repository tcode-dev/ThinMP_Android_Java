package tokyo.tkw.thinmp.shortcut;

import io.realm.Realm;
import tokyo.tkw.thinmp.realm.ShortcutRealm;

public class ShortcutRegister {
    private Realm realm;

    private ShortcutRegister() {
        realm = Realm.getDefaultInstance();
    }

    public static ShortcutRegister createInstance() {
        return new ShortcutRegister();
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

    public boolean exists(String itemId, int type) {
        return findFirst(itemId, type) != null;
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

    public void beginTransaction() {
        realm.beginTransaction();
    }

    public void commitTransaction() {
        realm.commitTransaction();
    }
}
