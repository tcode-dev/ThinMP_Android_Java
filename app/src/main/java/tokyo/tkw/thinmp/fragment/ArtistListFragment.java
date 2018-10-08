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

import tokyo.tkw.thinmp.music.Artist;
import tokyo.tkw.thinmp.adapter.ArtistListAdapter;
import tokyo.tkw.thinmp.music.MusicList;
import tokyo.tkw.thinmp.R;

/**
 * ArtistListFragment
 */
public class ArtistListFragment extends Fragment {
    private View mArtistListView;
    private RecyclerView mListView;

    private ArrayList<Artist> mArtistList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mArtistListView = inflater.inflate(R.layout.fragment_artist_list, container, false);

        setArtistList();
        setView();
        setAdapter();

        return mArtistListView;
    }

    private void setArtistList() {
        mArtistList = MusicList.getArtistList();
    }

    private void setView() {
        mListView = mArtistListView.findViewById(R.id.list);
    }

    private void setAdapter() {
        Activity context = getActivity();
        LinearLayoutManager layout = new LinearLayoutManager(context);
        ArtistListAdapter adapter = new ArtistListAdapter(context, mArtistList);
        mListView.setLayoutManager(layout);
        mListView.setAdapter(adapter);

        // 区切り線の描画
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                context, new LinearLayoutManager(context).getOrientation());
        mListView.addItemDecoration(dividerItemDecoration);
    }
}
