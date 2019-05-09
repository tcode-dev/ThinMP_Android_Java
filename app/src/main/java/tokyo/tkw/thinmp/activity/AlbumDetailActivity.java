package tokyo.tkw.thinmp.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.adapter.AlbumTrackListAdapter;
import tokyo.tkw.thinmp.fragment.MiniPlayerFragment;
import tokyo.tkw.thinmp.menu.TrackMenu;
import tokyo.tkw.thinmp.music.Album;
import tokyo.tkw.thinmp.music.MusicList;
import tokyo.tkw.thinmp.music.Track;
import tokyo.tkw.thinmp.provider.ThumbnailProvider;
import tokyo.tkw.thinmp.view.ResponsiveTextView;

public class AlbumDetailActivity extends AppCompatActivity implements AlbumTrackListAdapter.OnTrackListItemClickListener {
    private ArrayList<Track> mTrackList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_detail);

        String albumId = getIntent().getStringExtra("albumId");
        Album album = MusicList.getAlbum(albumId);

        // サムネイル
        ImageView thumbnailView = findViewById(R.id.thumbnail);
        Bitmap thumbnailBitmap = new ThumbnailProvider().getThumbnail(album.getThumbnailId());
        thumbnailView.setImageBitmap(thumbnailBitmap);

        // アルバム名
        ResponsiveTextView titleView = findViewById(R.id.title);
        titleView.setText(album.getName());

        // アーティスト名
        ResponsiveTextView subTitleView = findViewById(R.id.subTitle);
        subTitleView.setText(album.getArtistName());

        // 曲一覧
        mTrackList = MusicList.getAlbumTrackList(albumId);

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
