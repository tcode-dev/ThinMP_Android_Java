package tokyo.tkw.thinmp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.adapter.AlbumTrackListAdapter;
import tokyo.tkw.thinmp.fragment.MiniPlayerFragment;
import tokyo.tkw.thinmp.menu.TrackMenu;
import tokyo.tkw.thinmp.music.Album;
import tokyo.tkw.thinmp.music.Track;
import tokyo.tkw.thinmp.provider.AlbumContentProvider;
import tokyo.tkw.thinmp.provider.AlbumTracksContentProvider;
import tokyo.tkw.thinmp.util.GlideUtil;
import tokyo.tkw.thinmp.view.ResponsiveTextView;

public class AlbumDetailActivity extends AppCompatActivity implements AlbumTrackListAdapter.OnTrackListItemClickListener {
    private ArrayList<Track> mTrackList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_detail);

        String albumId = getIntent().getStringExtra("albumId");

        AlbumContentProvider albumContentProvider = new AlbumContentProvider(this, albumId);
        Album album = albumContentProvider.get();

        // サムネイル
        ImageView thumbnailView = findViewById(R.id.thumbnail);
        GlideUtil.bitmap(album.getThumbnailId(), thumbnailView);

        // アルバム名
        ResponsiveTextView titleView = findViewById(R.id.title);
        titleView.setText(album.getName());

        // アーティスト名
        ResponsiveTextView subTitleView = findViewById(R.id.subTitle);
        subTitleView.setText(album.getArtistName());

        // 曲一覧
        AlbumTracksContentProvider albumTracksContentProvider =
                new AlbumTracksContentProvider(this, albumId);
        mTrackList = albumTracksContentProvider.getList();

        AlbumTrackListAdapter adapter = new AlbumTrackListAdapter(mTrackList, this);
        LinearLayoutManager layout = new LinearLayoutManager(this);
        RecyclerView listView = findViewById(R.id.list);
        listView.setLayoutManager(layout);
        listView.setAdapter(adapter);

        // 区切り線の描画
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                this, new LinearLayoutManager(this).getOrientation());
        listView.addItemDecoration(dividerItemDecoration);
    }

    @Override
    public void onClickItem(int position) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.includeMiniPlayer);
        if (fragment instanceof MiniPlayerFragment) {
            ((MiniPlayerFragment) fragment).start(mTrackList, position);
        }
    }

    @Override
    public void onClickMenu(View view, Track track) {
        TrackMenu trackMenu = new TrackMenu(this, view, track);
        trackMenu.show();
    }
}
