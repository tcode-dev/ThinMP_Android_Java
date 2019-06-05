package tokyo.tkw.thinmp.activity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.epoxy.controller.ArtistDetailController;
import tokyo.tkw.thinmp.music.Artist;
import tokyo.tkw.thinmp.music.MusicList;
import tokyo.tkw.thinmp.util.GlideUtil;
import tokyo.tkw.thinmp.view.ResponsiveTextView;

import static android.net.Uri.parse;

public class ArtistDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_detail);

        String artistId = getIntent().getStringExtra("artistId");

        // artist取得
        Artist artist = MusicList.getArtist(artistId);

        Uri uri = parse("content://media/external/audio/albumart/" + artist.getThumbnailIdList().get(0));

        // 背景画像
        ImageView backgroundView = findViewById(R.id.background);
        Glide.with((Context) this).asBitmap().load(uri).into(backgroundView);

        // サムネイル
        ImageView thumbnailView = findViewById(R.id.thumbnail);
        GlideUtil.thumbnail(this, artist.getThumbnailIdList().get(0), thumbnailView);

        // タイトル
        ResponsiveTextView titleView = findViewById(R.id.title);
        titleView.setText(artist.getName());

        // recyclerView
        RecyclerView recyclerView = findViewById(R.id.list);
        GridLayoutManager layout = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layout);

        // epoxy
        ArtistDetailController controller = new ArtistDetailController();
        recyclerView.setAdapter(controller.getAdapter());
        controller.setSpanCount(2);
        layout.setSpanSizeLookup(controller.getSpanSizeLookup());

        ArtistDetailController.Data data = new ArtistDetailController.Data();
        data.albums = getResources().getString(R.string.albums);
        data.albumList = MusicList.getArtistAlbumList(artistId);
        data.songs = getResources().getString(R.string.songs);
        data.trackList = MusicList.getArtistTrackList(artistId);
        data.titleSpanSize = 2;
        data.albumListSpanSize = 1;
        data.trackListSpanSize = 2;

        controller.setData(data, this);
    }
}
