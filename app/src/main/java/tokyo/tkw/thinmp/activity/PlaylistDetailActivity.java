package tokyo.tkw.thinmp.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.adapter.PlaylistDetailAdapter;
import tokyo.tkw.thinmp.playlist.PlaylistTrack;
import tokyo.tkw.thinmp.realm.PlaylistTrackRealm;

public class PlaylistDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist_detail);

        int playlistId = getIntent().getIntExtra(PlaylistTrackRealm.PLAYLIST_ID, 0);
        RecyclerView view = findViewById(R.id.list);
        LinearLayoutManager layout = new LinearLayoutManager(this);
        view.setLayoutManager(layout);
        PlaylistTrack playlistTrack = new PlaylistTrack(this, playlistId);

        PlaylistDetailAdapter adapter = new PlaylistDetailAdapter(playlistTrack.getRealmResults()
                , playlistTrack.getTrackMap());
        view.setAdapter(adapter);
    }
}
