package tokyo.tkw.thinmp.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.favorite.FavoriteManager;
import tokyo.tkw.thinmp.listener.ItemClickListener;
import tokyo.tkw.thinmp.util.ThumbnailController;
import tokyo.tkw.thinmp.model.Track;

/**
 * Created by tk on 2018/03/22.
 */

public class AlbumTrackListAdapter extends RecyclerView.Adapter<AlbumTrackListAdapter.AlbumTrackListViewHolder> {
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
        holder.mTitleView.setText(track.getTitle());
        holder.itemView.setOnClickListener(new ItemClickListener(mContext, mTrackList, position));
        holder.mPopupMenu.setOnClickListener(new ViewOnClickListener(track.getId()));
    }

    @Override
    public int getItemCount() {
        return mTrackList.size();
    }

    /**
     * ポップアップメニュー
     * @param view
     * @param trackId
     */
    public void showPopupMenu(View view, String trackId) {
        int hiddenFavorite = FavoriteManager.exists(trackId) ? R.id.add_favorite : R.id.del_favorite;
        PopupMenu popupMenu = new PopupMenu(mContext, view);
        popupMenu.getMenuInflater().inflate(R.menu.popup_album_track_list, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenuClickListener(trackId));
        popupMenu.getMenu().findItem(hiddenFavorite).setVisible(false);
        popupMenu.show();
    }

    public void setFavorite(String trackId) {
        FavoriteManager.set(trackId);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    /**
     *
     */
    private class ViewOnClickListener implements View.OnClickListener {
        String mTrackId;

        private ViewOnClickListener(String trackId) {
            mTrackId = trackId;
        }

        @Override
        public void onClick(View view) {
            showPopupMenu(view, mTrackId);
        }
    }

    /**
     *
     */
    private class PopupMenuClickListener implements PopupMenu.OnMenuItemClickListener {
        String mTrackId;

        private PopupMenuClickListener(String trackId) {
            mTrackId = trackId;
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.playlist:
                    Toast.makeText(
                            mContext,
                            mTrackId + " の選択",
                            Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.add_favorite:
                    setFavorite(mTrackId);

                    return true;
                case R.id.del_favorite:
                    setFavorite(mTrackId);

                    return true;
                    default:
                    break;
            }

            return false;
        }
    }


    public class AlbumTrackListViewHolder extends RecyclerView.ViewHolder {
        public TextView mTitleView;
        public View mPopupMenu;


        public AlbumTrackListViewHolder(View view) {
            super(view);

            mTitleView = view.findViewById(R.id.title);
            mPopupMenu = view.findViewById(R.id.popup_album_menu);
        }
    }
}
