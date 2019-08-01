package tokyo.tkw.thinmp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.List;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.adapter.PlaylistsEditAdapter;
import tokyo.tkw.thinmp.dto.PlaylistsEditDto;
import tokyo.tkw.thinmp.logic.PlaylistsEditLogic;
import tokyo.tkw.thinmp.playlist.Playlist;
import tokyo.tkw.thinmp.playlist.PlaylistRegister;

public class PlaylistsEditActivity extends BaseActivity {
    private PlaylistsEditAdapter adapter;
    private List<String> playlistIdList;
    private List<Playlist> playlists;

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

        // プレイリストID一覧
        playlistIdList = dto.playlistIdList;
        playlists = dto.playlists;

        // adapter
        adapter = new PlaylistsEditAdapter(playlists);
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
    }

    private View.OnClickListener createApplyClickListener() {
        return v -> {
            PlaylistRegister playlistRegister = PlaylistRegister.createInstance();
            List<String> toPlaylistIdList = Stream.of(playlists).map(Playlist::getId).collect(Collectors.toList());

            playlistRegister.update(playlistIdList, toPlaylistIdList);
            finish();
        };
    }

    private View.OnClickListener createCancelClickListener() {
        return v -> {
            finish();
        };
    }

    private ItemTouchHelper createItemTouchHelper() {
        return new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                ItemTouchHelper.LEFT
        ) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                final int fromPos = viewHolder.getAdapterPosition();
                final int toPos = target.getAdapterPosition();

                // viewの並び替え
                adapter.notifyItemMoved(fromPos, toPos);

                // dataの並び替え
                playlists.add(toPos, playlists.remove(fromPos));

                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                final int fromPos = viewHolder.getAdapterPosition();

                // 削除
                playlists.remove(fromPos);
                adapter.notifyItemRemoved(fromPos);
            }
        });
    }
}
