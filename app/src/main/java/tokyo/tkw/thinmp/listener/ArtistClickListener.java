package tokyo.tkw.thinmp.listener;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import tokyo.tkw.thinmp.activity.ArtistDetailActivity;

public class ArtistClickListener implements View.OnClickListener {
    private Context mContext;
    private String mArtistId;

    public ArtistClickListener(Context context, String artistId) {
        mContext = context;
        mArtistId = artistId;
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(mContext, ArtistDetailActivity.class);
        intent.putExtra("artistId", mArtistId);
        mContext.startActivity(intent);
    }
}
