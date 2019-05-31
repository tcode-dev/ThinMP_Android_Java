package tokyo.tkw.thinmp.listener;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import tokyo.tkw.thinmp.activity.AlbumDetailActivity;

public class AlbumClickListener implements View.OnClickListener {
    private Context mContext;
    private String mAlbumId;

    public AlbumClickListener(Context context, String albumId) {
        mContext = context;
        mAlbumId = albumId;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(mContext, AlbumDetailActivity.class);
        intent.putExtra("albumId", mAlbumId);
        mContext.startActivity(intent);
    }
}
