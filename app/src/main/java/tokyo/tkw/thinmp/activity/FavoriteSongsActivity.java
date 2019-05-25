package tokyo.tkw.thinmp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.adapter.FavoriteSongListAdapter;
import tokyo.tkw.thinmp.favorite.FavoriteSongList;
import tokyo.tkw.thinmp.fragment.MiniPlayerFragment;
import tokyo.tkw.thinmp.menu.TrackMenu;
import tokyo.tkw.thinmp.music.Track;

public class FavoriteSongsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_songs);

        initList();
        initEdit();
    }

    private void initList() {
        RecyclerView favoriteListView = findViewById(R.id.list);
        FavoriteSongListAdapter adapter =
                new FavoriteSongListAdapter(FavoriteSongList.getFavoriteList(),
                        favoriteListItemClickListener(this));
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

    private View.OnClickListener editClickListener(Context context) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FavoriteSongEditActivity.class);
                context.startActivity(intent);
            }
        };
    }

    private FavoriteSongListAdapter.OnFavoriteListItemClickListener favoriteListItemClickListener(Context context) {
        return new FavoriteSongListAdapter.OnFavoriteListItemClickListener() {

            @Override
            public void onClickItem(int position) {
                Fragment fragment =
                        getSupportFragmentManager().findFragmentById(R.id.includeMiniPlayer);
                if (fragment instanceof MiniPlayerFragment) {
                    ((MiniPlayerFragment) fragment).start(FavoriteSongList.getTrackList(),
                            position);
                }
            }

            @Override
            public void onClickMenu(View view, Track track) {
                TrackMenu trackMenu = new TrackMenu(context, view, track);
                trackMenu.show();
            }
        };
    }
}
