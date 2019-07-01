package tokyo.tkw.thinmp.provider;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;

import com.annimon.stream.Collectors;
import com.annimon.stream.IntStream;

import java.util.ArrayList;

import tokyo.tkw.thinmp.music.Music;

public abstract class MediaStoreAudioProvider<T extends Music> {
    private Context mContext;
    Cursor mCursor;
    Uri uri;
    String[] projection;
    String selection;
    String[] selectionArgs;
    String sortOrder;

    abstract T fetch();

    MediaStoreAudioProvider(Context context) {
        mContext = context;
    }

    protected T get() {
        init();

        mCursor.moveToNext();

        if (!hasCursor()) return null;

        T item = fetch();

        destroy();

        return item;
    }

    protected ArrayList<T> getList() {
        init();

        ArrayList<T> list = new ArrayList<>();

        if (!hasCursor()) return list;

        while (mCursor.moveToNext()) {
            list.add(fetch());
        }

        destroy();

        return list;
    }

    String[] toStringArray(ArrayList<String> list) {
        return list.toArray(new String[0]);
    }

    String makePlaceholders(int size) {
        return TextUtils.join(",",
                IntStream.range(0, size).boxed().map((i) -> "?").collect(Collectors.toList()));
    }

    private void init() {
        mCursor = createCursor();
    }

    private boolean hasCursor() {
        return mCursor != null;
    }

    private Cursor createCursor() {
        return mContext.getContentResolver().query(
                uri,
                projection,
                selection,
                selectionArgs,
                sortOrder);
    }

    private void destroy() {
        if (!hasCursor()) return;

        mCursor.close();
        mCursor = null;
    }
}