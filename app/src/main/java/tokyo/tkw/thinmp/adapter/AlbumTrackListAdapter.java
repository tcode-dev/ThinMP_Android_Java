package tokyo.tkw.thinmp.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.favorite.FavoriteRegister;
import tokyo.tkw.thinmp.listener.TrackClickListener;
import tokyo.tkw.thinmp.music.Track;
import tokyo.tkw.thinmp.viewHolder.AlbumTrackListViewHolder;

/**
 * Created by tk on 2018/03/22.
 */

public class AlbumTrackListAdapter extends RecyclerView.Adapter<AlbumTrackListViewHolder> {
    private Activity mContext;
    private ArrayList<Track> mTrackList;

    public AlbumTrackListAdapter(@NonNull Activity context, @NonNull ArrayList<Track> trackList) {
        mContext = context;
        mTrackList = trackList;
    }

    @Override
    public AlbumTrackListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.album_track_list_item, parent, false);

        return new AlbumTrackListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AlbumTrackListViewHolder holder, int position) {
        Track track = mTrackList.get(position);
        String title = track.getTitle();

        holder.title.setText(title);

//        holder.itemView.setOnClickListener(new TrackClickListener(mContext, mTrackList, position));
//        holder.menu.setOnClickListener(new OpenTrackMenuClickListener(mContext, title, track));
    }

    @Override
    public int getItemCount() {
        return mTrackList.size();
    }

    public void setFavorite(String trackId) {
        FavoriteRegister.set(trackId);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }
}
