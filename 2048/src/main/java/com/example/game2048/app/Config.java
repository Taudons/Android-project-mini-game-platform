package com.example.game2048.app;

import android.app.Application;

public class Config extends Application {


    //保存最高分的SharedPreferences
    public static final String SAVE_BEST_SCORE = "SAVE_BEST_SCORE";

    //保存当前得分的SharedPreferences
    public static final String SAVE_CURRENT_SCORE_INFINITE = "SAVE_CURRENT_SCORE";

    //保存游戏音效状态的SharedPreferences
    public static final String SAVE_GAME_VOLUME_STATE = "SAVE_GAME_VOLUME_STATE";

    //保存达成游戏目标次数的SharedPreferences
    public static final String SAVE_GET_GOAL_TIME = "SAVE_GET_GOAL_TIME";

    //SharedPreferences保存最高分的KEY
    public static final String KEY_BEST_SCORE_WITHIN_INFINITE = "KEY_BEST_SCORE_WITHIN_INFINITE";

    //SharedPreferences保存游戏音效状态的KEY
    public static final String KEY_GAME_VOLUME_STATE = "KEY_GAME_VOLUME_STATE";

    //SharedPreferences保存达成游戏目标的KEY
    public static final String KEY_GET_GOAL_TIME = "KEY_GET_GOAL_TIME";

    //最高分
    public static int BestScore;

    //游戏音效状态(默认打开)
    public static boolean VolumeState = true;

    //达成游戏目标次数(默认0)
    public static int GetGoalTime = 0;

    public static final String DB_NAME = "GAME2048.db";

    @Override
    public void onCreate() {
        super.onCreate();
        // 获取到最高分
        BestScore = ConfigManager.getBestScore(this);
        // 获取游戏音效状态
        VolumeState = ConfigManager.getGameVolumeState(this);

    }
}
