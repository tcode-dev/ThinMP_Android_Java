package tokyo.tkw.thinmp.provider;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import tokyo.tkw.thinmp.music.Album;
import tokyo.tkw.thinmp.music.ArtistAlbumArt;

public class AllArtistAlbumArtContentProvider extends MediaStoreAudioAlbumsProvider {
    public AllArtistAlbumArtContentProvider(Context context) {
        super(context);
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
                MediaStore.Audio.Albums.ALBUM_ART + " IS NOT NULL",
                null,
                MediaStore.Audio.Albums.ALBUM + " ASC"
        );
    }

    @Override
    Album fetch() {
        return getAlbum();
    }
}
