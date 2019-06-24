package tokyo.tkw.thinmp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
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
        initEdit();
    }

    private void initList() {
        RecyclerView playlistView = findViewById(R.id.list);
        LinearLayoutManager layout = new LinearLayoutManager(this);
        playlistView.setLayoutManager(layout);

        Playlist playlist = new Playlist();
        PlaylistsAdapter adapter = new PlaylistsAdapter(playlist.getRealmResults());
        playlistView.setAdapter(adapter);
    }

    private void initEdit() {
        findViewById(R.id.edit).setOnClickListener(editClickListener(this));
    }

    private View.OnClickListener editClickListener(Context context) {
        return v -> {
            Intent intent = new Intent(context, PlaylistsEditActivity.class);
            context.startActivity(intent);
        };
    }
}
