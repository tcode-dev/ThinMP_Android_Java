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
import tokyo.tkw.thinmp.adapter.FavoriteSongsEditAdapter;
import tokyo.tkw.thinmp.dto.FavoriteSongsEditDto;
import tokyo.tkw.thinmp.listener.CancelClickListener;
import tokyo.tkw.thinmp.logic.FavoriteSongsEditLogic;
import tokyo.tkw.thinmp.registration.edit.FavoriteSongEditor;
import tokyo.tkw.thinmp.touch.EditItemTouchHelper;
import tokyo.tkw.thinmp.track.Track;

public class FavoriteSongsEditActivity extends BaseActivity {
    private List<Track> trackList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_favorite_songs_edit);

        initWithPermissionCheck();
    }

    @Override
    protected void init() {
        // view
        RecyclerView listView = findViewById(R.id.list);
        Button applyView = findViewById(R.id.apply);
        Button cancelView = findViewById(R.id.cancel);

        // logic
        FavoriteSongsEditLogic logic = FavoriteSongsEditLogic.createInstance(this);

        // dto
        FavoriteSongsEditDto dto = logic.createDto();

        // favoriteList
        trackList = dto.trackList;

        // adapter
        RecyclerView.Adapter adapter = new FavoriteSongsEditAdapter(dto.trackList);
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
        return view -> {
            apply();
            finish();
        };
    }

    private void apply() {
        FavoriteSongEditor favoriteSongEditor = FavoriteSongEditor.createInstance();

        List<String> trackIdList = Stream.of(trackList).map(Track::getId).collect(Collectors.toList());

        favoriteSongEditor.update(trackIdList);
    }
}
