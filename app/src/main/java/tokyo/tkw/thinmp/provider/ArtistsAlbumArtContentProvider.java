package tokyo.tkw.thinmp.provider;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import java.util.ArrayList;

import tokyo.tkw.thinmp.music.Album;

public class ArtistsAlbumArtContentProvider extends MediaStoreAudioAlbumsProvider {
    private String[] mArtistIdList;
    private String mPlaceholders;

    public ArtistsAlbumArtContentProvider(Context context, ArrayList<String> artistIdList) {
        super(context);

        mArtistIdList = toStringArray(artistIdList);
        mPlaceholders = makePlaceholders(artistIdList.size());
    }

    @Override
    Cursor createCursor() {
        return mContext.getContentResolver().query(
                MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                new String[]{
                        MediaStore.Audio.Albums._ID,
                        MediaStore.Audio.Albums.ALBUM,
                        MediaStore.Audio.Media.ARTIST_ID,
                        MediaStore.Audio.Albums.ARTIST,
                        MediaStore.Audio.Albums.ALBUM_ART
                },
                MediaStore.Audio.Media.ARTIST_ID + " IN (" + mPlaceholders + ") AND " + MediaStore.Audio.Albums.ALBUM_ART + " IS NOT NULL",
                mArtistIdList,
                MediaStore.Audio.Albums.ALBUM + " ASC"
        );
    }

    @Override
    Album fetch() {
        return getAlbum();
    }
}
