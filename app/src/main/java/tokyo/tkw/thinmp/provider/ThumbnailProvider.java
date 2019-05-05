package tokyo.tkw.thinmp.provider;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import java.io.FileNotFoundException;
import java.io.IOException;
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
    private static final String ALBUM_ART_URL = "content://media/external/audio/albumart/";
    private static final int DEFAULT_ALBUM_RESOURCE = R.drawable.album;
    private static final int DEFAULT_ARTIST_RESOURCE = R.drawable.artist;
    private Context mContext;
    private int mDefaultResourceId;
    private Float mWidth;
    private HashMap<String, Bitmap> mMap = new HashMap<>();

    public static ThumbnailProvider createAlbumThumbnailProvider(Context context, Float width) {
        return new ThumbnailProvider(context, width, DEFAULT_ALBUM_RESOURCE);
    }

    public static ThumbnailProvider createArtistThumbnailProvider(Context context, Float width) {
        return new ThumbnailProvider(context, width, DEFAULT_ARTIST_RESOURCE);
    }

    public ThumbnailProvider() {
        mContext = ActivityUtil.getContext();
        mDefaultResourceId = DEFAULT_ALBUM_RESOURCE;
        mWidth = 0.f;
    }

    public ThumbnailProvider(Context context, Float width, int defaultResourceId) {
        mContext = context;
        mWidth = width;
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

        Bitmap albumArt = decodeBitmap(id);

        if (albumArt != null) {
            mMap.put(id, albumArt);
        }

        return albumArt;
    }

    /**
     * 画像を縮小して読み込む
     *
     * @return
     */
    private Bitmap decodeBitmap(String id) {
        try {
            Uri albumArtUri = getAlbumArtUri(id);
            InputStream inputStream = mContext.getContentResolver().openInputStream(albumArtUri);

            if (inputStream == null) {
                return null;
            }

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.RGB_565;

            if (mWidth == 0.f) {
                Bitmap albumArt =  BitmapFactory.decodeStream(inputStream, null, options);
                inputStream.close();
                return albumArt;
            }

            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(inputStream, null, options);
            inputStream.close();

            options.inSampleSize = calculateInSampleSize(options, mWidth);
            inputStream = mContext.getContentResolver().openInputStream(albumArtUri);

            options.inJustDecodeBounds = false;
            Bitmap albumArt =  BitmapFactory.decodeStream(inputStream, null, options);
            inputStream.close();

            return albumArt;
        } catch (FileNotFoundException err) {
            err.printStackTrace();
        } catch (IOException err) {
            err.printStackTrace();
        }

        return null;
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

    /**
     * @param options
     * @param reqWidth
     * @return
     */
    private int calculateInSampleSize(BitmapFactory.Options options, Float reqWidth) {
        float scaleWidth = (float)options.outWidth / reqWidth;
        int inSampleSize = 1;

        if (scaleWidth > 2) {
            // inSampleSizeには効果的に速くデコードできる2のべき上を設定する
            for (int i = 2; i <= scaleWidth; i *= 2) {
                inSampleSize = i;
            }
        }

        return inSampleSize;
    }
}
