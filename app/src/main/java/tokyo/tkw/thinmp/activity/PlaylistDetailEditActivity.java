package tokyo.tkw.thinmp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.List;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.adapter.PlaylistDetailEditAdapter;
import tokyo.tkw.thinmp.dto.PlaylistDetailEditDto;
import tokyo.tkw.thinmp.listener.CancelClickListener;
import tokyo.tkw.thinmp.logic.PlaylistDetailEditLogic;
import tokyo.tkw.thinmp.playlist.Playlist;
import tokyo.tkw.thinmp.playlist.PlaylistRegister;
import tokyo.tkw.thinmp.touch.EditItemTouchHelper;
import tokyo.tkw.thinmp.track.Track;

public class PlaylistDetailEditActivity extends BaseActivity {
    private PlaylistDetailEditAdapter adapter;
    private String playlistId;
    private List<Track> trackList;
    private List<String> trackIdList;
    private EditText playlistNameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_playlist_detail_edit);

        initWithPermissionCheck();
    }

    @Override
    protected void init() {
        // playlistId
        playlistId = getIntent().getStringExtra(Playlist.PLAYLIST_ID);

        // view
        playlistNameView = findViewById(R.id.playlistName);
        RecyclerView listView = findViewById(R.id.list);
        Button applyView = findViewById(R.id.apply);
        Button cancelView = findViewById(R.id.cancel);

        // logic
        PlaylistDetailEditLogic logic = PlaylistDetailEditLogic.createInstance(this, playlistId);

        // dto
        PlaylistDetailEditDto dto = logic.createDto();

        // 曲一覧
        trackList = dto.trackList;

        // id一覧
        trackIdList = dto.trackIdList;

        // プレイリスト名
        playlistNameView.setText(dto.playlistName);

        // adapter
        adapter = new PlaylistDetailEditAdapter(trackList);
        listView.setAdapter(adapter);

        // layout
        LinearLayoutManager layout = new LinearLayoutManager(this);
        listView.setLayoutManager(layout);

        // ドラッグとスワイプ
        EditItemTouchHelper editItemTouchHelper = new EditItemTouchHelper(adapter, trackList);
        ItemTouchHelper itemTouchHelper = editItemTouchHelper.createItemTouchHelper();
        itemTouchHelper.attachToRecyclerView(listView);

        // event
        applyView.setOnClickListener(createApplyClickListener());
        cancelView.setOnClickListener(new CancelClickListener());
    }

    private View.OnClickListener createApplyClickListener() {
        return v -> {
            PlaylistRegister register = PlaylistRegister.createInstance();
            String name = playlistNameView.getText().toString();
            List<String> toTrackIdList = Stream.of(trackList).map(Track::getId).collect(Collectors.toList());

            register.update(playlistId, name, trackIdList, toTrackIdList);

            finish();
        };
    }
}
