package tokyo.tkw.thinmp.player;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

import java.util.ArrayList;

import tokyo.tkw.thinmp.favorite.FavoriteArtistRegister;
import tokyo.tkw.thinmp.favorite.FavoriteSongRegister;
import tokyo.tkw.thinmp.music.PlayingList;
import tokyo.tkw.thinmp.music.Track;
import tokyo.tkw.thinmp.provider.ConfigProvider;
import tokyo.tkw.thinmp.util.ActivityUtil;

public class MusicService extends Service {
    private final int REPEAT_OFF = 0;
    private final int REPEAT_ONE = 1;
    private final int REPEAT_ALL = 2;
    private final int PREV_MS = 3000;

    public IBinder mBinder = new MusicBinder();
    private int mRepeat;
    private boolean mShuffle;
    private MediaPlayer mMediaPlayer;
    private ArrayList<Track> mOriginalList;
    private PlayingList mPlayingList;
    private OnMusicServiceListener mListener;
    /**
     * 再生が終わったあとの処理
     */
    private MediaPlayer.OnCompletionListener onCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            mListener.onFinished();

            if (!hasNext()) {
                return;
            }

            if (mRepeat != REPEAT_ONE) {
                mPlayingList.next();
            }

            start();
        }
    };

    public MusicService() {
        ConfigProvider configProvider = new ConfigProvider(ActivityUtil.getContext());

        mRepeat = configProvider.getRepeat();
        mShuffle = configProvider.getShuffle();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    /**
     * 再生リストを登録
     *
     * @param playList
     * @param position
     */
    public void setPlayingList(ArrayList<Track> playList, int position) {
        mOriginalList = playList;
        mPlayingList = new PlayingList(playList, position);
    }

    /**
     * リスナーを登録
     *
     * @param listener
     */
    public void setListener(OnMusicServiceListener listener) {
        mListener = listener;
    }

    /**
     * リスナーを解除
     */
    public void unsetListener() {
        mListener = null;
    }

    /**
     * 再生を開始
     */
    public void start() {
        destroy();

        Track track = mPlayingList.getTrack();
        mMediaPlayer = MediaPlayer.create(ActivityUtil.getContext(), track.getUri());
        mMediaPlayer.start();
        mMediaPlayer.setOnCompletionListener(onCompletionListener);

        mListener.onChangeTrack(track);
        mListener.onStarted();
    }

    /**
     * 再生を再開
     */
    public void play() {
        mMediaPlayer.start();
    }

    /**
     * 一時停止
     */
    public void pause() {
        mMediaPlayer.pause();
    }

    /**
     * seek
     */
    public void seekTo(int msec) {
        mMediaPlayer.seekTo(msec);
    }

    /**
     * 現在の再生位置を取得
     *
     * @return ミリ秒
     */
    public int getCurrentPosition() {
        return mMediaPlayer.getCurrentPosition();
    }

    /**
     * リピート
     */
    public Integer repeat() {
        switch (mRepeat) {
            case REPEAT_OFF:
                repeatAll();
                break;
            case REPEAT_ALL:
                repeatOne();
                break;
            case REPEAT_ONE:
                repeatOff();
                break;
        }

        // 設定を保存
        ConfigProvider configProvider = new ConfigProvider(ActivityUtil.getContext());
        configProvider.setRepeat(mRepeat);

        return mRepeat;
    }

    /**
     * 1曲リピート
     */
    private void repeatOne() {
        mRepeat = REPEAT_ONE;
    }

    /**
     * 全曲リピート
     */
    private void repeatAll() {
        mRepeat = REPEAT_ALL;
    }

    /**
     * リピートoff
     */
    private void repeatOff() {
        mRepeat = REPEAT_OFF;
    }

    /**
     * シャッフル
     */
    public boolean shuffle() {
        mShuffle = !mShuffle;
        mPlayingList = mShuffle ? mPlayingList.getShufflePlayingList() : new PlayingList(mOriginalList, mPlayingList.getCurrentPosition());

        // 設定を保存
        ConfigProvider configProvider = new ConfigProvider(ActivityUtil.getContext());
        configProvider.setShuffle(mShuffle);

        return mShuffle;
    }

    /**
     * 現在のtrackを取得
     *
     * @return Track
     */
    public Track getTrack() {
        if (mPlayingList == null) return null;

        return mPlayingList.getTrack();
    }

    /**
     * 前の曲へ
     * 再生時間が3秒を超える場合同じ曲を最初から再生
     */
    public void prev() {
        if (getCurrentPosition() <= PREV_MS) {
            mPlayingList.prev();
        }

        destroy();

        Track track = mPlayingList.getTrack();
        mMediaPlayer = MediaPlayer.create(ActivityUtil.getContext(), track.getUri());
        mMediaPlayer.setOnCompletionListener(onCompletionListener);

        mListener.onChangeTrack(track);
    }

    /**
     * 前の曲へ
     * 再生時間が3秒を超える場合同じ曲を最初から再生
     */
    public void playPrev() {
        if (getCurrentPosition() <= PREV_MS) {
            mPlayingList.prev();
        }
        start();
    }

    /**
     * 前の曲があるか
     */
    public boolean hasPrev() {
        switch (mRepeat) {
            case REPEAT_OFF:
                return mPlayingList.hasPrev();

            case REPEAT_ONE:
                return true;

            case REPEAT_ALL:
                return true;

            default:
                return false;
        }
    }

    /**
     * 次の曲へ
     */
    public void next() {
        if (!hasNext()) {
            return;
        }
        destroy();

        mPlayingList.next();
        Track track = mPlayingList.getTrack();
        mMediaPlayer = MediaPlayer.create(ActivityUtil.getContext(), track.getUri());
        mMediaPlayer.setOnCompletionListener(onCompletionListener);

        mListener.onChangeTrack(track);
    }

    /**
     * 次の曲があるか
     */
    public boolean hasNext() {
        switch (mRepeat) {
            case REPEAT_OFF:
                return mPlayingList.hasNext();

            case REPEAT_ONE:
                return true;

            case REPEAT_ALL:
                return true;

            default:
                return false;
        }
    }

    /**
     * 次の曲の再生
     */
    public void playNext() {
        if (!hasNext()) {
            return;
        }

        mPlayingList.next();
        start();
    }

    /**
     * 再生中か
     *
     * @return
     */
    public boolean isPlaying() {
        return mMediaPlayer.isPlaying();
    }

    /**
     * シャッフルの状態を返す
     *
     * @return
     */
    public boolean getShuffle() {
        return mShuffle;
    }

    /**
     * リピートの状態を返す
     *
     * @return
     */
    public Integer getRepeat() {
        return mRepeat;
    }

    /**
     * MediaPlayerを破棄
     */
    public void destroy() {
        if (mMediaPlayer == null) return;

        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
        }

        mMediaPlayer.release();
        mMediaPlayer = null;
    }

    public MusicState getState() {
        Track track = mPlayingList.getTrack();

        return new MusicState(mMediaPlayer.isPlaying(), getCurrentPosition(), hasPrev(), hasNext(), getRepeat(), getShuffle(), FavoriteSongRegister.exists(track.getId()), FavoriteArtistRegister.exists(track.getArtistId()));
    }

    /**
     * interface
     */
    public interface OnMusicServiceListener {
        /**
         * 曲変更
         */
        void onChangeTrack(Track track);

        /**
         * 再生開始
         */
        void onStarted();

        /**
         * 再生終了
         */
        void onFinished();
    }

    /**
     * bind
     */
    public class MusicBinder extends Binder {
        public MusicService getService() {
            return MusicService.this;
        }
    }
}
