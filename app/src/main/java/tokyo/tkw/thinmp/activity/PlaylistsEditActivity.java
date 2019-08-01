package tokyo.tkw.thinmp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.List;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.adapter.PlaylistsEditAdapter;
import tokyo.tkw.thinmp.dto.PlaylistsEditDto;
import tokyo.tkw.thinmp.listener.CancelClickListener;
import tokyo.tkw.thinmp.logic.PlaylistsEditLogic;
import tokyo.tkw.thinmp.playlist.Playlist;
import tokyo.tkw.thinmp.playlist.PlaylistRegister;
import tokyo.tkw.thinmp.touch.EditItemTouchHelper;

public class PlaylistsEditActivity extends BaseActivity {
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
        RecyclerView.Adapter adapter = new PlaylistsEditAdapter(playlists);
        listView.setAdapter(adapter);

        // layout
        LinearLayoutManager layout = new LinearLayoutManager(this);
        listView.setLayoutManager(layout);

        // ドラッグとスワイプ
        EditItemTouchHelper editItemTouchHelper = new EditItemTouchHelper(adapter, playlists);
        ItemTouchHelper itemTouchHelper = editItemTouchHelper.createItemTouchHelper();
        itemTouchHelper.attachToRecyclerView(listView);

        // event
        applyView.setOnClickListener(createApplyClickListener());
        cancelView.setOnClickListener(new CancelClickListener());
    }

    private View.OnClickListener createApplyClickListener() {
        return view -> {
            apply();
            finish();
        };
    }

    private void apply() {
        PlaylistRegister playlistRegister = PlaylistRegister.createInstance();

        List<String> toPlaylistIdList = Stream.of(playlists).map(Playlist::getId).collect(Collectors.toList());

        playlistRegister.update(playlistIdList, toPlaylistIdList);
    }
}
