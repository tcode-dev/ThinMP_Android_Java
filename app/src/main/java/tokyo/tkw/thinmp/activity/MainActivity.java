package tokyo.tkw.thinmp.activity;

import android.os.Bundle;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.adapter.AlbumListAdapter;
import tokyo.tkw.thinmp.adapter.LibraryAdapter;
import tokyo.tkw.thinmp.provider.AllAlbumsContentProvider;

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
        LibraryAdapter libraryAdapter = new LibraryAdapter(this,
                getResources().getStringArray(R.array.library_menu), MENU_LINK_LIST);
        LinearLayoutManager libraryLayout = new LinearLayoutManager(this);
        RecyclerView menuView = findViewById(R.id.libraryMenu);

        menuView.setLayoutManager(libraryLayout);
        menuView.setAdapter(libraryAdapter);

        // 区切り線の描画
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                this, new LinearLayoutManager(this).getOrientation());
        menuView.addItemDecoration(dividerItemDecoration);

        RecyclerView list = findViewById(R.id.list);
        AllAlbumsContentProvider allAlbumsContentProvider = new AllAlbumsContentProvider(this);
        AlbumListAdapter albumListAdapter =
                new AlbumListAdapter(allAlbumsContentProvider.getList());
        GridLayoutManager albumLayout = new GridLayoutManager(this, 2);

        list.setLayoutManager(albumLayout);
        list.setAdapter(albumListAdapter);
    }
}