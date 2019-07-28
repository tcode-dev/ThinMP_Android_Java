package tokyo.tkw.thinmp.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.annimon.stream.Optional;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.artist.Artist;
import tokyo.tkw.thinmp.dto.ArtistDetailDto;
import tokyo.tkw.thinmp.epoxy.controller.ArtistDetailController;
import tokyo.tkw.thinmp.listener.ArtistMenuClickListener;
import tokyo.tkw.thinmp.listener.ScreenUpdateListener;
import tokyo.tkw.thinmp.logic.ArtistDetailLogic;
import tokyo.tkw.thinmp.util.GlideUtil;
import tokyo.tkw.thinmp.view.ResponsiveTextView;

public class ArtistDetailActivity extends BaseActivity implements ScreenUpdateListener {
    String artistId;
    ArtistDetailController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initWithPermissionCheck();
    }

    @Override
    protected void init() {
        // artistId
        artistId = getIntent().getStringExtra(Artist.ARTIST_ID);

        // logic
        ArtistDetailLogic logic = ArtistDetailLogic.createInstance(this, artistId);

        // dto
        logic.createDto().ifPresentOrElse(this::showDetail, this::notFound);
    }

    private void showDetail(ArtistDetailDto dto) {
        setContentView(R.layout.activity_artist_detail);

        // view
        ImageView backgroundView = findViewById(R.id.background);
        ImageView albumArtView = findViewById(R.id.albumArt);
        ResponsiveTextView titleView = findViewById(R.id.title);
        TextView metaView = findViewById(R.id.meta);
        RecyclerView listView = findViewById(R.id.list);
        ImageView menuView = findViewById(R.id.menu);

        // 背景画像
        Optional.ofNullable(dto.albumArtId).ifPresent(albumArtId -> {
            GlideUtil.bitmap(dto.albumArtId, backgroundView, GlideUtil.ARTIST_RESOURCE_ID);
        });

        // アルバムアート
        GlideUtil.bitmap(dto.albumArtId, albumArtView, GlideUtil.ARTIST_RESOURCE_ID);

        // タイトル
        titleView.setText(dto.artistName);

        // meta
        metaView.setText(dto.meta);

        // controller
        controller = new ArtistDetailController();
        controller.setData(dto);
        listView.setAdapter(controller.getAdapter());

        // layout
        GridLayoutManager layout = new GridLayoutManager(this, dto.layoutSpanSize);
        controller.setSpanCount(dto.layoutSpanSize);
        layout.setSpanSizeLookup(controller.getSpanSizeLookup());
        listView.setLayoutManager(layout);

        // event
        menuView.setOnClickListener(new ArtistMenuClickListener(artistId));
    }

    private void notFound() {
        setContentView(R.layout.activity_artist_detail_not_found);
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
        ArtistDetailLogic logic = ArtistDetailLogic.createInstance(this, artistId);
        logic.createDto().ifPresentOrElse((dto) -> controller.setData(dto), this::notFound);
    }
}
