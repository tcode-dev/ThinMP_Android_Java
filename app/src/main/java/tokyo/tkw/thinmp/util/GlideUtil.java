package tokyo.tkw.thinmp.util;

import android.content.Context;
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

    public static void bitmap(String thumbnailId, ImageView view) {
        bitmap(thumbnailId, view, DEFAULT_RESOURCE_ID);
    }

    public static void bitmap(String thumbnailId, ImageView view, int resourceId) {
        ArrayList<String> thumbnailIdList = new ArrayList<String>() {{
            add(thumbnailId);
        }};
        bitmap(thumbnailIdList, view, resourceId);
    }

    public static void bitmap(ArrayList<String> thumbnailIdList, ImageView view) {
        bitmap(thumbnailIdList, view, DEFAULT_RESOURCE_ID);
    }

    public static void bitmap(ArrayList<String> thumbnailIdList, ImageView view,
                              int resourceId) {
        DoGlide glide = new DoGlide(view.getContext(), thumbnailIdList, view, resourceId);
        glide.exec();
    }

    private static Uri createUri(String id) {
        return parse(ALBUM_ART_URL + id);
    }

    private static class DoGlide {
        private Context mContext;
        private Iterator mIterator;
        private ImageView mView;
        private int mResourceId;

        public DoGlide(Context context, ArrayList<String> thumbnailIdList, ImageView view,
                       int resourceId) {
            mContext = context;
            mIterator = thumbnailIdList.iterator();
            mView = view;
            mResourceId = resourceId;
        }

        public void exec() {
            if (mIterator.hasNext()) {
                Glide.with(mContext).asBitmap().load(createUri((String) mIterator.next())).listener(new RequestListener<Bitmap>() {
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
                Glide.with(mContext).asBitmap().load(mResourceId).into(mView);
            }
        }
    }
}
