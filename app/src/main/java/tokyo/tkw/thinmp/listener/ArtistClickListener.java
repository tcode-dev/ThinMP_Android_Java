package tokyo.tkw.thinmp.listener;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import tokyo.tkw.thinmp.activity.ArtistActivity;

public class ArtistClickListener implements View.OnClickListener {
    private Activity mContext;
    private String mArtistId;

    public ArtistClickListener(Activity context, String artistId) {
        mContext = context;
        mArtistId = artistId;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(mContext, ArtistActivity.class);
        intent.putExtra("artist_id", mArtistId);
        mContext.startActivity(intent);
    }
}
