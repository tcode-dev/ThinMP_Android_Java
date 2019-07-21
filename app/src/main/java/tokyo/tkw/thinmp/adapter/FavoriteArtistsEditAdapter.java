package tokyo.tkw.thinmp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.artist.Artist;
import tokyo.tkw.thinmp.util.GlideUtil;
import tokyo.tkw.thinmp.viewHolder.ArtistViewHolder;

public class FavoriteArtistsEditAdapter extends RecyclerView.Adapter<ArtistViewHolder> {
    private List<Artist> artistList;

    public FavoriteArtistsEditAdapter(List<Artist> artistList) {
        this.artistList = artistList;
    }

    @Override
    public ArtistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_linear_artist,
                parent, false);

        return new ArtistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ArtistViewHolder holder, int position) {
        Artist artist = artistList.get(position);

        GlideUtil.bitmap(artist.getAlbumArtId(), holder.albumArt, GlideUtil.ARTIST_RESOURCE_ID);
        holder.primaryText.setText(artist.getName());
    }

    @Override
    public int getItemCount() {
        return artistList.size();
    }
}
