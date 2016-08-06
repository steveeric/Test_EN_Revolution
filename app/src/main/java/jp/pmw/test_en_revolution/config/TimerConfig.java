package jp.pmw.test_en_revolution.config;

/**
 * Created by si on 2016/01/29.
 * タイマータスクで使用する
 * 設定時間を管理するクラス.
 */
public class TimerConfig {
    //  送信状態タスクを開始する時間[msec]
    public static final long TIMER_START_TRANSMIT_STATE = 0;
    //  送信状態タスクを確認する間隔[msec]
    public static final long TIMER_INTERVAL_TRANSMIT_STATE = 10000;
    //  受講者の出席状況のみを確認する間隔[msec]
    public static final long TIMER_INTERVAL_TIME_ATTENDANCE = 3000;

    //  クリッカー(アンケート)を開始するまでの時間[msec]
    public static final long TIMER_START_LAST_QUESTION_BMP_TRANSMIT_LOG = 0;
    //  クリッカー(アンケート)で最後に送信を行った送信所歌とNACK数を取得する時間間隔[msec]
    public static final long TIMER_INTERVAL_LAST_QUESTION_BMP_TRANSMIT_LOG = 3000;
}
