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
import tokyo.tkw.thinmp.adapter.FavoriteSongsEditAdapter;
import tokyo.tkw.thinmp.dto.FavoriteSongsEditDto;
import tokyo.tkw.thinmp.favorite.FavoriteSongRegister;
import tokyo.tkw.thinmp.logic.FavoriteSongsEditLogic;
import tokyo.tkw.thinmp.realm.FavoriteSongRealm;
import tokyo.tkw.thinmp.track.Track;

public class FavoriteSongsEditActivity extends BaseActivity {
    FavoriteSongsEditAdapter adapter;
    List<Track> trackList;

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
        adapter = new FavoriteSongsEditAdapter(dto.trackList);
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
        return view -> {
            List<String> trackIdList = Stream.of(trackList)
                    .map(Track::getId)
                    .collect(Collectors.toList());
            FavoriteSongRegister.update(trackIdList);
            finish();
        };
    }

    private View.OnClickListener createCancelClickListener() {
        return view -> finish();
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
                trackList.add(toPos, trackList.remove(fromPos));

                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                final int fromPos = viewHolder.getAdapterPosition();

                // 削除
                trackList.remove(fromPos);
                adapter.notifyItemRemoved(fromPos);
            }
        });
    }
}
