package tokyo.tkw.thinmp.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.adapter.AlbumListAdapter;
import tokyo.tkw.thinmp.music.AlbumCollection;

public class AlbumsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_albums);

        RecyclerView listView = findViewById(R.id.list);

        AlbumCollection albumCollection = AlbumCollection.createAllAlbumCollectionInstance(this);
        AlbumListAdapter adapter = new AlbumListAdapter(albumCollection.getList());
        GridLayoutManager layout = new GridLayoutManager(this, 2);

        listView.setLayoutManager(layout);
        listView.setAdapter(adapter);
    }
}
