package tokyo.tkw.thinmp.activity;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.adapter.AlbumTrackListAdapter;
import tokyo.tkw.thinmp.dto.AlbumDetailDto;
import tokyo.tkw.thinmp.listener.AlbumMenuClickListener;
import tokyo.tkw.thinmp.listener.TrackClickListener;
import tokyo.tkw.thinmp.logic.AlbumDetailLogic;
import tokyo.tkw.thinmp.music.Album;
import tokyo.tkw.thinmp.util.GlideUtil;
import tokyo.tkw.thinmp.view.ResponsiveTextView;

public class AlbumDetailActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_album_detail);

        initWithPermissionCheck();
    }

    @Override
    protected void init() {
        // albumId
        String albumId = getIntent().getStringExtra(Album.ALBUM_ID);

        // view
        RecyclerView listView = findViewById(R.id.list);
        ImageView albumArtView = findViewById(R.id.albumArt);
        ResponsiveTextView titleView = findViewById(R.id.title);
        ResponsiveTextView subTitleView = findViewById(R.id.subTitle);
        ImageView menuView = findViewById(R.id.menu);

        // logic
        AlbumDetailLogic logic = AlbumDetailLogic.createInstance(this, albumId);

        // dto
        AlbumDetailDto dto = logic.createDto();

        // アルバム名
        titleView.setText(dto.albumName);

        // アーティスト名
        subTitleView.setText(dto.artistName);

        // アルバムアート
        GlideUtil.bitmap(dto.albumArtId, albumArtView);

        // adapter
        AlbumTrackListAdapter adapter = new AlbumTrackListAdapter(
                dto.trackList,
                new TrackClickListener(dto.trackList)
        );
        listView.setAdapter(adapter);

        // layout
        LinearLayoutManager layout = new LinearLayoutManager(this);
        listView.setLayoutManager(layout);

        // event
        menuView.setOnClickListener(new AlbumMenuClickListener(albumId));
    }
}
