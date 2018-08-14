package tokyo.tkw.thinmp.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.util.ThumbnailController;
import tokyo.tkw.thinmp.model.Track;

/**
 * Created by tk on 2018/03/22.
 */

public class AlbumTrackListAdapter extends ArrayAdapter<Track> {
    private Activity mContext;
    private int mResource;
    private LayoutInflater mInflater;
    private ThumbnailController mThumbnailController;

    private ArrayList<Track> mTrackList;

    public AlbumTrackListAdapter(@NonNull Activity context, int resource, @NonNull ArrayList<Track> trackList) {
        super(context, resource, trackList);

        mContext = context;
        mResource = resource;
        mInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        mThumbnailController = new ThumbnailController(context);
        mTrackList = trackList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //view取得
        View view = getView(convertView);
        //曲取得
        Track track = getTrack(position);
        //曲名設定
        setTitle(view, track.getTitle());
        //ポップアップメニュー設定
        setPopupMenu(view, track.getId());

        return view;
    }

    /**
     * view取得
     * @param convertView
     * @return
     */
    private View getView(View convertView) {
        if (convertView != null) {
            return convertView;
        } else {
            return mInflater.inflate(mResource, null);
        }
    }

    /**
     * 曲取得
     * @param position
     * @return
     */
    private Track getTrack(int position) {
        return mTrackList.get(position);
    }

    /**
     * 曲名設定
     * @param view
     * @param title
     */
    private void setTitle(View view, String title) {
        TextView titleView = view.findViewById(R.id.title);
        titleView.setText(title);
    }

    /**
     * ポップアップメニュー設定
     * @param view
     * @param trackId
     */
    private void setPopupMenu(View view, String trackId) {
        View popupMenu = view.findViewById(R.id.popup_album_menu);
        popupMenu.setOnClickListener(new ViewOnClickListener(trackId));
    }

    /**
     * ポップアップメニュー
     * @param view
     * @param trackId
     */
    public void showPopupMenu(View view, String trackId) {
        PopupMenu popupMenu;
        popupMenu = new PopupMenu(mContext, view);
        popupMenu.getMenuInflater().inflate(R.menu.popup_album_track_list, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenuClickListener(trackId));
        popupMenu.show();
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
                case R.id.popup_album_track_list_to_playlist:
                case R.id.popup_album_track_list_to_favorite:
                    Toast.makeText(
                            mContext,
                            mTrackId + " の選択",
                            Toast.LENGTH_SHORT).show();
                    return true;
                default:
                    break;
            }

            return false;
        }
    }
}
