package tokyo.tkw.thinmp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.adapter.FavoriteArtistListAdapter;
import tokyo.tkw.thinmp.favorite.FavoriteArtistList;
import tokyo.tkw.thinmp.music.Artist;

public class FavoriteArtistsActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_artists);

        RecyclerView favoriteListView = findViewById(R.id.list);

        FavoriteArtistList favoriteArtistList = new FavoriteArtistList(this);
        FavoriteArtistListAdapter adapter =
                new FavoriteArtistListAdapter(favoriteArtistList.getRealmResults(),
                        favoriteArtistList.getArtistMap(),
                        favoriteArtistList.getArtistAlbumArtMap(),
                        createFavoriteArtistListListener());

        LinearLayoutManager layout = new LinearLayoutManager(this);
        favoriteListView.setLayoutManager(layout);
        favoriteListView.setAdapter(adapter);

        findViewById(R.id.edit).setOnClickListener(createEditClickListener());
    }

    private FavoriteArtistListAdapter.FavoriteArtistListListener createFavoriteArtistListListener() {
        return artistId -> {
            Intent intent = new Intent(this, ArtistDetailActivity.class);
            intent.putExtra(Artist.ARTIST_ID, artistId);
            startActivity(intent);
        };
    }

    private View.OnClickListener createEditClickListener() {
        return v -> {
            Intent intent = new Intent(this, FavoriteArtistEditActivity.class);
            startActivity(intent);
        };
    }
}
