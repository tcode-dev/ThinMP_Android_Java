package tokyo.tkw.thinmp.provider;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;

import com.annimon.stream.Collectors;
import com.annimon.stream.IntStream;
import com.annimon.stream.Optional;

import java.util.ArrayList;
import java.util.List;

import tokyo.tkw.thinmp.music.Music;

public abstract class MediaStoreAudioProvider<T extends Music> {
    Context mContext;
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

    protected Optional<T> get() {
        init();

        if (!hasCursor()) return Optional.empty();

        Optional<T> item = mCursor.moveToNext() ? Optional.of(fetch()) : Optional.empty();

        destroy();

        return item;
    }

    protected List<T> getList() {
        init();

        List<T> list = new ArrayList<>();

        if (!hasCursor()) return list;

        while (mCursor.moveToNext()) {
            list.add(fetch());
        }

        destroy();

        return list;
    }

    String[] toStringArray(List<String> list) {
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