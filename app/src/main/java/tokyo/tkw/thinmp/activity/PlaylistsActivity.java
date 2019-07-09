package tokyo.tkw.thinmp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import io.realm.RealmResults;
import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.adapter.PlaylistsAdapter;
import tokyo.tkw.thinmp.playlist.PlaylistCollection;
import tokyo.tkw.thinmp.realm.PlaylistRealm;

public class PlaylistsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_playlists);

        initWithPermissionCheck();
    }

    @Override
    protected void init() {
        RecyclerView listView = findViewById(R.id.list);
        Button editView = findViewById(R.id.edit);

        PlaylistCollection playlistCollection = PlaylistCollection.createInstance(this);
        RealmResults<PlaylistRealm> realmResults = playlistCollection.findAll();
        PlaylistsAdapter adapter = new PlaylistsAdapter(realmResults,
                playlistCollection.getAlbumArtMap(realmResults));
        GridLayoutManager layout = new GridLayoutManager(this, 2);

        listView.setLayoutManager(layout);
        listView.setAdapter(adapter);

        // event
        editView.setOnClickListener(createEditClickListener());
    }

    private View.OnClickListener createEditClickListener() {
        return view -> {
            Context context = view.getContext();
            Intent intent = new Intent(context, PlaylistsEditActivity.class);
            context.startActivity(intent);
        };
    }
}
