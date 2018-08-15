package tokyo.tkw.thinmp.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;
import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.favorite.Favorite;
import tokyo.tkw.thinmp.listener.ItemClickListener;
import tokyo.tkw.thinmp.model.Track;
import tokyo.tkw.thinmp.music.MusicList;
import tokyo.tkw.thinmp.util.ThumbnailController;

public class FavoriteListAdapter extends RealmRecyclerViewAdapter<Favorite, FavoriteListAdapter.FavoriteViewHolder> {
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
    public FavoriteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_row, parent, false);

        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FavoriteViewHolder holder, int position) {
        final Favorite favorite = getItem(position);
        final Track track = getTrack(favorite.getTrack_id());

        holder.thumbnail.setImageBitmap(getThumbnail(track.getThumbnailId()));
        holder.track.setText(track.getTitle());
        holder.artist.setText(track.getArtistName());

        holder.itemView.setOnClickListener(new tokyo.tkw.thinmp.listener.ItemClickListener(mContext, mTrackList, position));
    }

    private Track getTrack(String id) {
        return MusicList.getTrack(id);
    }

    private Bitmap getThumbnail(String id) {
        return mThumbnailController.getThumbnail(id);
    }

    public class FavoriteViewHolder extends RecyclerView.ViewHolder {
        public ImageView thumbnail;
        public TextView track;
        public TextView artist;

        public FavoriteViewHolder(View itemView) {
            super(itemView);

            thumbnail = itemView.findViewById(R.id.thumbnail);
            track = itemView.findViewById(R.id.track);
            artist = itemView.findViewById(R.id.artist);
        }
    }
}

