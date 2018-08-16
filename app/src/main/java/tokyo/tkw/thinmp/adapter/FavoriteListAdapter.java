package tokyo.tkw.thinmp.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import io.realm.OrderedCollectionChangeSet;
import io.realm.OrderedRealmCollection;
import io.realm.OrderedRealmCollectionChangeListener;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmRecyclerViewAdapter;
import io.realm.RealmResults;
import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.favorite.Favorite;
import tokyo.tkw.thinmp.listener.OpenTrackMenuClickListener;
import tokyo.tkw.thinmp.listener.TrackClickListener;
import tokyo.tkw.thinmp.model.Track;
import tokyo.tkw.thinmp.music.MusicList;
import tokyo.tkw.thinmp.util.ThumbnailController;
import tokyo.tkw.thinmp.viewHolder.TrackViewHolder;

public class FavoriteListAdapter extends RealmRecyclerViewAdapter<Favorite, TrackViewHolder> {
    private Activity mContext;
    private ThumbnailController mThumbnailController;
    private ArrayList<Track> mTrackList;

    public FavoriteListAdapter(Activity context, OrderedRealmCollection<Favorite> favoriteList, ArrayList<Track> trackList) {
        super(favoriteList, true);

        mContext = context;
        mTrackList = trackList;
        mThumbnailController = new ThumbnailController(context);
    }

    @Override
    public TrackViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.track_row, parent, false);

        return new TrackViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrackViewHolder holder, int position) {
        final Favorite favorite = getItem(position);
        final Track track = getTrack(favorite.getTrackId());

        holder.thumbnail.setImageBitmap(getThumbnail(track.getThumbnailId()));
        holder.track.setText(track.getTitle());
        holder.artist.setText(track.getArtistName());

        holder.itemView.setOnClickListener(new TrackClickListener(mContext, mTrackList, position));
        holder.menu.setOnClickListener(new OpenTrackMenuClickListener(mContext, track.getId()));
    }

    private Track getTrack(String id) {
        return MusicList.getTrack(id);
    }

    private Bitmap getThumbnail(String id) {
        return mThumbnailController.getThumbnail(id);
    }
}
