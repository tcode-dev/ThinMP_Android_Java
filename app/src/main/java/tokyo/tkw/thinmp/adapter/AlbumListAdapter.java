package tokyo.tkw.thinmp.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import tokyo.tkw.thinmp.model.Album;
import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.util.ThumbnailController;

/**
 * Created by tk on 2018/03/22.
 */

public class AlbumListAdapter extends ArrayAdapter<Album> {
    private int mResource;
    private LayoutInflater mInflater;
    private ThumbnailController mThumbnailController;

    private ArrayList<Album> mAlbumList;

    public AlbumListAdapter(@NonNull Activity context, int resource, @NonNull ArrayList<Album> albumList) {
        super(context, resource, albumList);

        mResource = resource;
        mInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        mThumbnailController = new ThumbnailController(context);
        mAlbumList = albumList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view;

        if (convertView != null) {
            view = convertView;
        } else {
            view = mInflater.inflate(mResource, null);
        }

        Album album = mAlbumList.get(position);

        ImageView thumbnailView = view.findViewById(R.id.thumbnail);
        Bitmap thumbnailBitMap = mThumbnailController.getThumbnail(album.getThumbnailId());
        thumbnailView.setImageBitmap(thumbnailBitMap);

        TextView albumNameView = view.findViewById(R.id.albumName);
        albumNameView.setText(album.getName());

        TextView artistNameView = view.findViewById(R.id.artistName);
        artistNameView.setText(album.getArtistName());

        return view;
    }
}
