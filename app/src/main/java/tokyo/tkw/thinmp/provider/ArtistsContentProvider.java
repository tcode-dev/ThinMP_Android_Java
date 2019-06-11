package tokyo.tkw.thinmp.provider;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.text.TextUtils;

import java.util.ArrayList;

public class ArtistsContentProvider extends MediaStoreAudioArtistsProvider {
    private ArrayList<String> mArtistIdList;

    public ArtistsContentProvider(Context context, ArrayList<String> artistIdList) {
        super(context);

        mArtistIdList = artistIdList;
    }

    @Override
    Cursor createCursor() {
        return mContext.getContentResolver().query(
                MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI,
                new String[]{
                        MediaStore.Audio.Artists._ID,
                        MediaStore.Audio.Artists.ARTIST
                },
                MediaStore.Audio.Artists._ID + " IN (" + TextUtils.join(",", mArtistIdList) + ")",
                null,
                MediaStore.Audio.Artists.ARTIST + " ASC"
        );
    }
}
