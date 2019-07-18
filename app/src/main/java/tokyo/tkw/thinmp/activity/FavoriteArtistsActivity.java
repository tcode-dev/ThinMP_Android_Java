package tokyo.tkw.thinmp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.adapter.FavoriteArtistsAdapter;
import tokyo.tkw.thinmp.dto.FavoriteArtistsDto;
import tokyo.tkw.thinmp.logic.FavoriteArtistsLogic;
import tokyo.tkw.thinmp.music.Artist;

public class FavoriteArtistsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_favorite_artists);

        initWithPermissionCheck();
    }

    @Override
    protected void init() {
        // view
        RecyclerView favoriteListView = findViewById(R.id.list);
        Button editView = findViewById(R.id.edit);

        // logic
        FavoriteArtistsLogic logic = FavoriteArtistsLogic.createInstance(this);

        // dto
        FavoriteArtistsDto dto = logic.createDto();

        // adapter
        FavoriteArtistsAdapter adapter = new FavoriteArtistsAdapter(
                dto.realmResults,
                dto.artistMap,
                createFavoriteArtistListListener()
        );
        favoriteListView.setAdapter(adapter);

        // layout
        LinearLayoutManager layout = new LinearLayoutManager(this);
        favoriteListView.setLayoutManager(layout);

        // event
        editView.setOnClickListener(createEditClickListener());
    }

    private FavoriteArtistsAdapter.FavoriteArtistListListener createFavoriteArtistListListener() {
        return artistId -> {
            Intent intent = new Intent(this, ArtistDetailActivity.class);
            intent.putExtra(Artist.ARTIST_ID, artistId);
            startActivity(intent);
        };
    }

    private View.OnClickListener createEditClickListener() {
        return v -> {
            Intent intent = new Intent(this, FavoriteArtistsEditActivity.class);
            startActivity(intent);
        };
    }
}
