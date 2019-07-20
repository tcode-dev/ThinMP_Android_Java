package tokyo.tkw.thinmp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Map;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;
import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.realm.FavoriteArtistRealm;
import tokyo.tkw.thinmp.artist.Artist;
import tokyo.tkw.thinmp.util.GlideUtil;
import tokyo.tkw.thinmp.viewHolder.ArtistViewHolder;

public class FavoriteArtistsAdapter extends RealmRecyclerViewAdapter<FavoriteArtistRealm,
        ArtistViewHolder> {
    private Map<String, Artist> mArtistMap;
    private FavoriteArtistListListener mListener;

    public FavoriteArtistsAdapter(OrderedRealmCollection<FavoriteArtistRealm> favoriteList,
                                  Map<String, Artist> artistMap,
                                  FavoriteArtistListListener listener) {
        super(favoriteList, true);

        mArtistMap = artistMap;
        mListener = listener;
    }

    @Override
    public ArtistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_linear_artist,
                parent, false);

        return new ArtistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ArtistViewHolder holder, int position) {
        FavoriteArtistRealm favorite = getItem(position);
        Artist artist = mArtistMap.get(favorite.getArtistId());

        GlideUtil.bitmap(artist.getAlbumArtId(), holder.albumArt, GlideUtil.ARTIST_RESOURCE_ID);
        holder.primaryText.setText(artist.getName());
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
