package tokyo.tkw.thinmp.activity;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.epoxy.controller.ArtistsController;
import tokyo.tkw.thinmp.music.ArtistCollection;

public class ArtistsActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artists);

        RecyclerView listView = findViewById(R.id.list);

        ArtistsController controller = new ArtistsController();

        ArtistCollection artistCollection =
                ArtistCollection.createAllArtistCollectionInstance(this);
        ArtistsController.Data data = new ArtistsController.Data();
        data.title = getResources().getString(R.string.artists);
        data.artistList = artistCollection.getList();
        data.artistAlbumArtMap = artistCollection.getAllArtistAlbumArtMap();
        controller.setData(data);

        LinearLayoutManager layout = new LinearLayoutManager(this);
        listView.setLayoutManager(layout);
        listView.setAdapter(controller.getAdapter());
    }
}
