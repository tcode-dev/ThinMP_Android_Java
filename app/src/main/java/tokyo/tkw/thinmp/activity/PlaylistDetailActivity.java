package tokyo.tkw.thinmp.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.dto.PlaylistDetailDto;
import tokyo.tkw.thinmp.epoxy.controller.PlaylistDetailController;
import tokyo.tkw.thinmp.listener.PlaylistMenuClickListener;
import tokyo.tkw.thinmp.listener.ScreenUpdateListener;
import tokyo.tkw.thinmp.logic.PlaylistDetailLogic;
import tokyo.tkw.thinmp.realm.PlaylistTrackRealm;
import tokyo.tkw.thinmp.util.GlideUtil;
import tokyo.tkw.thinmp.view.ResponsiveTextView;

public class PlaylistDetailActivity extends BaseActivity implements ScreenUpdateListener {
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

        // logic
        PlaylistDetailLogic logic = PlaylistDetailLogic.createInstance(this, playlistId);

        // dto
        logic.createDto().ifPresentOrElse(this::showDetail, this::notFound);
    }

    private void showDetail(PlaylistDetailDto dto) {
        // view
        RecyclerView listView = findViewById(R.id.list);
        ImageView albumArtView = findViewById(R.id.albumArt);
        ResponsiveTextView titleView = findViewById(R.id.title);
        ResponsiveTextView subTitleView = findViewById(R.id.subTitle);
        ImageView menuView = findViewById(R.id.menu);

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

    private void notFound() {
        setContentView(R.layout.activity_not_found);

        TextView textView = findViewById(R.id.text);
        textView.setText(getString(R.string.playlist_not_found));

        // 別アクティビティから戻ってきたときにfitsSystemWindowsを効かせる
        ViewCompat.requestApplyInsets(findViewById(R.id.main));
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        screenUpdate();
    }

    @Override
    public void screenUpdate() {
        // view
        ImageView albumArtView = findViewById(R.id.albumArt);
        ResponsiveTextView titleView = findViewById(R.id.title);

        // logic
        PlaylistDetailLogic logic = PlaylistDetailLogic.createInstance(this, playlistId);

        // dto
        logic.createDto().ifPresentOrElse(dto -> {
            // アルバムアート
            GlideUtil.bitmap(dto.albumArtId, albumArtView);

            // プレイリスト名
            titleView.setText(dto.playlistName);

            // controller
            controller.setData(dto.trackList);
        }, this::notFound);
    }
}
