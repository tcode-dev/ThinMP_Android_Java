package tokyo.tkw.thinmp.provider;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

public class ArtistContentProvider extends MediaStoreAudioArtistsProvider {
    private String mArtistId;

    public ArtistContentProvider(Context context, String artistId) {
        super(context);

        mArtistId = artistId;
    }

    @Override
    Cursor createCursor() {
        return mContext.getContentResolver().query(
                MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI,
                new String[]{
                        MediaStore.Audio.Media._ID,
                        MediaStore.Audio.Artists.ARTIST,
                        MediaStore.Audio.Artists.NUMBER_OF_ALBUMS,
                        MediaStore.Audio.Artists.NUMBER_OF_TRACKS
                },
                MediaStore.Audio.Media._ID + " = ?",
                new String[]{
                        mArtistId
                },
                null
        );
    }
}
