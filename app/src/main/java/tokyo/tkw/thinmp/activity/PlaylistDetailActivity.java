package tokyo.tkw.thinmp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.adapter.PlaylistDetailAdapter;
import tokyo.tkw.thinmp.dto.PlaylistDetailActivityDto;
import tokyo.tkw.thinmp.listener.PlaylistTrackClickListener;
import tokyo.tkw.thinmp.logic.PlaylistDetailActivityLogic;
import tokyo.tkw.thinmp.playlist.Playlist;
import tokyo.tkw.thinmp.realm.PlaylistTrackRealm;
import tokyo.tkw.thinmp.util.GlideUtil;
import tokyo.tkw.thinmp.view.ResponsiveTextView;

public class PlaylistDetailActivity extends BaseActivity {
    private int playlistId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_playlist_detail);

        initWithPermissionCheck();
    }

    @Override
    protected void init() {
        playlistId = getIntent().getIntExtra(PlaylistTrackRealm.PLAYLIST_ID, 0);

        // view
        RecyclerView listView = findViewById(R.id.list);
        ImageView albumArtView = findViewById(R.id.albumArt);
        ResponsiveTextView titleView = findViewById(R.id.title);
        ResponsiveTextView subTitleView = findViewById(R.id.subTitle);
        Button editView = findViewById(R.id.edit);

        // logic
        PlaylistDetailActivityLogic logic = PlaylistDetailActivityLogic.createInstance(
                this,
                playlistId
        );

        // dto
        PlaylistDetailActivityDto dto = logic.createDto();

        // アルバムアート
        GlideUtil.bitmap(dto.albumArt, albumArtView);

        // プレイリスト名
        titleView.setText(dto.playlistName);

        // playlist文字列
        subTitleView.setText(dto.typeName);

        // adapter
        PlaylistDetailAdapter adapter = new PlaylistDetailAdapter(
                dto.playlistTrackRealms,
                dto.trackMap,
                new PlaylistTrackClickListener(playlistId)
        );
        listView.setAdapter(adapter);

        // layout
        LinearLayoutManager layout = new LinearLayoutManager(this);
        listView.setLayoutManager(layout);

        // event
        editView.setOnClickListener(createEditClickListener());
    }

    private View.OnClickListener createEditClickListener() {
        return view -> {
            Context context = view.getContext();
            Intent intent = new Intent(context, PlaylistDetailEditActivity.class);
            intent.putExtra(Playlist.PLAYLIST_ID, playlistId);
            context.startActivity(intent);
        };
    }
}
