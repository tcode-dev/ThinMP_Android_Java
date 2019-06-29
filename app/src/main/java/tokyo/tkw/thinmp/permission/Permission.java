package tokyo.tkw.thinmp.permission;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class Permission {
    public static final int PERMISSION_EXTERNAL_STORAGE_CODE = 1;
    private Context mContext;

    private Permission(Context context) {
        mContext = context;
    }

    public static Permission createInstance(Context context) {
        return new Permission(context);
    }

    public boolean isAllowed() {
        int permission = ContextCompat.checkSelfPermission(mContext,
                Manifest.permission.READ_EXTERNAL_STORAGE);

        return permission == PackageManager.PERMISSION_GRANTED;
    }

    public void requestPermissions() {
        ActivityCompat.requestPermissions((Activity) mContext,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                PERMISSION_EXTERNAL_STORAGE_CODE);
    }
}
