package tokyo.tkw.thinmp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.adapter.PlaylistDetailAdapter;
import tokyo.tkw.thinmp.listener.PlaylistTrackClickListener;
import tokyo.tkw.thinmp.playlist.Playlist;
import tokyo.tkw.thinmp.playlist.PlaylistTrack;
import tokyo.tkw.thinmp.realm.PlaylistRealm;
import tokyo.tkw.thinmp.realm.PlaylistTrackRealm;

public class PlaylistDetailActivity extends BaseActivity {
    private int mPlaylistId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist_detail);

        mPlaylistId = getIntent().getIntExtra(PlaylistTrackRealm.PLAYLIST_ID, 0);

        RecyclerView view = findViewById(R.id.list);

        PlaylistRealm playlistRealm = PlaylistRealm.createInstance(mPlaylistId);
        PlaylistTrack playlistTrack = new PlaylistTrack(this, mPlaylistId);

        PlaylistDetailAdapter adapter = new PlaylistDetailAdapter(playlistRealm.getTracks(),
                playlistTrack.getTrackMap(), new PlaylistTrackClickListener(mPlaylistId));
        view.setAdapter(adapter);

        findViewById(R.id.edit).setOnClickListener(createEditClickListener());

        LinearLayoutManager layout = new LinearLayoutManager(this);
        view.setLayoutManager(layout);
    }

    private View.OnClickListener createEditClickListener() {
        return view -> {
            Context context = view.getContext();
            Intent intent = new Intent(context, PlaylistDetailEditActivity.class);
            intent.putExtra(Playlist.PLAYLIST_ID, mPlaylistId);
            context.startActivity(intent);
        };
    }
}
