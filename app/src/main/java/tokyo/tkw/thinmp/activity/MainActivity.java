package tokyo.tkw.thinmp.activity;

import android.os.Bundle;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.adapter.AlbumListAdapter;
import tokyo.tkw.thinmp.adapter.LibraryAdapter;
import tokyo.tkw.thinmp.dto.MainDto;
import tokyo.tkw.thinmp.logic.MainLogic;

public class MainActivity extends BaseActivity {
    private static final Class<?>[] MENU_LINK_LIST = {
            ArtistsActivity.class,
            AlbumsActivity.class,
            TracksActivity.class,
            FavoriteArtistsActivity.class,
            FavoriteSongsActivity.class,
            PlaylistsActivity.class
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_main);

        initWithPermissionCheck();
    }

    @Override
    protected void init() {
        // view
        RecyclerView menuView = findViewById(R.id.libraryMenu);
        RecyclerView list = findViewById(R.id.list);

        // logic
        MainLogic logic = MainLogic.createInstance(this);

        // dto
        MainDto dto = logic.createDto();

        // libraryAdapter
        LibraryAdapter libraryAdapter = new LibraryAdapter(this, dto.menuLabelList, MENU_LINK_LIST);
        menuView.setAdapter(libraryAdapter);

        // libraryLayout
        LinearLayoutManager libraryLayout = new LinearLayoutManager(this);
        menuView.setLayoutManager(libraryLayout);

        // 区切り線の描画
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                this, new LinearLayoutManager(this).getOrientation());
        menuView.addItemDecoration(dividerItemDecoration);

        // albumListAdapter
        AlbumListAdapter albumListAdapter = new AlbumListAdapter(dto.albumList);
        list.setAdapter(albumListAdapter);

        // albumLayout
        GridLayoutManager albumLayout = new GridLayoutManager(this, 2);
        list.setLayoutManager(albumLayout);
    }
}