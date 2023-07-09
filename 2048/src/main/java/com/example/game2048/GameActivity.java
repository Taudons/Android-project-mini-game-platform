package com.example.game2048;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import com.example.game2048.app.Config;
import com.example.game2048.app.ConfigManager;
import com.example.game2048.db.CellEntity;
import com.example.game2048.db.GameDatabaseHelper;
import com.example.game2048.view.CommonDialog;
import com.example.game2048.view.SetDialog;
import com.example.game2048.view.GameOverDialog;
import com.example.game2048.view.GameView;


public class GameActivity extends AppCompatActivity {
    private Button reset;
    private Button menu;
    private GameView gameView;
    private BroadcastReceiver myReceiver;
    private SetDialog setdialog;
    private GestureOverlayView mGestureOverlayView;
    private GameDatabaseHelper gameDatabaseHelper;
    private SQLiteDatabase db;
    private TextView currentScores;
    private TextView bestScores;
    public static boolean newscore=false;
    private boolean isNeedSave = true;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        initView();
        initData();
        initGesture();
        //若没有继续游戏，则重新开始
        if(!MainActivity.Re_or_Con){
            gameView.resetGame();
            // 重置分数
            currentScores.setText("0");
            saveCurrentScore(0);
            deleteCache("G_INFINITE");
        }
    }

    private Timer timer;
    @Override
    protected void onResume() {
        super.onResume();

        if (null == timer) {
            timer = new Timer();
            startTiming();
        }
    }

    private void startTiming() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (isNeedSave) {
                    // 10S保存一次游戏进度
                    saveGameProgress();
                }
            }
        }, 5 * 1000, 10 * 1000);
    }

    @Override
    protected void onDestroy() {
        // 取消注册广播
        if (myReceiver != null) {
            unregisterReceiver(myReceiver);
            myReceiver = null;
        }
        // 移除监听器
        mGestureOverlayView.removeAllOnGestureListeners();

        if (null != timer) {
            timer.cancel();
            timer.purge();
            timer = null;
        }
        if (null != gameDatabaseHelper) {
            gameDatabaseHelper.close();
            gameDatabaseHelper = null;
        }
        super.onDestroy();
    }

    private void initView() {
        currentScores = findViewById(R.id.tv_current_score);
        bestScores = findViewById(R.id.tv_best_score);
        reset = findViewById(R.id.btn_reset);
        menu = findViewById(R.id.btn_option);
        mGestureOverlayView = findViewById(R.id.gesture_overlay_view);
        gameView = findViewById(R.id.game_view);

        bestScores.setText(String.valueOf(ConfigManager.getBestScore(this)));
        currentScores.setText(String.valueOf(ConfigManager.getCurrentScore(this)));
        gameView.initView();
    }

    //初始化数据
    private void initData() {
        // 注册广播
        myReceiver = new MyReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(GameView.ACTION_RECORD_SCORE);
        filter.addAction(GameView.ACTION_WIN);
        filter.addAction(GameView.ACTION_LOSE);
        registerReceiver(myReceiver, filter);

        newscore=false;

        // 重置按钮，重新开始游戏
        reset.setOnClickListener(v -> showConfirmDialog());
        // 打开菜单
        menu.setOnClickListener(v -> showConfigDialog());

        gameDatabaseHelper = new GameDatabaseHelper(this, Config.DB_NAME, null, 1);
        db = gameDatabaseHelper.getWritableDatabase();
    }

    //初始化手势
    private void initGesture() {

            // 定义手势库
            GestureLibrary library = GestureLibraries.fromRawResource(this, R.raw.gestures);
            // 设置手势监听（手势绘制完成调用）
            mGestureOverlayView.addOnGesturePerformedListener((overlay, gesture) -> {
                // 加载手势库成功
                if (library.load()) {
                    // 从手势库中查找所有匹配的手势
                    ArrayList<Prediction> predictions = library.recognize(gesture);
                }
            });

    }

    //打开重新开始确认对话框
    private void showConfirmDialog() {
        CommonDialog dialog = new CommonDialog(this, R.style.CustomDialog);
        dialog.setCancelable(false);
        dialog.setTitle(getResources().getString(R.string.tip))
                .setMessage(getResources().getString(R.string.tip_reset_btn))
                .setOnNegativeClickListener("", v -> dialog.cancel())
                .setOnPositiveClickedListener("", v -> {
                    gameView.resetGame();
                    // 重置分数
                    currentScores.setText("0");
                    saveCurrentScore(0);
                    deleteCache("G_INFINITE");
                    dialog.cancel();
                }).show();
    }

    //打开设置对话框
    private void showConfigDialog() {
        setdialog = new SetDialog(this, R.style.CustomDialog);
        setdialog.setOnNegativeClickListener(v -> setdialog.cancel())
                .setOnPositiveClickListener(v -> onDialogConfirm())
                .show();
    }


    //设置对话框的确认按钮监听
    private void onDialogConfirm() {
        // 获取音效状态
        boolean volumeState = setdialog.getVolumeState();
            // 判断音效配置是否更改
            if (volumeState != Config.VolumeState) {
                // 保存音效设置
                ConfigManager.putGameVolume(this, volumeState);
                Config.VolumeState = volumeState;
            }
            setdialog.cancel();
        changeConfiguration(setdialog,  volumeState);
    }

    //更改游戏配置
    private void changeConfiguration(SetDialog dialog, boolean volumeState) {
        Config.VolumeState = volumeState;
//        gameView.initView();
        bestScores.setText(String.valueOf(ConfigManager.getBestScore(this)));
        // 保存音效设置
        ConfigManager.putGameVolume(this, volumeState);
        dialog.cancel();
    }

    //记录得分
    private void recordScore(int score) {
        currentScores.setText(String.valueOf(score));
        // 分数大于最高分时
        if (score > ConfigManager.getBestScore(this)) {
            updateBestScore(score);
            //标记新纪录
            newscore=true;
        }
    }

    //更新最高分
    private void updateBestScore(int newScore) {
        bestScores.setText(String.valueOf(newScore));
        Config.BestScore = newScore;
        ConfigManager.putBestScore(this, newScore);
    }

    //自定义广播类
    private class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            assert action != null;
            if (action.equals(GameView.ACTION_RECORD_SCORE)) {
                // 获取分数
                int score = intent.getIntExtra(GameView.KEY_SCORE, 0);
                // 获取历史分数
                int historyScore = Integer.parseInt(currentScores.getText().toString());
                saveCurrentScore(score + historyScore);
                recordScore(score + historyScore);
                // 游戏结束
            } else if (action.equals(GameView.ACTION_WIN)
                    || action.equals(GameView.ACTION_LOSE)) {
                // 清除缓存
                isNeedSave = false;
                deleteCache("G_INFINITE");
                saveCurrentScore(0);

                String result = intent.getStringExtra(GameView.KEY_RESULT);
                GameOverDialog dialog = new GameOverDialog(GameActivity.this, R.style.CustomDialog);
                dialog.setCancelable(false);
                new Handler().postDelayed(() ->
                        dialog.setFinalScore(currentScores.getText().toString())
                                .setTitle(result)
                                .setOnGoOnClickListener(v -> {
                                    // 清除缓存
                                    isNeedSave = true;
                                    gameView.reset();
                                    deleteCache("G_INFINITE");
                                    saveCurrentScore(0);

                                    gameView.initView();
                                    currentScores.setText("0");
                                    dialog.cancel();
                                }).show(), 666);
            }
        }
    }

    private void saveCurrentScore(int score) {
        ConfigManager.putCurrentScore(GameActivity.this, score);
    }

    //退出时保存
    @Override
    public void onBackPressed() {
        saveGameProgress();
        finish();
    }

    //保留游戏进度
    private void saveGameProgress() {
        String tableName = "G_INFINITE";
        deleteCache(tableName);
        // 保存新的数据
        ArrayList<CellEntity> data = gameView.getCurrentProcess();
        if (data.size() > 2) {
            ContentValues values = new ContentValues();
            for (CellEntity cell : data) {
                values.put("x", cell.getX());
                values.put("y", cell.getY());
                values.put("num", cell.getNum());
                db.insert(tableName, null, values);
                values.clear();
            }
        }
    }

    //清空缓存
    private void deleteCache(String tableName) {
        db.execSQL("delete from " + tableName);
    }

}
