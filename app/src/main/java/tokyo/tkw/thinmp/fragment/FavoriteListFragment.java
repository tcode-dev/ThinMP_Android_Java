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

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.adapter.FavoriteListAdapter;
import tokyo.tkw.thinmp.favorite.FavoriteList;
import tokyo.tkw.thinmp.music.Track;

/**
 * FavoriteListFragment
 */
public class FavoriteListFragment extends Fragment implements FavoriteListAdapter.OnFavoriteListItemClickListener {
    private OnFragmentInteractionListener mListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_favorite_list, container, false);

        Activity context = getActivity();

        RecyclerView favoriteListView = view.findViewById(R.id.favoriteList);
        LinearLayoutManager layout = new LinearLayoutManager(context);
        favoriteListView.setLayoutManager(layout);

        FavoriteListAdapter adapter = new FavoriteListAdapter(FavoriteList.getFavoriteList(context), (FavoriteListAdapter.OnFavoriteListItemClickListener) this);
        favoriteListView.setAdapter(adapter);

        // 区切り線の描画
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                context, new LinearLayoutManager(context).getOrientation());
        favoriteListView.addItemDecoration(dividerItemDecoration);

        return view;
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

    @Override
    public void onClickItem(int position) {
        if (mListener != null) {
            mListener.onStartClick(FavoriteList.getTrackList(getActivity()), position);
        }
    }

    /**
     * interface
     */
    public interface OnFragmentInteractionListener {
        void onStartClick(ArrayList<Track> trackList, int position);
    }
}
