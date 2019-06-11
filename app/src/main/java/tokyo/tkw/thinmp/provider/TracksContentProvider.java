package tokyo.tkw.thinmp.provider;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.text.TextUtils;

import java.util.ArrayList;

public class TracksContentProvider extends MediaStoreAudioMediaProvider {
    private ArrayList<String> mTrackIdList;

    public TracksContentProvider(Context context, ArrayList<String> trackIdList) {
        super(context);

        mTrackIdList = trackIdList;
    }

    @Override
    Cursor createCursor() {
        return mContext.getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                new String[]{
                        MediaStore.Audio.Media._ID,
                        MediaStore.Audio.Media.TITLE,
                        MediaStore.Audio.Media.ARTIST_ID,
                        MediaStore.Audio.Media.ARTIST,
                        MediaStore.Audio.Media.ALBUM_ID,
                        MediaStore.Audio.Media.ALBUM,
                        MediaStore.Audio.Media.DURATION
                },
                MediaStore.Audio.Media._ID + " IN (" + TextUtils.join(",", mTrackIdList) + ") AND" +
                        " " + MediaStore.Audio.Media.IS_MUSIC + " = 1",
                null,
                null
        );
    }
}
