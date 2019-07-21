package tokyo.tkw.thinmp.activity;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.adapter.PlaylistDetailAdapter;
import tokyo.tkw.thinmp.dto.PlaylistDetailDto;
import tokyo.tkw.thinmp.epoxy.controller.PlaylistDetailController;
import tokyo.tkw.thinmp.listener.PlaylistMenuClickListener;
import tokyo.tkw.thinmp.listener.PlaylistTrackClickListener;
import tokyo.tkw.thinmp.logic.PlaylistDetailLogic;
import tokyo.tkw.thinmp.logic.PlaylistsLogic;
import tokyo.tkw.thinmp.realm.PlaylistTrackRealm;
import tokyo.tkw.thinmp.util.GlideUtil;
import tokyo.tkw.thinmp.view.ResponsiveTextView;

public class PlaylistDetailActivity extends BaseActivity {
    private String playlistId;
    private PlaylistDetailController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_playlist_detail);

        initWithPermissionCheck();
    }

    @Override
    protected void init() {
        // playlistId
        playlistId = getIntent().getStringExtra(PlaylistTrackRealm.PLAYLIST_ID);

        // view
        RecyclerView listView = findViewById(R.id.list);
        ImageView albumArtView = findViewById(R.id.albumArt);
        ResponsiveTextView titleView = findViewById(R.id.title);
        ResponsiveTextView subTitleView = findViewById(R.id.subTitle);
        ImageView menuView = findViewById(R.id.menu);

        // logic
        PlaylistDetailLogic logic = PlaylistDetailLogic.createInstance(this, playlistId);

        // dto
        PlaylistDetailDto dto = logic.createDto();

        // アルバムアート
        GlideUtil.bitmap(dto.albumArtId, albumArtView);

        // プレイリスト名
        titleView.setText(dto.playlistName);

        // playlist文字列
        subTitleView.setText(dto.typeName);

        // controller
        controller = new PlaylistDetailController();
        controller.setData(dto.trackList);
        listView.setAdapter(controller.getAdapter());

        // layout
        LinearLayoutManager layout = new LinearLayoutManager(this);
        listView.setLayoutManager(layout);

        // event
        menuView.setOnClickListener(new PlaylistMenuClickListener(playlistId));
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        update();
    }

    private void update() {
        // view
        ImageView albumArtView = findViewById(R.id.albumArt);
        ResponsiveTextView titleView = findViewById(R.id.title);

        // logic
        PlaylistDetailLogic logic = PlaylistDetailLogic.createInstance(this, playlistId);

        // dto
        PlaylistDetailDto dto = logic.createDto();

        // アルバムアート
        GlideUtil.bitmap(dto.albumArtId, albumArtView);

        // プレイリスト名
        titleView.setText(dto.playlistName);

        // controller
        controller.setData(dto.trackList);
    }
}
