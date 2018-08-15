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

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.util.ThumbnailController;
import tokyo.tkw.thinmp.model.Track;

/**
 * Created by tk on 2018/03/22.
 */

public class TrackListAdapter extends ArrayAdapter<Track> {
    private int mResource;
    private LayoutInflater mInflater;
    private ThumbnailController mThumbnailController;

    private ArrayList<Track> mTrackList;

    public TrackListAdapter(@NonNull Activity context, int resource, @NonNull ArrayList<Track> trackList) {
        super(context, resource, trackList);

        mResource = resource;
        mInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        mThumbnailController = new ThumbnailController(context);
        mTrackList = trackList;
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

        Track item = mTrackList.get(position);

        ImageView thumbnailView = view.findViewById(R.id.thumbnail);
        Bitmap thumbnail = mThumbnailController.getThumbnail(item.getThumbnailId());
        thumbnailView.setImageBitmap(thumbnail);

        TextView track = view.findViewById(R.id.track);
        track.setText(item.getTitle());

        TextView artist = view.findViewById(R.id.artist);
        artist.setText(item.getArtistName());

        return view;
    }
}
