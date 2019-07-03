package tokyo.tkw.thinmp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.adapter.PlaylistsAdapter;
import tokyo.tkw.thinmp.playlist.Playlist;

public class PlaylistsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_playlists);

        initWithPermissionCheck();
    }

    @Override
    protected void init() {
        RecyclerView playlistView = findViewById(R.id.list);

        Playlist playlist = new Playlist(this);
        PlaylistsAdapter adapter = new PlaylistsAdapter(playlist.getRealmResults(), playlist.getAlbumArtMap());
        LinearLayoutManager layout = new LinearLayoutManager(this);

        playlistView.setLayoutManager(layout);
        playlistView.setAdapter(adapter);

        findViewById(R.id.edit).setOnClickListener(createEditClickListener(this));
    }

    private View.OnClickListener createEditClickListener(Context context) {
        return v -> {
            Intent intent = new Intent(context, PlaylistsEditActivity.class);
            context.startActivity(intent);
        };
    }
}
