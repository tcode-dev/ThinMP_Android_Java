package tokyo.tkw.thinmp.listener;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import tokyo.tkw.thinmp.activity.PlaylistDetailActivity;

public class PlaylistClickListener implements View.OnClickListener {
    private Activity mContext;
    private int mPlaylistId;

    public PlaylistClickListener(Activity context, int playlistId) {
        mContext = context;
        mPlaylistId = playlistId;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(mContext, PlaylistDetailActivity.class);
        intent.putExtra("playlistId", mPlaylistId);
        mContext.startActivity(intent);
    }
}
