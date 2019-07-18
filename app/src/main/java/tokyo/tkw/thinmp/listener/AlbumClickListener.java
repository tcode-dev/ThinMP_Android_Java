package tokyo.tkw.thinmp.listener;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import tokyo.tkw.thinmp.activity.AlbumDetailActivity;
import tokyo.tkw.thinmp.album.Album;

public class AlbumClickListener implements View.OnClickListener {
    private String mAlbumId;

    public AlbumClickListener(String albumId) {
        mAlbumId = albumId;
    }

    @Override
    public void onClick(View v) {
        Context context = v.getContext();
        Intent intent = new Intent(context, AlbumDetailActivity.class);
        intent.putExtra(Album.ALBUM_ID, mAlbumId);
        context.startActivity(intent);
    }
}
