package tokyo.tkw.thinmp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.adapter.FavoriteArtistListAdapter;
import tokyo.tkw.thinmp.favorite.FavoriteArtistList;

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
        FavoriteArtistListAdapter adapter =
                new FavoriteArtistListAdapter(FavoriteArtistList.getFavoriteList(),
                        favoriteArtistListListener(this));
        favoriteListView.setAdapter(adapter);

        LinearLayoutManager layout = new LinearLayoutManager(this);
        favoriteListView.setLayoutManager(layout);

        // 区切り線の描画
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                this, new LinearLayoutManager(this).getOrientation());
        favoriteListView.addItemDecoration(dividerItemDecoration);
    }

    private void initEdit() {
        findViewById(R.id.edit).setOnClickListener(editClickListener(this));
    }

    private FavoriteArtistListAdapter.FavoriteArtistListListener favoriteArtistListListener(Context context) {
        return new FavoriteArtistListAdapter.FavoriteArtistListListener() {
            @Override
            public void onClick(String artistId) {
                Intent intent = new Intent(context, ArtistDetailActivity.class);
                intent.putExtra("artistId", artistId);
                startActivity(intent);
            }
        };
    }

    private View.OnClickListener editClickListener(Context context) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FavoriteArtistEditActivity.class);
                startActivity(intent);
            }
        };
    }
}
