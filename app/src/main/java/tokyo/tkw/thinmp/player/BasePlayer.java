package tokyo.tkw.thinmp.player;

/**
 * Created by tk on 2018/03/19.
 * 再生開始前でも使用できる機能
 */

public class BasePlayer {
    // 音量
    private final int VOLUME_MIN  = 0;
    private final int VOLUME_MAX  = 100;

    // ミュート
    private final boolean MUTE_ON = true;
    private final boolean MUTE_OFF = false;

    // リピート
    private final boolean REPEAT_ON = true;
    private final boolean REPEAT_OFF = false;
    private final int REPEAT_ONE = 1;
    private final int REPEAT_ALL = 2;

    // シャッフル
    private final boolean SHUFFLE_ON  = true;
    private final boolean SHUFFLE_OFF = false;

    // 音量
    protected int mVolume = 100;
    protected boolean isMute;

    // リピート
    protected boolean isRepeat;
    protected int repeatMode;

    // シャッフル
    private boolean isShuffle;

//    public BasePlayer(int mVolume, boolean isMute, boolean isRepeat, int repeatMode, boolean isShuffle) {
//        this.mVolume = mVolume;
//        this.isMute = isMute;
//        this.isRepeat = isRepeat;
//        this.repeatMode = repeatMode;
//        this.isShuffle = isShuffle;
//    }

    public void getConfig(){

    }

    /**
     * 音量変更
     */
    public void changeVolume(int volume) {
        mVolume = volume;
    }

    /**
     * 1曲リピートか
     * @return
     */
    public boolean isRepeatOne() {
        return repeatMode == REPEAT_ONE;
    }

    /**
     * 全曲リピートか
     * @return
     */
    public boolean isRepeatAll() {
        return repeatMode == REPEAT_ALL;
    }
}
