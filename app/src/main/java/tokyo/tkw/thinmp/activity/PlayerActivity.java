package tokyo.tkw.thinmp.activity;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.databinding.ActivityPlayerBinding;
import tokyo.tkw.thinmp.music.Track;
import tokyo.tkw.thinmp.player.MusicManager;
import tokyo.tkw.thinmp.player.Player;

public class PlayerActivity extends AppCompatActivity {
    private ActivityPlayerBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Track track = MusicManager.getTrack();
        ActivityPlayerBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_player);
        Player player = new Player(binding);
        binding.setPlayer(player);
        player.update(track);

        if (MusicManager.isPlaying()) {
            player.setDurationTimer();
        }

        mBinding = binding;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
