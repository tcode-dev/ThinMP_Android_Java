package tokyo.tkw.thinmp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Map;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;
import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.realm.FavoriteArtistRealm;
import tokyo.tkw.thinmp.music.Artist;
import tokyo.tkw.thinmp.util.GlideUtil;
import tokyo.tkw.thinmp.viewHolder.ArtistViewHolder;

public class FavoriteArtistListAdapter extends RealmRecyclerViewAdapter<FavoriteArtistRealm,
        ArtistViewHolder> {
    private Map<String, Artist> mArtistMap;
    private Map<String, String> mArtistAlbumArtMap;
    private FavoriteArtistListListener mListener;

    public FavoriteArtistListAdapter(OrderedRealmCollection<FavoriteArtistRealm> favoriteList,
                                     Map<String, Artist> artistMap,
                                     Map<String, String> artistAlbumArtMap,
                                     FavoriteArtistListListener listener) {
        super(favoriteList, true);

        mArtistMap = artistMap;
        mArtistAlbumArtMap = artistAlbumArtMap;
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
        FavoriteArtistRealm favorite = getItem(position);
        Artist artist = mArtistMap.get(favorite.getArtistId());

        GlideUtil.bitmap(mArtistAlbumArtMap.get(artist.getId()), holder.albumArt, GlideUtil.ARTIST_RESOURCE_ID);
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
