package tokyo.tkw.thinmp.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.adapter.TopAdapter;
import tokyo.tkw.thinmp.music.Album;
import tokyo.tkw.thinmp.music.MusicList;

/**
 * TopFragment
 */
public class TopFragment extends Fragment {
    private View mFragmentView;
    private RecyclerView mListView;

    private ArrayList<Album> mAlbumList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mFragmentView = inflater.inflate(R.layout.fragment_top, container, false);

        setView();
        setAlbumList();
        setAdapter();

        return mFragmentView;
    }

    private void setView() {
        mListView = mFragmentView.findViewById(R.id.list);
    }

    private void setAlbumList() {
        mAlbumList = MusicList.getAlbumList();
    }

    private void setAdapter() {
        Activity context = getActivity();
        TopAdapter adapter = new TopAdapter(context, mAlbumList);
        LinearLayoutManager layout = new GridLayoutManager(context, 2);
        mListView.setLayoutManager(layout);
        mListView.setAdapter(adapter);
    }
}
