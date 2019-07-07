package tokyo.tkw.thinmp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import io.realm.RealmList;
import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.adapter.PlaylistDetailEditAdapter;
import tokyo.tkw.thinmp.dto.PlaylistDetailEditDto;
import tokyo.tkw.thinmp.logic.PlaylistDetailEditLogic;
import tokyo.tkw.thinmp.playlist.PlaylistRegister;
import tokyo.tkw.thinmp.realm.PlaylistRealm;
import tokyo.tkw.thinmp.realm.PlaylistTrackRealm;

public class PlaylistDetailEditActivity extends BaseActivity {
    public RealmList<PlaylistTrackRealm> trackRealmList;
    public PlaylistDetailEditAdapter adapter;
    private PlaylistRealm playlistRealm;
    private PlaylistRegister playlistRegister;
    private EditText playlistName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_playlist_detail_edit);

        initWithPermissionCheck();
    }

    @Override
    protected void init() {
        // playlistId
        int playlistId = getIntent().getIntExtra(PlaylistTrackRealm.PLAYLIST_ID, 0);

        // view
        playlistName = findViewById(R.id.playlistName);
        RecyclerView listView = findViewById(R.id.list);
        Button applyView = findViewById(R.id.apply);
        Button cancelView = findViewById(R.id.cancel);

        // logic
        PlaylistDetailEditLogic logic = PlaylistDetailEditLogic.createInstance(this, playlistId);

        // dto
        PlaylistDetailEditDto dto = logic.createDto();

        // playlistRealm
        playlistRealm = dto.playlistRealm;

        // プレイリストの曲一覧
        trackRealmList = dto.trackRealmList;

        // プレイリスト名
        playlistName.setText(dto.playlistName);

        // adapter
        adapter = new PlaylistDetailEditAdapter(trackRealmList, dto.trackMap);
        listView.setAdapter(adapter);

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
        playlistRegister = new PlaylistRegister();
        playlistRegister.beginTransaction();
    }

    @Override
    public void onBackPressed() {
        playlistRegister.cancelTransaction();

        super.onBackPressed();
    }

    private View.OnClickListener createApplyClickListener() {
        return v -> {
            playlistRealm.setName(playlistName.getText().toString());
            playlistRegister.commitTransaction();
            finish();
        };
    }

    private View.OnClickListener createCancelClickListener() {
        return v -> {
            playlistRegister.cancelTransaction();
            finish();
        };
    }

    private ItemTouchHelper createItemTouchHelper() {
        return new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                ItemTouchHelper.LEFT
        ) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                final int fromPos = viewHolder.getAdapterPosition();
                final int toPos = target.getAdapterPosition();

                // viewの並び替え
                adapter.notifyItemMoved(fromPos, toPos);

                // dataの並び替え
                trackRealmList.add(toPos, trackRealmList.remove(fromPos));

                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                final int fromPos = viewHolder.getAdapterPosition();

                // 削除
                trackRealmList.remove(fromPos);
                adapter.notifyItemRemoved(fromPos);
            }
        });
    }
}
