package tokyo.tkw.thinmp.util;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;

import static android.net.Uri.parse;

/**
 * Created by tk on 2018/03/22.
 */

public class ThumbnailController {
    private final String ALBUM_ART_URL = "content://media/external/audio/albumart/";

    private HashMap<String, Bitmap> mMap = new HashMap<String, Bitmap>();
    private Activity mContext;

    public ThumbnailController(Activity context) {
        mContext = context;
    }

    public Bitmap getThumbnail(String id) {
        if (! hasThumbnail(id)) {
            setThumbnail(id);
        }

        return mMap.get(id);
    }

    public void setThumbnail(String id) {
        Bitmap thumbnail = getAlbumArt(id);
        if (thumbnail != null) {
            mMap.put(id, thumbnail);
        }
    }

    private Boolean hasThumbnail(String id) {
        return mMap.containsKey(id);
    }

    /**
     * アルバムアートを取得
     *
     * @return
     */
    private Bitmap getAlbumArt(String id) {
        Bitmap albumArt = null;

        try {
            Uri albumArtUri = getAlbumArtUri(id);
            InputStream is = mContext.getContentResolver().openInputStream(albumArtUri);

            if (is != null) {
                albumArt = BitmapFactory.decodeStream(is);
            }
        } catch (FileNotFoundException err) {
//            err.printStackTrace();
        }

        return albumArt;
    }

    /**
     * アルバムアートのuriを取得
     *
     * @return
     */
    private Uri getAlbumArtUri(String id) {
        String url = ALBUM_ART_URL + id;
        Uri uri = parse(url);

        return uri;
    }
}