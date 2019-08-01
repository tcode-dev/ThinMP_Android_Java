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
import tokyo.tkw.thinmp.adapter.FavoriteArtistsEditAdapter;
import tokyo.tkw.thinmp.artist.Artist;
import tokyo.tkw.thinmp.dto.FavoriteArtistsEditDto;
import tokyo.tkw.thinmp.favorite.FavoriteArtistRegister;
import tokyo.tkw.thinmp.listener.CancelClickListener;
import tokyo.tkw.thinmp.logic.FavoriteArtistsEditLogic;
import tokyo.tkw.thinmp.touch.EditItemTouchHelper;

public class FavoriteArtistsEditActivity extends BaseActivity {
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
        RecyclerView.Adapter adapter = new FavoriteArtistsEditAdapter(dto.artistList);
        listView.setAdapter(adapter);

        // layout
        LinearLayoutManager layout = new LinearLayoutManager(this);
        listView.setLayoutManager(layout);

        // ドラッグとスワイプ
        EditItemTouchHelper editItemTouchHelper = new EditItemTouchHelper(adapter, artistList);
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
        FavoriteArtistRegister register = FavoriteArtistRegister.createInstance();
        List<String> artistIdList = Stream.of(artistList).map(Artist::getId).collect(Collectors.toList());
        register.update(artistIdList);
    }
}
