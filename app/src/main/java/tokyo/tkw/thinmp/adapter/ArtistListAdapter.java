package tokyo.tkw.thinmp.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.listener.ArtistClickListener;
import tokyo.tkw.thinmp.music.Artist;
import tokyo.tkw.thinmp.viewHolder.ArtistViewHolder;

public class ArtistListAdapter extends RecyclerView.Adapter<ArtistViewHolder> {
    private Activity mContext;
    private ArrayList<Artist> mArtistList;

    public ArtistListAdapter(@NonNull Activity context, @NonNull ArrayList<Artist> artistList) {
        mContext = context;
        mArtistList = artistList;
    }

    @Override
    public ArtistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.artist_list_item, parent, false);

        return new ArtistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ArtistViewHolder holder, int position) {
        Artist artist = mArtistList.get(position);

        holder.thumbnail.setImageBitmap(artist.getThumbnail());
        holder.artist.setText(artist.getName());

        holder.itemView.setOnClickListener(new ArtistClickListener(mContext, artist.getId()));
    }

    @Override
    public int getItemCount() {
        return mArtistList.size();
    }
}
