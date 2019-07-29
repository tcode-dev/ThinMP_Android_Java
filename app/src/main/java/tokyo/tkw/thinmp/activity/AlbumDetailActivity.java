package tokyo.tkw.thinmp.activity;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.annimon.stream.Optional;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.album.Album;
import tokyo.tkw.thinmp.dto.AlbumDetailDto;
import tokyo.tkw.thinmp.epoxy.controller.AlbumDetailController;
import tokyo.tkw.thinmp.listener.AlbumMenuClickListener;
import tokyo.tkw.thinmp.logic.AlbumDetailLogic;
import tokyo.tkw.thinmp.util.GlideUtil;
import tokyo.tkw.thinmp.view.ResponsiveTextView;

public class AlbumDetailActivity extends BaseActivity {
    String albumId;
    AlbumDetailController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initWithPermissionCheck();
    }

    @Override
    protected void init() {
        // albumId
        albumId = getIntent().getStringExtra(Album.ALBUM_ID);

        // logic
        AlbumDetailLogic logic = AlbumDetailLogic.createInstance(this, albumId);

        // dto
        Optional<AlbumDetailDto> dtoOptional = logic.createDto();

        dtoOptional.ifPresentOrElse(this::showDetail, this::notFound);
    }

    private void showDetail(AlbumDetailDto dto) {
        setContentView(R.layout.activity_album_detail);

        // view
        RecyclerView listView = findViewById(R.id.list);
        ImageView albumArtView = findViewById(R.id.albumArt);
        ResponsiveTextView titleView = findViewById(R.id.title);
        ResponsiveTextView subTitleView = findViewById(R.id.subTitle);
        ImageView menuView = findViewById(R.id.menu);

        // アルバム名
        titleView.setText(dto.albumName);

        // アーティスト名
        subTitleView.setText(dto.artistName);

        // アルバムアート
        GlideUtil.bitmap(dto.albumArtId, albumArtView);

        // controller
        controller = new AlbumDetailController();
        controller.setData(dto.trackList);
        listView.setAdapter(controller.getAdapter());

        // layout
        LinearLayoutManager layout = new LinearLayoutManager(this);
        listView.setLayoutManager(layout);

        // event
        menuView.setOnClickListener(new AlbumMenuClickListener(albumId));
    }

    private void notFound() {
        setContentView(R.layout.activity_album_detail_not_found);
    }
}
