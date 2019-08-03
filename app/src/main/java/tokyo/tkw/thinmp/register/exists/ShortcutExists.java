package tokyo.tkw.thinmp.register.exists;

import tokyo.tkw.thinmp.realm.ShortcutRealm;
import tokyo.tkw.thinmp.register.RealmRegister;

public class ShortcutExists extends RealmRegister {
    public static ShortcutExists createInstance() {
        return new ShortcutExists();
    }

    public boolean exists(String itemId, int type) {
        ShortcutRealm shortcutRealm = ShortcutRealm.createInstance(itemId, type);
        return shortcutRealm != null;
    }
}
