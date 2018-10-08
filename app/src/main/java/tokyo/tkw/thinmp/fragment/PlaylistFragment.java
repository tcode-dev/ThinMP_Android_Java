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

import io.realm.Realm;
import io.realm.RealmResults;
import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.adapter.PlaylistAdapter;
import tokyo.tkw.thinmp.playlist.Playlist;

/**
 * PlaylistFragment
 */
public class PlaylistFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_playlist, container, false);

        Activity context = getActivity();

        RecyclerView playlistView = view.findViewById(R.id.playlist);
        LinearLayoutManager layout = new LinearLayoutManager(context);
        playlistView.setLayoutManager(layout);

        Realm.init(context);
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Playlist> playlists = realm.where(Playlist.class).findAll().sort("order");
        PlaylistAdapter adapter = new PlaylistAdapter(context, playlists);
        playlistView.setAdapter(adapter);

        // 区切り線の描画
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                context, new LinearLayoutManager(context).getOrientation());
        playlistView.addItemDecoration(dividerItemDecoration);
        return view;
    }
}
