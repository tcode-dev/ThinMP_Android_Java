package tokyo.tkw.thinmp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.annimon.stream.Stream;

import io.realm.RealmList;
import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.adapter.PlaylistsEditAdapter;
import tokyo.tkw.thinmp.dto.PlaylistsEditDto;
import tokyo.tkw.thinmp.logic.PlaylistsEditLogic;
import tokyo.tkw.thinmp.playlist.PlaylistRegister;
import tokyo.tkw.thinmp.realm.PlaylistRealm;

public class PlaylistsEditActivity extends BaseActivity {
    private PlaylistsEditAdapter mAdapter;
    private RealmList<PlaylistRealm> realmList;
    private PlaylistRegister mPlaylistRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_playlists_edit);

        initWithPermissionCheck();
    }

    @Override
    protected void init() {
        // view
        RecyclerView listView = findViewById(R.id.list);
        Button applyView = findViewById(R.id.apply);
        Button cancelView = findViewById(R.id.cancel);

        // logic
        PlaylistsEditLogic logic = PlaylistsEditLogic.createInstance(this);

        // dto
        PlaylistsEditDto dto = logic.createDto();

        // プレイリスト一覧
        realmList = dto.realmList;

        // adapter
        mAdapter = new PlaylistsEditAdapter(realmList, dto.playlistMap);
        listView.setAdapter(mAdapter);

        // layout
        LinearLayoutManager layout = new LinearLayoutManager(this);
        listView.setLayoutManager(layout);

        // ドラッグとスワイプ
        ItemTouchHelper itemTouchHelper = createItemTouchHelper();
        itemTouchHelper.attachToRecyclerView(listView);

        // event
        applyView.setOnClickListener(createApplyClickListener());
        cancelView.setOnClickListener(createCancelClickListener());

        // transaction
        mPlaylistRegister = PlaylistRegister.createInstance();
        mPlaylistRegister.beginTransaction();
    }

    @Override
    public void onBackPressed() {
        mPlaylistRegister.cancelTransaction();

        super.onBackPressed();
    }

    private View.OnClickListener createApplyClickListener() {
        return v -> {
            Stream.of(realmList).forEachIndexed((i, realm) -> {
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
                realmList.add(toPos, realmList.remove(fromPos));

                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                final int fromPos = viewHolder.getAdapterPosition();

                // 削除
                realmList.get(fromPos).deleteFromRealm();
                realmList.remove(fromPos);
                mAdapter.notifyItemRemoved(fromPos);
            }
        });
    }
}
