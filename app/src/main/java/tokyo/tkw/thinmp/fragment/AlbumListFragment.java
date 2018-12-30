package tokyo.tkw.thinmp.fragment;

import android.app.Activity;
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
import tokyo.tkw.thinmp.adapter.AlbumListAdapter;
import tokyo.tkw.thinmp.music.Album;
import tokyo.tkw.thinmp.music.MusicList;

/**
 * AlbumListFragment
 */
public class AlbumListFragment extends Fragment {
    private View mFragmentView;
    private RecyclerView mListView;

    private ArrayList<Album> mAlbumList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mFragmentView = inflater.inflate(R.layout.fragment_album_list, container, false);

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
        AlbumListAdapter adapter = new AlbumListAdapter(context, mAlbumList);
        LinearLayoutManager layout = new LinearLayoutManager(context);
        mListView.setLayoutManager(layout);
        mListView.setAdapter(adapter);

        // 区切り線の描画
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                context, new LinearLayoutManager(context).getOrientation());
        mListView.addItemDecoration(dividerItemDecoration);
    }
}
