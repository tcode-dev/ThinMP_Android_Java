package tokyo.tkw.thinmp.register;

import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmObject;

public abstract class RealmRegister<T extends RealmObject> {
    protected Realm realm;

    protected RealmRegister() {
        realm = Realm.getDefaultInstance();
    }

    protected void beginTransaction() {
        realm.beginTransaction();
    }

    protected void commitTransaction() {
        realm.commitTransaction();
    }

    protected String uuid() {
        return UUID.randomUUID().toString();
    }

    protected int increment(Class<T> table, String field) {
        Number max = realm.where(table).max(field);
        return (max != null) ? max.intValue() + 1 : 1;
    }
}
