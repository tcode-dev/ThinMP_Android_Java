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
import tokyo.tkw.thinmp.adapter.FavoriteArtistsEditAdapter;
import tokyo.tkw.thinmp.artist.Artist;
import tokyo.tkw.thinmp.dto.FavoriteArtistsEditDto;
import tokyo.tkw.thinmp.favorite.FavoriteArtistRegister;
import tokyo.tkw.thinmp.logic.FavoriteArtistsEditLogic;

public class FavoriteArtistsEditActivity extends BaseActivity {
    private FavoriteArtistsEditAdapter adapter;
    private List<Artist> artistList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_favorite_artists_edit);

        initWithPermissionCheck();
    }

    @Override
    protected void init() {
        // view
        RecyclerView listView = findViewById(R.id.list);
        Button applyView = findViewById(R.id.apply);
        Button cancelView = findViewById(R.id.cancel);

        // logic
        FavoriteArtistsEditLogic logic = FavoriteArtistsEditLogic.createInstance(this);

        // dto
        FavoriteArtistsEditDto dto = logic.createDto();

        // artistList
        artistList = dto.artistList;

        // adapter
        adapter = new FavoriteArtistsEditAdapter(dto.artistList);
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
            FavoriteArtistRegister register = FavoriteArtistRegister.createInstance();
            List<String> artistIdList = Stream.of(artistList).map(Artist::getId).collect(Collectors.toList());
            register.allUpdate(artistIdList);
            finish();
        };
    }

    private View.OnClickListener createCancelClickListener() {
        return view -> {
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
                artistList.add(toPos, artistList.remove(fromPos));

                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                final int fromPos = viewHolder.getAdapterPosition();

                // 削除
                artistList.remove(fromPos);
                adapter.notifyItemRemoved(fromPos);
            }
        });
    }
}
