package tokyo.tkw.thinmp.activity;

import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import tokyo.tkw.thinmp.permission.Permission;

public abstract class BaseActivity extends AppCompatActivity {
    abstract protected void init();

    protected void initWithPermissionCheck() {
        Permission permission = Permission.createInstance(this);

        if (permission.hasSelfPermissions()) {
            init();
        } else {
            permission.requestPermissions();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        // If request is cancelled, the result arrays are empty.
        if (requestCode == Permission.PERMISSION_EXTERNAL_STORAGE_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                recreate();
            }
        }
    }
}
