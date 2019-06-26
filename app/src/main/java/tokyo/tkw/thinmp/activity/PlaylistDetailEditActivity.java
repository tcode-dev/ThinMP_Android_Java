package tokyo.tkw.thinmp.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.adapter.PlaylistDetailEditAdapter;
import tokyo.tkw.thinmp.playlist.PlaylistTrack;
import tokyo.tkw.thinmp.realm.PlaylistTrackRealm;

public class PlaylistDetailEditActivity extends AppCompatActivity {
    public ArrayList<PlaylistTrackRealm> mList;
    public PlaylistDetailEditAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist_detail_edit);

        int playlistId = getIntent().getIntExtra(PlaylistTrackRealm.PLAYLIST_ID, 0);

        RecyclerView view = findViewById(R.id.list);
        LinearLayoutManager layout = new LinearLayoutManager(this);
        view.setLayoutManager(layout);

        PlaylistTrack playlistTrack = new PlaylistTrack(this, playlistId);

        mList = playlistTrack.getList();

        mAdapter = new PlaylistDetailEditAdapter(mList, playlistTrack.getTrackMap());
        view.setAdapter(mAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(onItemTouchHelperSimpleCallback());
        itemTouchHelper.attachToRecyclerView(view);

        findViewById(R.id.apply).setOnClickListener(v -> {
//            List<String> artistIdList =
//                    Stream.of(mList).map(PlaylistRealm::getId).collect(Collectors
//                    .toList());
//            PlaylistRegister.update(artistIdList);
            finish();
        });

        findViewById(R.id.cancel).setOnClickListener(v -> finish());
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
                mList.add(toPos, mList.remove(fromPos));

                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                final int fromPos = viewHolder.getAdapterPosition();

                mList.remove(fromPos);
                mAdapter.notifyItemRemoved(fromPos);
            }
        };
    }
}
