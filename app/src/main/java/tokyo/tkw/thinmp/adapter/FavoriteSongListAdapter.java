package tokyo.tkw.thinmp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Map;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;
import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.realm.FavoriteSongRealm;
import tokyo.tkw.thinmp.music.Track;
import tokyo.tkw.thinmp.util.GlideUtil;
import tokyo.tkw.thinmp.viewHolder.TrackViewHolder;

public class FavoriteSongListAdapter extends RealmRecyclerViewAdapter<FavoriteSongRealm,
        TrackViewHolder> {
    private Map<String, Track> mTrackMap;
    private OnFavoriteListItemClickListener mListener;

    public FavoriteSongListAdapter(OrderedRealmCollection<FavoriteSongRealm> favoriteList,
                                   Map<String, Track> trackMap,
                                   OnFavoriteListItemClickListener listener) {
        super(favoriteList, true);

        mTrackMap = trackMap;
        mListener = listener;
    }

    @Override
    public TrackViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.track_row, parent,
                false);

        return new TrackViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrackViewHolder holder, int position) {
        FavoriteSongRealm favorite = getItem(position);
        Track track = mTrackMap.get(favorite.getTrackId());
        String title = track.getTitle();

        GlideUtil.bitmap(track.getAlbumArtId(), holder.albumArt);
        holder.track.setText(title);
        holder.artist.setText(track.getArtistName());

        holder.itemView.setOnClickListener(onClickListener(position));
        holder.menu.setOnClickListener(openMenuButtonClickListener(track));
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
