package tokyo.tkw.thinmp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.adapter.FavoriteSongListAdapter;
import tokyo.tkw.thinmp.favorite.FavoriteSongList;
import tokyo.tkw.thinmp.listener.FavoriteSongClickListener;

public class FavoriteSongsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_favorite_songs);

        initWithPermissionCheck();
    }

    @Override
    protected void init() {
        RecyclerView favoriteListView = findViewById(R.id.list);

        FavoriteSongList favoriteSongList = new FavoriteSongList(this);

        FavoriteSongListAdapter adapter =
                new FavoriteSongListAdapter(favoriteSongList.getRealmResults(),
                        favoriteSongList.getTrackMap(), new FavoriteSongClickListener());

        LinearLayoutManager layout = new LinearLayoutManager(this);
        favoriteListView.setLayoutManager(layout);
        favoriteListView.setAdapter(adapter);

        findViewById(R.id.edit).setOnClickListener(createEditClickListener(this));
    }

    private View.OnClickListener createEditClickListener(Context context) {
        return v -> {
            Intent intent = new Intent(context, FavoriteSongEditActivity.class);
            context.startActivity(intent);
        };
    }
}
