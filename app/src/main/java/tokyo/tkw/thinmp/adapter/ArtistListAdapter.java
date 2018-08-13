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

import tokyo.tkw.thinmp.model.Artist;
import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.util.ThumbnailController;

/**
 * Created by tk on 2018/03/22.
 */

public class ArtistListAdapter extends ArrayAdapter<Artist> {
    private int mResource;
    private LayoutInflater mInflater;
    private ThumbnailController mThumbnailController;

    private ArrayList<Artist> mArtistList;

    public ArtistListAdapter(@NonNull Activity context, int resource, @NonNull ArrayList<Artist> artistList) {
        super(context, resource, artistList);

        mResource = resource;
        mInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        mThumbnailController = new ThumbnailController(context);
        mArtistList = artistList;
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

        Artist artist = mArtistList.get(position);

        ImageView thumbnailView = view.findViewById(R.id.thumbnail);
        Bitmap thumbnail = mThumbnailController.getThumbnail(artist.getThumbnailId());
        thumbnailView.setImageBitmap(thumbnail);

        TextView artistNameView = view.findViewById(R.id.artist);
        artistNameView.setText(artist.getName());

        return view;
    }
}
