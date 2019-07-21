package tokyo.tkw.thinmp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.Map;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;
import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.listener.ITrackClickListener;
import tokyo.tkw.thinmp.track.Track;
import tokyo.tkw.thinmp.realm.FavoriteSongRealm;
import tokyo.tkw.thinmp.util.GlideUtil;
import tokyo.tkw.thinmp.viewHolder.TrackViewHolder;

public class FavoriteSongsAdapter extends RealmRecyclerViewAdapter<FavoriteSongRealm,
        TrackViewHolder> {
    private Map<String, Track> mTrackMap;
    private ITrackClickListener mListener;
    private RecyclerView mRecycler;

    public FavoriteSongsAdapter(OrderedRealmCollection<FavoriteSongRealm> favoriteList,
                                Map<String, Track> trackMap,
                                ITrackClickListener listener) {
        super(favoriteList, true);

        mTrackMap = trackMap;
        mListener = listener;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        mRecycler = recyclerView;
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);

        mRecycler = null;
    }

    @Override
    public TrackViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_linear_track, parent,
                false);

        return new TrackViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrackViewHolder holder, int position) {
        FavoriteSongRealm favorite = getItem(position);
        Track track = mTrackMap.get(favorite.getTrackId());

        GlideUtil.bitmap(track.getAlbumArtId(), holder.albumArt);
        holder.primaryText.setText(track.getName());
        holder.secondaryText.setText(track.getArtistName());
        holder.itemView.setOnClickListener(onClickTrack());
        holder.menu.setOnClickListener(onClickMenu());
    }

    private View.OnClickListener onClickTrack() {
        return view -> {
            mListener.onClickTrack(view, mRecycler.getChildAdapterPosition(view));
        };
    }

    private View.OnClickListener onClickMenu() {
        return view -> {
            mListener.onClickMenu(view, mRecycler.getChildAdapterPosition((View) view.getParent()));
        };
    }
}
