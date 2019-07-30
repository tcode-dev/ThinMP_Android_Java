package tokyo.tkw.thinmp.realm;

import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmObject;

public abstract class RealmRegister <T extends RealmObject>{
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

    public String uuid() {
        return UUID.randomUUID().toString();
    }

    public int increment(Class<T> table, String field) {
        Number max = realm.where(table).max(field);
        return (max != null) ? max.intValue() + 1 : 1;
    }
}
