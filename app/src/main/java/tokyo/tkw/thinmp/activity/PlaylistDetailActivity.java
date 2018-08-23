package tokyo.tkw.thinmp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.adapter.FavoriteListAdapter;
import tokyo.tkw.thinmp.adapter.PlaylistDetailAdapter;
import tokyo.tkw.thinmp.favorite.Favorite;
import tokyo.tkw.thinmp.favorite.FavoriteList;
import tokyo.tkw.thinmp.music.MusicList;
import tokyo.tkw.thinmp.music.Track;
import tokyo.tkw.thinmp.playlist.PlaylistTrack;

public class PlaylistDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist_detail);

        int playlistId = getIntent().getIntExtra("playlistId", 0);
        RecyclerView view = findViewById(R.id.playlist);
        LinearLayoutManager layout = new LinearLayoutManager(this);
        view.setLayoutManager(layout);

        Realm.init(this);
        Realm realm = Realm.getDefaultInstance();
        RealmResults<PlaylistTrack> playlistTracks = realm.where(PlaylistTrack.class).equalTo("playlistId", playlistId).findAll();
        ArrayList<Track> trackList = getTrackList(playlistTracks);
        PlaylistDetailAdapter adapter = new PlaylistDetailAdapter(this, playlistTracks, trackList);
        view.setAdapter(adapter);

        // 区切り線の描画
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                this, new LinearLayoutManager(this).getOrientation());
        view.addItemDecoration(dividerItemDecoration);
    }

    private ArrayList<Track> getTrackList(RealmResults<PlaylistTrack> playlistTracks) {
        ArrayList<Track> trackList = new ArrayList<Track>();

        for (PlaylistTrack playlistTrack: playlistTracks) {
            Track track = MusicList.getTrack(playlistTrack.getTrackId());
            trackList.add(track);
        }

        return trackList;
    }
}
