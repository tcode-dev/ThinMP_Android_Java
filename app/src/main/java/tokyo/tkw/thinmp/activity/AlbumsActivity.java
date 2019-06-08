package tokyo.tkw.thinmp.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.adapter.AlbumListAdapter;
import tokyo.tkw.thinmp.provider.AlbumsContentProvider;

public class AlbumsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_albums);

        RecyclerView listView = findViewById(R.id.list);

        AlbumsContentProvider albumsContentProvider = new AlbumsContentProvider(this);
        AlbumListAdapter adapter = new AlbumListAdapter(this, albumsContentProvider.getList());
        GridLayoutManager layout = new GridLayoutManager(this, 2);
        listView.setLayoutManager(layout);
        listView.setAdapter(adapter);
    }
}
