package tokyo.tkw.thinmp.util;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.Iterator;

import tokyo.tkw.thinmp.R;

import static android.net.Uri.parse;

public class GlideUtil {
    public static final int ALBUM_RESOURCE_ID = R.drawable.default_album_art;
    public static final int ARTIST_RESOURCE_ID = R.drawable.default_artist_art;
    private static final int DEFAULT_RESOURCE_ID = ALBUM_RESOURCE_ID;
    private static final String ALBUM_ART_URL = "content://media/external/audio/albumart/";
    private static final int RADIUS = 10;

    public static void bitmap(String albumArtId, ImageView view) {
        bitmap(albumArtId, view, DEFAULT_RESOURCE_ID);
    }

    public static void bitmap(String albumArtId, ImageView view, int resourceId) {
        if (albumArtId != null) {
            albumArt(albumArtId, view, resourceId);
        } else {
            drawable(view, resourceId);
        }
    }

    private static void albumArt(String albumArtId, ImageView view, int resourceId) {
        Glide.with(view)
                .asBitmap()
                .load(createUri(albumArtId))
                .transform(new FitCenter(), new RoundedCorners(RADIUS))
                .listener(new RequestListener<Bitmap>() {
                    Handler handler = new Handler();

                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model,
                                                Target<Bitmap> target,
                                                boolean isFirstResource) {
                        handler.post(() -> drawable(view, resourceId));
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model,
                                                   Target<Bitmap> target,
                                                   DataSource dataSource,
                                                   boolean isFirstResource) {
                        return false;
                    }
                })
                .into(view);
    }

    public static void drawable(ImageView view, int resourceId) {
        Glide.with(view)
                .asBitmap()
                .load(resourceId)
                .transform(new FitCenter(), new RoundedCorners(RADIUS))
                .into(view);
    }

    private static Uri createUri(String id) {
        return parse(ALBUM_ART_URL + id);
    }
}
