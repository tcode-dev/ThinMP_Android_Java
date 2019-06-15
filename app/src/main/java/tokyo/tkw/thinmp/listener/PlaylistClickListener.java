package tokyo.tkw.thinmp.listener;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import tokyo.tkw.thinmp.activity.PlaylistDetailActivity;

public class PlaylistClickListener implements View.OnClickListener {
    private int mPlaylistId;

    public PlaylistClickListener(int playlistId) {
        mPlaylistId = playlistId;
    }

    @Override
    public void onClick(View v) {
        Context context = v.getContext();
        Intent intent = new Intent(context, PlaylistDetailActivity.class);
        intent.putExtra("playlistId", mPlaylistId);
        context.startActivity(intent);
    }
}
