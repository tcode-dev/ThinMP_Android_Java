package tokyo.tkw.thinmp.util;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.Iterator;

import tokyo.tkw.thinmp.R;

import static android.net.Uri.parse;

public class GlideUtil {
    private static final String ALBUM_ART_URL = "content://media/external/audio/albumart/";
    public static final int ALBUM_RESOURCE_ID = R.drawable.album;
    public static final int ARTIST_RESOURCE_ID = R.drawable.artist;
    private static final int DEFAULT_RESOURCE_ID = ALBUM_RESOURCE_ID;

    public static void bitmap(String albumArtId, ImageView view) {
        bitmap(albumArtId, view, DEFAULT_RESOURCE_ID);
    }

    public static void bitmap(String albumArtId, ImageView view, int resourceId) {
        ArrayList<String> albumArtIdList = new ArrayList<String>() {{
            add(albumArtId);
        }};
        bitmap(albumArtIdList, view, resourceId);
    }

    public static void bitmap(ArrayList<String> albumArtIdList, ImageView view) {
        bitmap(albumArtIdList, view, DEFAULT_RESOURCE_ID);
    }

    public static void bitmap(ArrayList<String> albumArtIdList, ImageView view,
                              int resourceId) {
        DoGlide glide = new DoGlide(albumArtIdList, view, resourceId);
        glide.exec();
    }

    private static Uri createUri(String id) {
        return parse(ALBUM_ART_URL + id);
    }

    private static class DoGlide {
        private Iterator mIterator;
        private ImageView mView;
        private int mResourceId;

        public DoGlide(ArrayList<String> albumArtIdList, ImageView view,
                       int resourceId) {
            mIterator = albumArtIdList.iterator();
            mView = view;
            mResourceId = resourceId;
        }

        public void exec() {
            if (mIterator.hasNext()) {
                Glide.with(mView).asBitmap().load(createUri((String) mIterator.next())).listener(new RequestListener<Bitmap>() {
                    Handler handler = new Handler();

                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model,
                                                Target<Bitmap> target, boolean isFirstResource) {
                        handler.post(() -> exec());
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model,
                                                   Target<Bitmap> target, DataSource dataSource,
                                                   boolean isFirstResource) {
                        return false;
                    }
                }).into(mView);
            } else {
                Glide.with(mView).asBitmap().load(mResourceId).into(mView);
            }
        }
    }
}
