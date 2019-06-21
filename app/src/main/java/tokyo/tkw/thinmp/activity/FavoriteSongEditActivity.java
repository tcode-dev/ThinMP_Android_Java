package tokyo.tkw.thinmp.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.ArrayList;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.adapter.FavoriteSongEditAdapter;
import tokyo.tkw.thinmp.favorite.FavoriteSongList;
import tokyo.tkw.thinmp.favorite.FavoriteSongRegister;
import tokyo.tkw.thinmp.realm.FavoriteSongRealm;

public class FavoriteSongEditActivity extends AppCompatActivity {
    FavoriteSongEditAdapter mAdapter;
    ArrayList<FavoriteSongRealm> mFavoriteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_song_edit);

        RecyclerView view = findViewById(R.id.favoriteList);

        FavoriteSongList favoriteSongList = new FavoriteSongList(this);
        mFavoriteList = favoriteSongList.getList();
        mAdapter = new FavoriteSongEditAdapter(mFavoriteList, favoriteSongList.getTrackMap());

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(onSimpleCallback());
        itemTouchHelper.attachToRecyclerView(view);

        findViewById(R.id.apply).setOnClickListener(onApplyClickListener());
        findViewById(R.id.cancel).setOnClickListener(onCancelClickListener());

        LinearLayoutManager layout = new LinearLayoutManager(this);
        view.setLayoutManager(layout);
        view.setAdapter(mAdapter);
    }

    private ItemTouchHelper.SimpleCallback onSimpleCallback() {
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

    private View.OnClickListener onApplyClickListener() {
        return v -> {
            ArrayList<String> trackIdList =
                    (ArrayList<String>) Stream.of(mFavoriteList)
                            .map(FavoriteSongRealm::getTrackId)
                            .collect(Collectors.toList());
            FavoriteSongRegister.update(trackIdList);
            finish();
        };
    }

    private View.OnClickListener onCancelClickListener() {
        return v -> finish();
    }
}
