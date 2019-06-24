package tokyo.tkw.thinmp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.adapter.FavoriteArtistListAdapter;
import tokyo.tkw.thinmp.favorite.FavoriteArtistList;
import tokyo.tkw.thinmp.music.Artist;

public class FavoriteArtistsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_artists);

        initList();
        initEdit();
    }

    private void initList() {
        RecyclerView favoriteListView = findViewById(R.id.list);

        FavoriteArtistList favoriteArtistList = new FavoriteArtistList(this);
        FavoriteArtistListAdapter adapter =
                new FavoriteArtistListAdapter(favoriteArtistList.getRealmResults(),
                        favoriteArtistList.getArtistMap(),
                        favoriteArtistList.getArtistAlbumArtMap(),
                        favoriteArtistListListener());
        favoriteListView.setAdapter(adapter);

        LinearLayoutManager layout = new LinearLayoutManager(this);
        favoriteListView.setLayoutManager(layout);
    }

    private void initEdit() {
        findViewById(R.id.edit).setOnClickListener(editClickListener());
    }

    private FavoriteArtistListAdapter.FavoriteArtistListListener favoriteArtistListListener() {
        return artistId -> {
            Intent intent = new Intent(this, ArtistDetailActivity.class);
            intent.putExtra(Artist.ARTIST_ID, artistId);
            startActivity(intent);
        };
    }

    private View.OnClickListener editClickListener() {
        return v -> {
            Intent intent = new Intent(this, FavoriteArtistEditActivity.class);
            startActivity(intent);
        };
    }
}
