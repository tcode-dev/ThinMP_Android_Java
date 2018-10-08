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

import io.realm.Realm;
import io.realm.RealmResults;
import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.adapter.FavoriteListAdapter;
import tokyo.tkw.thinmp.favorite.Favorite;
import tokyo.tkw.thinmp.favorite.FavoriteList;
import tokyo.tkw.thinmp.music.Track;

/**
 * FavoriteListFragment
 */
public class FavoriteListFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_favorite_list, container, false);

        Activity context = getActivity();

        RecyclerView favoriteListView = view.findViewById(R.id.favoriteList);
        LinearLayoutManager layout = new LinearLayoutManager(context);
        favoriteListView.setLayoutManager(layout);

        Realm.init(context);
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Favorite> favoriteList = realm.where(Favorite.class).findAll().sort("createdAt");
        ArrayList<Track> trackList = FavoriteList.getTrackList(favoriteList);
        FavoriteListAdapter adapter = new FavoriteListAdapter(context, favoriteList, trackList);
        favoriteListView.setAdapter(adapter);

        // 区切り線の描画
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                context, new LinearLayoutManager(context).getOrientation());
        favoriteListView.addItemDecoration(dividerItemDecoration);
        return view;
    }
}
