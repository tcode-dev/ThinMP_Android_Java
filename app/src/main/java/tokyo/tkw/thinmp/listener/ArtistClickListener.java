package tokyo.tkw.thinmp.listener;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import tokyo.tkw.thinmp.activity.ArtistDetailActivity;
import tokyo.tkw.thinmp.artist.Artist;

public class ArtistClickListener implements View.OnClickListener {
    private String artistId;

    public ArtistClickListener(String artistId) {
        this.artistId = artistId;
    }

    @Override
    public void onClick(View v) {
        Context context = v.getContext();
        Intent intent = new Intent(context, ArtistDetailActivity.class);
        intent.putExtra(Artist.ARTIST_ID, artistId);
        context.startActivity(intent);
    }
}
