package tokyo.tkw.thinmp.register.add;

import tokyo.tkw.thinmp.realm.ShortcutRealm;
import tokyo.tkw.thinmp.register.RealmRegister;

public class ShortcutAdder extends RealmRegister {
    public static ShortcutAdder createInstance() {
        return new ShortcutAdder();
    }

    public void add(String itemId, int type) {
        beginTransaction();

        realm.createObject(ShortcutRealm.class, uuid())
                .set(itemId, type, increment(ShortcutRealm.class, ShortcutRealm.ORDER));

        commitTransaction();
    }
}
