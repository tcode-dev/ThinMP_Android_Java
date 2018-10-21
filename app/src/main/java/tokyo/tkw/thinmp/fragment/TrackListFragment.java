package tokyo.tkw.thinmp.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import tokyo.tkw.thinmp.music.MusicList;
import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.music.Track;
import tokyo.tkw.thinmp.adapter.TrackListAdapter;

/**
 * TrackListFragment
 */
public class TrackListFragment extends Fragment implements TrackListAdapter.OnTrackListItemClickListener {
    private View mFragmentView;
    private ArrayList<Track> mTrackList;
    private RecyclerView mListView;

    private OnFragmentInteractionListener mListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mFragmentView = inflater.inflate(R.layout.fragment_track_list, container, false);

        setView();
        setTrackList();
        setAdapter();

        return mFragmentView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void setView() {
        mListView = mFragmentView.findViewById(R.id.list);
    }

    private void setTrackList() {
        mTrackList = MusicList.getTrackList();
    }

    private void setAdapter() {
        Activity context = getActivity();
        TrackListAdapter adapter = new TrackListAdapter(context, mTrackList, (TrackListAdapter.OnTrackListItemClickListener) this);
        LinearLayoutManager layout = new LinearLayoutManager(context);
        mListView.setLayoutManager(layout);
        mListView.setAdapter(adapter);

        // 区切り線の描画
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                context, new LinearLayoutManager(context).getOrientation());
        mListView.addItemDecoration(dividerItemDecoration);
    }

    @Override
    public void onClickItem(int position) {
        if (mListener != null) {
            mListener.onStartClick(MusicList.getTrackList(), position);
        }
    }

    /**
     * interface
     */
    public interface OnFragmentInteractionListener {
        void onStartClick(ArrayList<Track> trackList, int position);
    }
}
