package com.example.shudu;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

public class ShuduView extends View {

  //单元格的宽度和高度
    private float width;
    private float height;
    private static  int selectedX;
    private  static int selectedY;
    private Game game=new Game();
    public ShuduView(Context context) {
        super(context);
    }



    //计算单元格的高度和宽度
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        this.width=w/9f;
        this.height=h/9f;
        super.onSizeChanged(w, h, oldw, oldh);
    }

    //当Android系统需要绘制一个View对象时，就会调用该对象的onDraw
    @Override
    protected void onDraw(Canvas canvas) {
        //生成用于绘制背景色的画笔并绘制
        Paint backgroundpaint=new Paint();
         backgroundpaint.setColor(getResources().getColor(R.color.shudu_background));
         canvas.drawRect(0,0,getWidth(),getHeight(),backgroundpaint);

         Paint darkPaint=new Paint();
         darkPaint.setColor(getResources().getColor(R.color.shudu_dark));

         Paint hillitePaint=new Paint();
         hillitePaint.setColor(getResources().getColor(R.color.shudu_hilite));

         Paint lightPaint=new Paint();
         lightPaint.setColor(getResources().getColor(R.color.shudu_light));
        for (int i = 1; i < 9; i++) {
            //以下两行代码用于绘制横向单元格线
            canvas.drawLine(0,i*height,getWidth(),i*height,lightPaint);
            canvas.drawLine(0,i*height+1,getWidth(),i*height+1,lightPaint);
           //以下两行代码用于绘制纵向单元格线
            canvas.drawLine(i*width,0,i*width,getHeight(),lightPaint);
            canvas.drawLine(i*width+1,0,i*width+1,getHeight(),lightPaint);
         }
        for (int i = 1; i < 9; i++) {
            if(i%3!=0){
                continue;
            }
            canvas.drawLine(0,i*height,getWidth(),i*height,darkPaint);
            canvas.drawLine(0,i*height+1,getWidth(),i*height+1,darkPaint);
            canvas.drawLine(i*width,0,i*width,getHeight(),darkPaint);
            canvas.drawLine(i*width+1,0,i*width+1,getHeight(),darkPaint);
        }
        //绘制数字
        Paint numberPaint=new Paint();
        numberPaint.setColor(Color.BLACK);
       // numberPaint.setStyle(Paint.Style.STROKE);
        numberPaint.setTextSize(height*0.75f);
        numberPaint.setTextAlign(Paint.Align.CENTER);
        Paint.FontMetrics fm=numberPaint.getFontMetrics();
        float x=width/2;
        float y=height/2-(fm.descent+fm.ascent)/2;
        //canvas.drawText("9",4*width+x,height*0.75f,numberPaint);
        //canvas.drawText("9",3*width+x,y,numberPaint);
        for (int i = 0; i < 9; i++) {
            for(int j = 0;j < 9;j++){
                //如果初始数独库数据不为空，则绘制
                if(!game.getTileString(i,j,game.sudoku).equals(""))
                    canvas.drawText(game.getTileString(i,j,game.sudoku),i * width + x,j * height + y,numberPaint);
            }
        }
        //绘制所选数字
        Paint selectPaint = new Paint();
        selectPaint.setColor(Color.BLUE);
      //  selectPaint.setStyle(Paint.Style.STROKE);
        selectPaint.setTextSize(height * 0.75f);
        selectPaint.setTextAlign(Paint.Align.CENTER);

        //画出九宫格的所选数据
        for(int i = 0;i < 9;i++){
            for(int j = 0;j < 9;j++){
                //如果玩家所选数独库数据不为空，则绘制
                if(!game.getTileString(i,j,game.sudoku1).equals(""))
                    canvas.drawText(game.getTileString(i,j,game.sudoku1),i * width + x,j * height + y,selectPaint);
            }
        }
        super.onDraw(canvas);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction()!=MotionEvent.ACTION_DOWN){
            return super.onTouchEvent(event);
        }
        //获取点击格子的X和Y值
        selectedX = (int) (event.getX() / width);
        selectedY = (int) (event.getY() / height);

        //如果点击的不是初始数独的数据
        if(game.sudoku[selectedY * 9 + selectedX] == 0){
            //获取点击格子不可用数据
            int used[] = game.getUsedTiles(selectedX,selectedY);

            StringBuffer stringBuffer = new StringBuffer();
            //输出不可用数据
            for(int i = 0;i < used.length;i++){
                stringBuffer.append(used[i]);
            }

            KeyDialog keyDialog = new KeyDialog(getContext(),used,this);
            keyDialog.show();
        }
        return true;
    }
    public void setSelectedTile(int tile){
        if(game.setTileIfValid(selectedX,selectedY,tile)){
            invalidate();
            //判断游戏是否通关，若通关则显示通关的dialog
            if(game.ifPassGame()){
                PassGameDialog passGameDialog=new PassGameDialog(getContext());
                passGameDialog.show();
            }
        }
    }
      //删除用户所选数字
     public  void setDeleteTile(){
        game.deleteTile(selectedX,selectedY);
        //重绘整个画布
         invalidate();
     }
}

