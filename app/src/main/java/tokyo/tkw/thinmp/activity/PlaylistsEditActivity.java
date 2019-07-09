package tokyo.tkw.thinmp.activity;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.annimon.stream.Stream;

import io.realm.RealmList;
import io.realm.RealmResults;
import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.adapter.PlaylistsEditAdapter;
import tokyo.tkw.thinmp.playlist.PlaylistCollection;
import tokyo.tkw.thinmp.playlist.PlaylistRegister;
import tokyo.tkw.thinmp.realm.PlaylistRealm;

public class PlaylistsEditActivity extends BaseActivity {
    private PlaylistsEditAdapter mAdapter;
    private RealmList<PlaylistRealm> mList;
    private PlaylistRegister mPlaylistRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_playlists_edit);

        initWithPermissionCheck();
    }

    @Override
    protected void init() {
        RecyclerView view = findViewById(R.id.list);

        PlaylistCollection playlistCollection = PlaylistCollection.createInstance(this);

        RealmResults<PlaylistRealm> RealmResults  = playlistCollection.findAll();
        mList = playlistCollection.toRealmList(RealmResults);
        mAdapter = new PlaylistsEditAdapter(mList);
        mPlaylistRegister = new PlaylistRegister();

        ItemTouchHelper itemTouchHelper = createItemTouchHelper();
        itemTouchHelper.attachToRecyclerView(view);

        findViewById(R.id.apply).setOnClickListener(createApplyClickListener());
        findViewById(R.id.cancel).setOnClickListener(createCancelClickListener());

        LinearLayoutManager layout = new LinearLayoutManager(this);

        view.setLayoutManager(layout);
        view.setAdapter(mAdapter);

        mPlaylistRegister.beginTransaction();
    }

    @Override
    public void onBackPressed() {
        mPlaylistRegister.cancelTransaction();

        super.onBackPressed();
    }

    private View.OnClickListener createApplyClickListener() {
        return v -> {
            Stream.of(mList).forEachIndexed((i, realm) -> {
                realm.setOrder(i + 1);
            });
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
                mList.get(fromPos).deleteFromRealm();
                mList.remove(fromPos);
                mAdapter.notifyItemRemoved(fromPos);
            }
        });
    }
}
