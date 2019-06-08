package tokyo.tkw.thinmp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Map;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;
import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.favorite.FavoriteArtist;
import tokyo.tkw.thinmp.music.Artist;
import tokyo.tkw.thinmp.util.GlideUtil;
import tokyo.tkw.thinmp.viewHolder.ArtistViewHolder;

public class FavoriteArtistListAdapter extends RealmRecyclerViewAdapter<FavoriteArtist,
        ArtistViewHolder> {
    private Map<String, Artist> mArtistMap;
    private FavoriteArtistListListener mListener;

    public FavoriteArtistListAdapter(OrderedRealmCollection<FavoriteArtist> favoriteList,
                                     Map<String, Artist> artistMap,
                                     FavoriteArtistListListener listener) {
        super(favoriteList, true);

        mArtistMap = artistMap;
        mListener = listener;
    }

    @Override
    public ArtistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.artist_list_item,
                parent, false);

        return new ArtistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ArtistViewHolder holder, int position) {
        FavoriteArtist favorite = getItem(position);
        Artist artist = mArtistMap.get(favorite.getArtistId());

        GlideUtil.bitmap(artist.getThumbnailIdList(), holder.thumbnail);
        holder.artistName.setText(artist.getName());

        holder.itemView.setOnClickListener(onClickListener(artist.getId()));
    }

    private View.OnClickListener onClickListener(String artistId) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClick(artistId);
            }
        };
    }

    /**
     * interface
     */
    public interface FavoriteArtistListListener {
        void onClick(String artistId);
    }
}
