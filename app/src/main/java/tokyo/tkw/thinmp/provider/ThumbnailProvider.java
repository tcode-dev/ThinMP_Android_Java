package tokyo.tkw.thinmp.provider;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.util.ActivityUtil;

import static android.net.Uri.parse;

/**
 * ThumbnailProvider
 */
public class ThumbnailProvider {
    private final String ALBUM_ART_URL = "content://media/external/audio/albumart/";
    private Context mContext;
    private int mDefaultResourceId;
    private HashMap<String, Bitmap> mMap = new HashMap<>();

    public ThumbnailProvider() {
        this(R.drawable.album);
    }

    public ThumbnailProvider(int defaultResourceId) {
        mContext = ActivityUtil.getContext();
        mDefaultResourceId = defaultResourceId;
    }

    public Bitmap getThumbnail(String id) {
        Bitmap albumArt = getAlbumArt(id);
        if (albumArt != null) {
            return albumArt;
        }

        return getDefaultBitmap();
    }

    public Bitmap getThumbnail(List<String> idList) {
        for (String id : idList) {
            Bitmap albumArt = getAlbumArt(id);
            if (albumArt != null) {
                return albumArt;
            }
        }
        return getDefaultBitmap();
    }

    /**
     * アルバムアートを取得
     *
     * @return
     */
    private Bitmap getAlbumArt(String id) {
        if (hasThumbnail(id)) {
            return mMap.get(id);
        }

        Bitmap albumArt = null;

        try {
            Uri albumArtUri = getAlbumArtUri(id);
            InputStream is = mContext.getContentResolver().openInputStream(albumArtUri);

            if (is != null) {
                albumArt = BitmapFactory.decodeStream(is);
            }
        } catch (FileNotFoundException err) {
            err.printStackTrace();
        }

        if (albumArt != null) {
            mMap.put(id, albumArt);
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

    /**
     * デフォルト画像のbitmapを返す
     *
     * @return
     */
    private Bitmap getDefaultBitmap() {
        return BitmapFactory.decodeResource(mContext.getResources(), mDefaultResourceId);
    }

    private Boolean hasThumbnail(String id) {
        return mMap.containsKey(id);
    }
}
