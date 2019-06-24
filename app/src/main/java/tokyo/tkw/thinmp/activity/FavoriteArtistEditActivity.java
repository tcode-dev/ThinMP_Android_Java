package tokyo.tkw.thinmp.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.ArrayList;
import java.util.List;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.adapter.FavoriteArtistEditAdapter;
import tokyo.tkw.thinmp.favorite.FavoriteArtistList;
import tokyo.tkw.thinmp.favorite.FavoriteArtistRegister;
import tokyo.tkw.thinmp.realm.FavoriteArtistRealm;

public class FavoriteArtistEditActivity extends AppCompatActivity {
    private ArrayList<FavoriteArtistRealm> mFavoriteList;
    private FavoriteArtistEditAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_artist_edit);

        RecyclerView view = findViewById(R.id.list);

        FavoriteArtistList favoriteArtistList = new FavoriteArtistList(this);
        mFavoriteList = favoriteArtistList.getFavoriteArtistRealmList();
        mAdapter = new FavoriteArtistEditAdapter(
                mFavoriteList,
                favoriteArtistList.getArtistMap(),
                favoriteArtistList.getArtistAlbumArtMap());

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(onItemTouchHelperSimpleCallback());
        itemTouchHelper.attachToRecyclerView(view);

        findViewById(R.id.apply).setOnClickListener(v -> {
            List<String> artistIdList =
                    Stream.of(mFavoriteList).map(FavoriteArtistRealm::getArtistId).collect(Collectors.toList());
            FavoriteArtistRegister.update(artistIdList);
            finish();
        });

        findViewById(R.id.cancel).setOnClickListener(v -> finish());

        LinearLayoutManager layout = new LinearLayoutManager(this);
        view.setLayoutManager(layout);
        view.setAdapter(mAdapter);
    }

    private ItemTouchHelper.SimpleCallback onItemTouchHelperSimpleCallback() {
        return new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                ItemTouchHelper.LEFT
        ) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {
                final int fromPos = viewHolder.getAdapterPosition();
                final int toPos = target.getAdapterPosition();

                // viewの並び替え
                mAdapter.notifyItemMoved(fromPos, toPos);
                // dataの並び替え
                mFavoriteList.add(toPos, mFavoriteList.remove(fromPos));

                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                final int fromPos = viewHolder.getAdapterPosition();

                mFavoriteList.remove(fromPos);
                mAdapter.notifyItemRemoved(fromPos);
            }
        };
    }
}
