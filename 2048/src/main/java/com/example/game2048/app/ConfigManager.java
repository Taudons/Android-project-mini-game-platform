package com.example.game2048.app;

import android.content.Context;
import android.content.SharedPreferences;


public class ConfigManager {

    //保存最高分
    public static void putBestScore(Context context, int bestScore) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                Config.SAVE_BEST_SCORE, Context.MODE_PRIVATE).edit();
        editor.putInt(Config.KEY_BEST_SCORE_WITHIN_INFINITE, bestScore).apply();
    }

    // 获取最高分
    public static int getBestScore(Context context) {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(Config.SAVE_BEST_SCORE, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(Config.KEY_BEST_SCORE_WITHIN_INFINITE, 0);
    }

    // 保存当前分数
    public static void putCurrentScore(Context context, int currentScore) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                Config.SAVE_CURRENT_SCORE_INFINITE, Context.MODE_PRIVATE).edit();
        editor.putInt("KEY_CURRENT_SCORE_INFINITE", currentScore).apply();
    }

    // 获取当前分数
    public static int getCurrentScore(Context context) {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(Config.SAVE_CURRENT_SCORE_INFINITE, Context.MODE_PRIVATE);
        return sharedPreferences.getInt("KEY_CURRENT_SCORE_INFINITE", 0);
    }


    // 保存游戏音效状态
    public static void putGameVolume(Context context, boolean volumeState) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                Config.SAVE_GAME_VOLUME_STATE, Context.MODE_PRIVATE).edit();
        editor.putBoolean(Config.KEY_GAME_VOLUME_STATE, volumeState).apply();
    }

    //获取游戏音效状态
    static boolean getGameVolumeState(Context context) {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(Config.SAVE_GAME_VOLUME_STATE, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(Config.KEY_GAME_VOLUME_STATE, true);
    }

    //保存达成游戏目标次数
    public static void putGoalTime(Context context, int time) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                Config.SAVE_GET_GOAL_TIME, Context.MODE_PRIVATE).edit();
        editor.putInt(Config.KEY_GET_GOAL_TIME, time).apply();
    }

    // 获取达成游戏目标次数
    public static int getGoalTime(Context context) {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(Config.SAVE_GET_GOAL_TIME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(Config.KEY_GET_GOAL_TIME, 0);
    }
}
