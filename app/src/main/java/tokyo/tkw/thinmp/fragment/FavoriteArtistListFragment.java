package tokyo.tkw.thinmp.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.activity.ArtistActivity;
import tokyo.tkw.thinmp.activity.FavoriteArtistEditActivity;
import tokyo.tkw.thinmp.adapter.FavoriteArtistListAdapter;
import tokyo.tkw.thinmp.favorite.FavoriteArtistList;

/**
 * FavoriteArtistListFragment
 */
public class FavoriteArtistListFragment extends Fragment {
    private FavoriteArtistListAdapter.FavoriteArtistListListener mOnClickListener = new FavoriteArtistListAdapter.FavoriteArtistListListener() {
        @Override
        public void onClick(String artistId) {
            Activity context = getActivity();
            Intent intent = new Intent(context, ArtistActivity.class);
            intent.putExtra("artist_id", artistId);
            context.startActivity(intent);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite_artist_list, container, false);

        Activity context = getActivity();

        RecyclerView favoriteListView = view.findViewById(R.id.favoriteArtistList);
        LinearLayoutManager layout = new LinearLayoutManager(context);
        favoriteListView.setLayoutManager(layout);

        FavoriteArtistListAdapter adapter = new FavoriteArtistListAdapter(FavoriteArtistList.getFavoriteList(), mOnClickListener);
        favoriteListView.setAdapter(adapter);

        view.findViewById(R.id.edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FavoriteArtistEditActivity.class);
                getActivity().startActivity(intent);
            }
        });

        // 区切り線の描画
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                context, new LinearLayoutManager(context).getOrientation());
        favoriteListView.addItemDecoration(dividerItemDecoration);

        return view;
    }
}
