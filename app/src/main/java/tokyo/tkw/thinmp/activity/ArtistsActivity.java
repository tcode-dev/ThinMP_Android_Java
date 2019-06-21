package tokyo.tkw.thinmp.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.ArrayList;
import java.util.Map;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.epoxy.controller.ArtistsController;
import tokyo.tkw.thinmp.music.ArtistAlbumArt;
import tokyo.tkw.thinmp.music.ArtistCollection;
import tokyo.tkw.thinmp.provider.ArtistAlbumArtContentProvider;

public class ArtistsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artists);

        initList();
    }

    private void initList() {
        RecyclerView listView = findViewById(R.id.list);
        LinearLayoutManager layout = new LinearLayoutManager(this);
        ArtistsController controller = new ArtistsController();
        listView.setLayoutManager(layout);
        listView.setAdapter(controller.getAdapter());

        ArtistAlbumArtContentProvider artistAlbumArtContentProvider =
                new ArtistAlbumArtContentProvider(this);
        ArrayList<ArtistAlbumArt> artistAlbumArtList = artistAlbumArtContentProvider.getList();
        Map<String, String> artistAlbumArtMap =
                Stream.of(artistAlbumArtList).distinctBy(artistAlbumArt -> artistAlbumArt.artistId).collect(Collectors.toMap(artistAlbumArt -> artistAlbumArt.artistId, artistAlbumArt -> artistAlbumArt.albumArtId));

        ArtistCollection artistCollection =
                ArtistCollection.createAllArtistCollectionInstance(this);
        ArtistsController.Data data = new ArtistsController.Data();
        data.title = getResources().getString(R.string.artists);
        data.artistList = artistCollection.getList();
        data.artistAlbumArtMap = artistAlbumArtMap;
        controller.setData(data);
    }
}
