package tokyo.tkw.thinmp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;
import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.favorite.FavoriteSong;
import tokyo.tkw.thinmp.music.MusicList;
import tokyo.tkw.thinmp.music.Track;
import tokyo.tkw.thinmp.provider.ThumbnailProvider;
import tokyo.tkw.thinmp.viewHolder.TrackViewHolder;

public class FavoriteSongListAdapter extends RealmRecyclerViewAdapter<FavoriteSong, TrackViewHolder> {
    private OnFavoriteListItemClickListener mListener;
    private ThumbnailProvider mThumbnailProvider;

    public FavoriteSongListAdapter(OrderedRealmCollection<FavoriteSong> favoriteList, OnFavoriteListItemClickListener listener) {
        super(favoriteList, true);

        mListener = listener;
        mThumbnailProvider = new ThumbnailProvider();
    }

    @Override
    public TrackViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.track_row, parent, false);

        return new TrackViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrackViewHolder holder, int position) {
        final FavoriteSong favorite = getItem(position);
        final String trackId = favorite.getTrackId();
        final Track track = getTrack(trackId);
        final String title = track.getTitle();

        holder.thumbnail.setImageBitmap(mThumbnailProvider.getThumbnail(track.getThumbnailId()));
        holder.track.setText(title);
        holder.artist.setText(track.getArtistName());

        holder.itemView.setOnClickListener(onClickListener(position));
        holder.menu.setOnClickListener(openMenuButtonClickListener(track));
    }

    private Track getTrack(String id) {
        return MusicList.getTrack(id);
    }

    private View.OnClickListener onClickListener(int position) {
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
    public interface OnFavoriteListItemClickListener {
        void onClickItem(int position);

        void onClickMenu(View view, Track track);
    }
}
