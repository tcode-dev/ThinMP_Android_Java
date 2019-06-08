package tokyo.tkw.thinmp.provider;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.text.TextUtils;

import java.util.ArrayList;

import tokyo.tkw.thinmp.music.Artist;

public class ArtistsContentProvider extends MusicContentProvider<Artist> {
    private ArrayList<String> mArtistIdList;

    public ArtistsContentProvider(Context context, ArrayList<String> artistIdList) {
        super(context);

        mArtistIdList = artistIdList;
    }

    @Override
    Cursor createCursor() {
        return mContext.getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                new String[]{
                        "DISTINCT " + MediaStore.Audio.Media.ARTIST_ID,
                        MediaStore.Audio.Media.ARTIST
                },
                MediaStore.Audio.Media.ARTIST_ID + " IN (" + TextUtils.join(",", mArtistIdList) + ") AND " + MediaStore.Audio.Media.IS_MUSIC + " = 1",
                null,
                MediaStore.Audio.Media.ARTIST + " ASC"
        );
    }

    @Override
    Artist fetch() {
        return getArtist();
    }
}
