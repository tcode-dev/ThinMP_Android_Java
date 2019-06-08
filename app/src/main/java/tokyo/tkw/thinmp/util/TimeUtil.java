package tokyo.tkw.thinmp.util;

/**
 * 時間の変換
 */
public class TimeUtil {
    /**
     * ミリ秒を秒に変換する
     *
     * @param millisecond
     * @return
     */
    public static int millisecondToSecond(int millisecond) {
        return millisecond / 1000;
    }

    /**
     * 秒を時刻形式に変換する
     * 1時間以上の場合h:mm:ss
     * 1時間未満の場合m:ss
     * 頻繁に呼ばれるので速度重視
     * String#formatは使わずに一度だけ文字列結合する
     *
     * @param second
     * @return
     */
    public static String secondToTime(int second) {
        int h = second / 3600;
        int m = second / 60;
        int s = second % 60;
        String time;

        if (h >= 1) {
            if (m >= 10) {
                if (s >= 10) {
                    time = h + ":" + m + ":" + s;
                } else {
                    time = h + ":" + m + ":0" + s;
                }
            } else {
                if (s >= 10) {
                    time = h + ":0" + m + ":" + s;
                } else {
                    time = h + ":0" + m + ":0" + s;
                }
            }
        } else {
            if (s >= 10) {
                time = m + ":" + s;
            } else {
                time = m + ":0" + s;
            }
        }

        return time;
    }
}
