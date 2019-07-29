package tokyo.tkw.thinmp.shortcut;

import java.util.List;

import tokyo.tkw.thinmp.realm.FavoriteArtistRealm;
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

        realm.createObject(ShortcutRealm.class, uuid()).set(itemId, type);

        commitTransaction();
    }

    /**
     * 削除
     */
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

    /**
     * 仮削除
     */
    public void temporaryDelete(String itemId, int type) {
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
