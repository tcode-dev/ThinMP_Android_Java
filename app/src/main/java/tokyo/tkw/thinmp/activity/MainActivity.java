package tokyo.tkw.thinmp.activity;

import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.adapter.AlbumListAdapter;
import tokyo.tkw.thinmp.adapter.LibraryAdapter;
import tokyo.tkw.thinmp.music.MusicList;
import tokyo.tkw.thinmp.provider.AlbumsContentProvider;

public class MainActivity extends AppCompatActivity {
    private static final int PERMISSION_CODE = 1;
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

        MusicList.setInstance(this);

        setMenu();
        setList();
    }

    private void setMenu() {
        LibraryAdapter adapter = new LibraryAdapter(this,
                getResources().getStringArray(R.array.library_menu), MENU_LINK_LIST);
        LinearLayoutManager layout = new LinearLayoutManager(this);
        RecyclerView menuView = findViewById(R.id.libraryMenu);

        menuView.setLayoutManager(layout);
        menuView.setAdapter(adapter);

        // 区切り線の描画
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                this, new LinearLayoutManager(this).getOrientation());
        menuView.addItemDecoration(dividerItemDecoration);
    }

    private void setList() {
        RecyclerView list = findViewById(R.id.list);
        AlbumsContentProvider albumContentProvider = new AlbumsContentProvider(this);
        AlbumListAdapter adapter = new AlbumListAdapter(this, albumContentProvider.getList());
        GridLayoutManager layout = new GridLayoutManager(this, 2);

        list.setLayoutManager(layout);
        list.setAdapter(adapter);
    }

    /**
     * ユーザーにパーミッション権限を要求した承認結果を受け取る
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    recreate();
                }

                return;
            }
        }
    }
}