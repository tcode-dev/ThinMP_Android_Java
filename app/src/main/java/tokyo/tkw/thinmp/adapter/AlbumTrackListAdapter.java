package tokyo.tkw.thinmp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.favorite.FavoriteSongRegister;
import tokyo.tkw.thinmp.music.Track;
import tokyo.tkw.thinmp.viewHolder.AlbumTrackListViewHolder;

public class AlbumTrackListAdapter extends RecyclerView.Adapter<AlbumTrackListViewHolder> {
    private ArrayList<Track> mTrackList;
    private OnTrackListItemClickListener mListener;

    public AlbumTrackListAdapter(@NonNull ArrayList<Track> trackList, OnTrackListItemClickListener listener) {
        mTrackList = trackList;
        mListener = listener;
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
        holder.itemView.setOnClickListener(listItemClickListener(position));
        holder.menu.setOnClickListener(openMenuButtonClickListener(track));
    }

    @Override
    public int getItemCount() {
        return mTrackList.size();
    }

    public void setFavorite(String trackId) {
        FavoriteSongRegister.set(trackId);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    /**
     * listitemをクリックしたときのイベント
     *
     * @param position
     * @return
     */
    private View.OnClickListener listItemClickListener(int position) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClickItem(position);
            }
        };
    }

    /**
     * メニューオープンのイベント
     *
     * @return
     */
    public View.OnClickListener openMenuButtonClickListener(Track track) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClickMenu(view, track);
            }
        };
    }

    /**
     * interface
     */
    public interface OnTrackListItemClickListener {
        void onClickItem(int position);

        void onClickMenu(View view, Track track);
    }
}
