package tokyo.tkw.thinmp.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.List;

import io.realm.RealmResults;
import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.adapter.FavoriteArtistEditAdapter;
import tokyo.tkw.thinmp.favorite.FavoriteArtist;
import tokyo.tkw.thinmp.favorite.FavoriteArtistList;
import tokyo.tkw.thinmp.favorite.FavoriteArtistRegister;

public class FavoriteArtistEditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_artist_edit);

        RecyclerView view = findViewById(R.id.favoriteList);

        RealmResults<FavoriteArtist> realmResults = FavoriteArtistList.getFavoriteList();
        List<FavoriteArtist> favoriteList = Stream.of(realmResults).toList();

        FavoriteArtistEditAdapter adapter = new FavoriteArtistEditAdapter(favoriteList);

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
                List<String> artistIdList = Stream.of(favoriteList).map(FavoriteArtist::getArtistId).collect(Collectors.toList());
                FavoriteArtistRegister.update(artistIdList);
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
