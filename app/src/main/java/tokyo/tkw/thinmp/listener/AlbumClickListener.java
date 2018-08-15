package tokyo.tkw.thinmp.listener;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import tokyo.tkw.thinmp.activity.AlbumActivity;

public class AlbumClickListener implements View.OnClickListener {
    private Activity mContext;
    private String mAlbumId;

    public AlbumClickListener(Activity context, String albumId) {
        mContext = context;
        mAlbumId = albumId;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(mContext, AlbumActivity.class);
        intent.putExtra("album_id", mAlbumId);
        mContext.startActivity(intent);
    }
}
