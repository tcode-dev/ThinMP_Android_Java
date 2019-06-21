package tokyo.tkw.thinmp.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.adapter.PlaylistsAdapter;
import tokyo.tkw.thinmp.playlist.Playlist;

public class PlaylistsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlists);

        initList();
    }

    private void initList() {
        RecyclerView playlistView = findViewById(R.id.list);
        LinearLayoutManager layout = new LinearLayoutManager(this);
        playlistView.setLayoutManager(layout);

        Playlist playlist = new Playlist();
        PlaylistsAdapter adapter = new PlaylistsAdapter(playlist.getRealmResults());
        playlistView.setAdapter(adapter);
    }
}
