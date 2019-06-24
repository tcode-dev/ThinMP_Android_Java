package tokyo.tkw.thinmp.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.adapter.PlaylistsEditAdapter;
import tokyo.tkw.thinmp.playlist.Playlist;
import tokyo.tkw.thinmp.realm.PlaylistRealm;

public class PlaylistsEditActivity extends AppCompatActivity {
    private ArrayList<PlaylistRealm> mPlaylistList;
    private PlaylistsEditAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_artist_edit);

        RecyclerView view = findViewById(R.id.list);

        Playlist playlist = new Playlist();
        mPlaylistList = playlist.getList();
        mAdapter = new PlaylistsEditAdapter(mPlaylistList);
        view.setAdapter(mAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(onItemTouchHelperSimpleCallback());
        itemTouchHelper.attachToRecyclerView(view);

        findViewById(R.id.apply).setOnClickListener(v -> {
//            List<String> artistIdList =
//                    Stream.of(mPlaylistList).map(PlaylistRealm::getId).collect(Collectors
//                    .toList());
//            PlaylistRegister.update(artistIdList);
            finish();
        });

        findViewById(R.id.cancel).setOnClickListener(v -> finish());

        LinearLayoutManager layout = new LinearLayoutManager(this);
        view.setLayoutManager(layout);
        view.setAdapter(mAdapter);
    }

    private ItemTouchHelper.SimpleCallback onItemTouchHelperSimpleCallback() {
        return new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                ItemTouchHelper.LEFT
        ) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {
                final int fromPos = viewHolder.getAdapterPosition();
                final int toPos = target.getAdapterPosition();

                // viewの並び替え
                mAdapter.notifyItemMoved(fromPos, toPos);
                // dataの並び替え
                mPlaylistList.add(toPos, mPlaylistList.remove(fromPos));

                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                final int fromPos = viewHolder.getAdapterPosition();

                mPlaylistList.remove(fromPos);
                mAdapter.notifyItemRemoved(fromPos);
            }
        };
    }
}
