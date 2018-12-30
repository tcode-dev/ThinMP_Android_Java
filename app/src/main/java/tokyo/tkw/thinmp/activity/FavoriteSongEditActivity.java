package tokyo.tkw.thinmp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.List;

import io.realm.RealmResults;
import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.adapter.FavoriteSongEditAdapter;
import tokyo.tkw.thinmp.favorite.FavoriteSong;
import tokyo.tkw.thinmp.favorite.FavoriteSongList;
import tokyo.tkw.thinmp.favorite.FavoriteSongRegister;

public class FavoriteSongEditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_song_edit);

        RecyclerView view = findViewById(R.id.favoriteList);

        RealmResults<FavoriteSong> realmResults = FavoriteSongList.getFavoriteList();
        List<FavoriteSong> favoriteList = Stream.of(realmResults).toList();

        FavoriteSongEditAdapter adapter = new FavoriteSongEditAdapter(favoriteList);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                ItemTouchHelper.LEFT
        ) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                final int fromPos = viewHolder.getAdapterPosition();
                final int toPos = target.getAdapterPosition();

                // viewの並び替え
                adapter.notifyItemMoved(fromPos, toPos);
                // dataの並び替え
                favoriteList.add(toPos, favoriteList.remove(fromPos));

                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                final int fromPos = viewHolder.getAdapterPosition();

                favoriteList.remove(fromPos);
                adapter.notifyItemRemoved(fromPos);
            }
        });

        itemTouchHelper.attachToRecyclerView(view);

        findViewById(R.id.apply).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> trackIdList = Stream.of(favoriteList).map(FavoriteSong::getTrackId).collect(Collectors.toList());
                FavoriteSongRegister.update(trackIdList);
                finish();
            }
        });

        findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        LinearLayoutManager layout = new LinearLayoutManager(this);
        view.setLayoutManager(layout);
        view.setAdapter(adapter);
        // 区切り線の描画
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                this, new LinearLayoutManager(this).getOrientation());
        view.addItemDecoration(dividerItemDecoration);
    }
}
