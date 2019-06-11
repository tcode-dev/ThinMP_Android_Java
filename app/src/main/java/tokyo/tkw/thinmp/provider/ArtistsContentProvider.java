package tokyo.tkw.thinmp.provider;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import java.util.ArrayList;

public class ArtistsContentProvider extends MediaStoreAudioArtistsProvider {
    private String[] mArtistIdList;
    private String mPlaceholders;

    public ArtistsContentProvider(Context context, ArrayList<String> artistIdList) {
        super(context);

        mArtistIdList = toStringArray(artistIdList);
        mPlaceholders = makePlaceholders(artistIdList.size());
    }

    @Override
    Cursor createCursor() {
        return mContext.getContentResolver().query(
                MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI,
                new String[]{
                        MediaStore.Audio.Artists._ID,
                        MediaStore.Audio.Artists.ARTIST
                },
                MediaStore.Audio.Artists._ID + " IN (" + mPlaceholders + ")",
                mArtistIdList,
                MediaStore.Audio.Artists.ARTIST + " ASC"
        );
    }
}
