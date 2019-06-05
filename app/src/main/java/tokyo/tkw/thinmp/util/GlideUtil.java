package tokyo.tkw.thinmp.util;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import tokyo.tkw.thinmp.R;

import static android.net.Uri.parse;

public class GlideUtil {
    private static final String ALBUM_ART_URL = "content://media/external/audio/albumart/";
    private static final int DEFAULT_ALBUM_RESOURCE = R.drawable.album;
    private static final int DEFAULT_ARTIST_RESOURCE = R.drawable.artist;

    public static void thumbnail(Context context, String thumbnailId, ImageView view) {
        Glide.with(context).load(createUri(thumbnailId)).into(view);
    }

    private static Uri createUri(String id) {
        return parse(ALBUM_ART_URL + id);
    }
}
