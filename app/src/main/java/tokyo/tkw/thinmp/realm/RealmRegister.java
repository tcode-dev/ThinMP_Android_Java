package tokyo.tkw.thinmp.realm;

import io.realm.Realm;

public abstract class RealmRegister {
    protected Realm realm;

    protected RealmRegister() {
        realm = Realm.getDefaultInstance();
    }

    public void beginTransaction() {
        realm.beginTransaction();
    }

    public void commitTransaction() {
        realm.commitTransaction();
    }

    public void cancelTransaction() {
        realm.cancelTransaction();
    }
}
