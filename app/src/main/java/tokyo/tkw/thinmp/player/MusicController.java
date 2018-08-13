package tokyo.tkw.thinmp.player;

import android.app.Activity;

import java.util.ArrayList;

import tokyo.tkw.thinmp.fragment.PlayerFragment;
import tokyo.tkw.thinmp.model.Track;

public class  MusicController {
    private static MusicPlayer mMusicPlayer;
    private static PlayerFragment mPlayerView;

    public static void set(Activity context, ArrayList<Track> trackList, int position) {
        destroy();

        mMusicPlayer = new MusicPlayer(context, trackList, position);
    }

    public static void setPlayerView(PlayerFragment playerFragment) {
        mPlayerView = playerFragment;
    }

    public static void start() {
        if (! isActive()) return;

        mMusicPlayer.start();

        mPlayerView.update();
    }

    public static void play() {
        if (! isActive()) return;

        mMusicPlayer.play();
    }

    public static void pause() {
        if (! isActive()) return;

        mMusicPlayer.pause();
    }

    public static void next() {
        if (! isActive()) return;

        if (isPlaying()) {
            mMusicPlayer.nextPlay();
        } else {
            mMusicPlayer.next();
        }
    }

    public static void prev() {
        if (! isActive()) return;

        if (isPlaying()) {
            mMusicPlayer.prevPlay();
        } else {
            mMusicPlayer.prev();
        }
    }

    public static boolean isActive() {
        return mMusicPlayer != null;
    }

    public static boolean isPlaying(){
        if (! isActive()) return false;

        return mMusicPlayer.isPlaying();
    }

    public static void destroy() {
        if (! isActive()) return;

        mMusicPlayer.destroy();
        mMusicPlayer = null;
    }
}
