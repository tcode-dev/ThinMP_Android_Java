package tokyo.tkw.thinmp.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import tokyo.tkw.thinmp.activity.FavoriteSongEditActivity;
import tokyo.tkw.thinmp.adapter.FavoriteSongListAdapter;
import tokyo.tkw.thinmp.favorite.FavoriteSongList;
import tokyo.tkw.thinmp.music.Track;

/**
 * FavoriteListFragment
 */
public class FavoriteSongListFragment extends Fragment implements FavoriteSongListAdapter.OnFavoriteListItemClickListener {
    private OnFragmentInteractionListener mListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite_song_list, container, false);

        Activity context = getActivity();

        RecyclerView favoriteListView = view.findViewById(R.id.favoriteList);
        LinearLayoutManager layout = new LinearLayoutManager(context);
        favoriteListView.setLayoutManager(layout);

        FavoriteSongListAdapter adapter = new FavoriteSongListAdapter(FavoriteSongList.getFavoriteList(), (FavoriteSongListAdapter.OnFavoriteListItemClickListener) this);
        favoriteListView.setAdapter(adapter);

        view.findViewById(R.id.edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FavoriteSongEditActivity.class);
                getActivity().startActivity(intent);
            }
        });

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
            mListener.onStartClick(FavoriteSongList.getTrackList(), position);
        }
    }

    @Override
    public void onClickMenu(View view, Track track) {

    }

    /**
     * interface
     */
    public interface OnFragmentInteractionListener {
        void onStartClick(ArrayList<Track> trackList, int position);
    }
}
