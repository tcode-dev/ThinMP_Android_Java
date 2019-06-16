package tokyo.tkw.thinmp.provider;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.text.TextUtils;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.annimon.stream.Collectors;
import com.annimon.stream.IntStream;

import java.util.ArrayList;

import tokyo.tkw.thinmp.music.Music;

public abstract class MediaStoreAudioProvider<T extends Music> {
    private final int PERMISSION_CODE = 1;
    protected Context mContext;
    protected Cursor mCursor;

    abstract Cursor createCursor();

    abstract T fetch();

    public MediaStoreAudioProvider(Context context) {
        mContext = context;
    }

    public T get() {
        ArrayList<T> list = getList();

        if (list.isEmpty()) return null;

        return list.get(0);
    }

    public ArrayList<T> getList() {
        init();

        ArrayList<T> list = fetchAll();

        destroy();

        return list;
    }

    protected String[] toStringArray(ArrayList<String> list) {
        return list.toArray(new String[list.size()]);
    }

    protected String makePlaceholders(int size) {
        return TextUtils.join(",",
                IntStream.range(0, size).boxed().map((i) -> "?").collect(Collectors.toList()));
    }

    private void init() {
        if (isAllowed()) {
            mCursor = createCursor();
        } else {
            requestPermissions();
        }
    }

    private boolean hasCursor() {
        return mCursor != null;
    }

    private ArrayList<T> fetchAll() {
        ArrayList<T> list = new ArrayList<>();

        if (!hasCursor()) return list;

        while (mCursor.moveToNext()) {
            list.add(fetch());
        }

        return list;
    }

    private boolean isAllowed() {
        int permission = ContextCompat.checkSelfPermission(mContext,
                Manifest.permission.READ_EXTERNAL_STORAGE);

        return permission == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions((Activity) mContext,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_CODE);
    }

    private void destroy() {
        if (!hasCursor()) return;

        mCursor.close();
        mCursor = null;
    }
}