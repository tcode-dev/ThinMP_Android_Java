package tokyo.tkw.thinmp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.ArrayList;
import java.util.Map;

import io.realm.OrderedRealmCollection;
import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.adapter.FavoriteArtistListAdapter;
import tokyo.tkw.thinmp.favorite.FavoriteArtistList;
import tokyo.tkw.thinmp.music.Artist;
import tokyo.tkw.thinmp.provider.ArtistsContentProvider;
import tokyo.tkw.thinmp.realm.FavoriteArtistRealm;

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
        OrderedRealmCollection<FavoriteArtistRealm> realmResults =
                FavoriteArtistList.getFavoriteList();

        ArrayList<FavoriteArtistRealm> favoriteList =
                (ArrayList<FavoriteArtistRealm>) Stream.of(realmResults).toList();
        ArrayList<String> artistIdList =
                (ArrayList<String>) Stream.of(favoriteList).map(FavoriteArtistRealm::getArtistId).collect(Collectors.toList());
        ArtistsContentProvider artistsContentProvider = new ArtistsContentProvider(this,
                artistIdList);
        Map<String, Artist> artistMap =
                Stream.of(artistsContentProvider.getList()).collect(Collectors.toMap(artist -> artist.getId(), artist -> artist));
        FavoriteArtistListAdapter adapter =
                new FavoriteArtistListAdapter(realmResults, artistMap,
                        favoriteArtistListListener());
        favoriteListView.setAdapter(adapter);

        LinearLayoutManager layout = new LinearLayoutManager(this);
        favoriteListView.setLayoutManager(layout);

        // 区切り線の描画
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                this, new LinearLayoutManager(this).getOrientation());
        favoriteListView.addItemDecoration(dividerItemDecoration);
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
