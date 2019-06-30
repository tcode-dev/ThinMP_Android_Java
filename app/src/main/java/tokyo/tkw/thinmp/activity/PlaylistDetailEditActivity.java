package tokyo.tkw.thinmp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import io.realm.RealmList;
import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.adapter.PlaylistDetailEditAdapter;
import tokyo.tkw.thinmp.playlist.PlaylistRegister;
import tokyo.tkw.thinmp.playlist.PlaylistTrack;
import tokyo.tkw.thinmp.realm.PlaylistRealm;
import tokyo.tkw.thinmp.realm.PlaylistTrackRealm;

public class PlaylistDetailEditActivity extends BaseActivity {
    public RealmList<PlaylistTrackRealm> mList;
    public PlaylistDetailEditAdapter mAdapter;
    private PlaylistRegister mPlaylistRegister;
    private EditText mPlaylistName;
    private PlaylistRealm mPlaylistRealm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist_detail_edit);

        RecyclerView view = findViewById(R.id.list);

        int playlistId = getIntent().getIntExtra(PlaylistTrackRealm.PLAYLIST_ID, 0);

        mPlaylistName = findViewById(R.id.playlistName);

        mPlaylistRegister = new PlaylistRegister();
        mPlaylistRealm = PlaylistRealm.createInstance(playlistId);
        PlaylistTrack playlistTrack = new PlaylistTrack(this, playlistId);

        mPlaylistName.setText(mPlaylistRealm.getName());
        mList = mPlaylistRealm.getTracks();

        mAdapter = new PlaylistDetailEditAdapter(mList, playlistTrack.getTrackMap());

        ItemTouchHelper itemTouchHelper = createItemTouchHelper();
        itemTouchHelper.attachToRecyclerView(view);

        findViewById(R.id.apply).setOnClickListener(createApplyClickListener());
        findViewById(R.id.cancel).setOnClickListener(createCancelClickListener());

        LinearLayoutManager layout = new LinearLayoutManager(this);
        view.setLayoutManager(layout);
        view.setAdapter(mAdapter);

        mPlaylistRegister.beginTransaction();
    }

    private View.OnClickListener createApplyClickListener() {
        return v -> {
            mPlaylistRealm.setName(mPlaylistName.getText().toString());
            mPlaylistRegister.commitTransaction();
            finish();
        };
    }

    private View.OnClickListener createCancelClickListener() {
        return v -> {
            mPlaylistRegister.cancelTransaction();
            finish();
        };
    }

    private ItemTouchHelper createItemTouchHelper() {
        return new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
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

                // 削除
                mList.remove(fromPos);
                mAdapter.notifyItemRemoved(fromPos);
            }
        });
    }
}
