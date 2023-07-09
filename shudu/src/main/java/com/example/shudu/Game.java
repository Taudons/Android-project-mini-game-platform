package com.example.shudu;

import java.util.Random;

public class Game {
    public static String []strings=new String[]{"360000000" +
            "004230800" +
            "000004200" +
            "070460003" +
            "820000014" +
            "500013020" +
            "001900000" +
            "007048300" +
            "000000045","190200700" +
            "503000049" +
            "000900100" +
            "205003480" +
            "010000070" +
            "089600301" +
            "008009000" +
            "650000804" +
            "001004027","005310400" +
            "000400090" +
            "041708625" +
            "100070040" +
            "006000700" +
            "070040006" +
            "419804530" +
            "080001000" +
            "004037900","007000001" +
            "900000628" +
            "602000590" +
            "000081005" +
            "070503000" +
            "090060070" +
            "000005000" +
            "200040086" +
            "040030000","007020030" +
            "000000608" +
            "002006500" +
            "000102940" +
            "400800003" +
            "010000000" +
            "100200000" +
            "060370009" +
            "080001300" +
            "200508000"};

    private final String str;
   /**
     * 写了三个sudoku数组，可能难理解，介于要给玩家所选数组更替颜色，所以需要把初始数据和玩家所选数据分离，
     * 但是这两个用于界面的更新，不涉及的计算不可用数据，计算不可用数据需要它俩的结合，这个结合只涉及到逻辑计算，不涉及到界面，
     * 因此产生了三个数组。
     */
    //数独初始数据的存储
    protected int sudoku [] = new int [9 * 9];
    //数独玩家所选数据的存储
    protected int sudoku1 [] = new int [9 * 9];
    //数独全部数据的存储 初始+所选
    protected int sudoku2 [] = new int [9 * 9];
    //用于存储每个单元格已经不可用的数据
    private int used[][][]=new int[9][9][];
    public Game(){
        int rusult=new Random().nextInt(5);
        str=strings[rusult];
        sudoku2=fromPuzzleString(str);
        sudoku=fromPuzzleString(str);
        calculateAllUsedTiles();
    }
    private int getTile(int x,int y,int[] sudo){
        return sudo[y*9+x];
    }
    private void setTile(int x,int y,int value) {
        //把玩家所选数据增加到数独玩家所选数据
        sudoku1[y * 9 + x] = value;
        //把玩家所选数据增加到 数独全部数据
        sudoku2[y * 9 + x] = value;
    }
    public String getTileString(int x,int y,int[] sudo){
        int v=getTile(x,y,sudo);
        if(v==0){
            return "";
        }else{
            return String.valueOf(v);
        }
    }
    //根据一个字符串数据，生成一个整型数组，所谓数独游戏的初始化数据
    protected int[]fromPuzzleString(String src){
        int[] sudo=new int[src.length()];
        for (int i = 0; i < sudo.length; i++) {
            sudo[i]=src.charAt(i)-'0';//把字符变成数字
        }
        return sudo;
    }
    //用于计算所有单元格对应的不可用数据
    public void calculateAllUsedTiles(){
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                 used[x][y]=calculateUsedTiles(x,y);
            }
        }
    }
    //取出出该单元格内不能填入那些数字
    public int[] getUsedTilesByCoor(int x,int y){
        return used[x][y];
    }
    //计算某个单元格中已经不可用的数据
    public int[] calculateUsedTiles(int x,int y){
       //把全部数独数据赋值给sudo
        int[] sudo=sudoku2;
       //记录已经不能用的数字
        int c[]=new int[9];

        //计算所选单元格一列全部用过的数字
        for (int i = 0; i < 9; i++) {
            if(i==y)
                continue;
                int t=getTile(x,i,sudo);
                if(t!=0)  c[t-1]=t;
            }

        //计算所选单元格一行全部用过的数字
        for (int i = 0; i < 9; i++) {
            if(i==x)
                continue;
            int t=getTile(i,y,sudo);
            if(t!=0)   c[t-1]=t;
        }
        //计算小九宫格已经用过的数字
        int startX=(x/3)*3;
        int startY=(y/3)*3;
        for (int i = startX; i < startX+3; i++) {
            for (int j = startY; j <startY+3 ; j++) {
                    if(i==x&&j==y)
                        continue;
                    int t=getTile(i,j,sudo);
                    if(t!=0)  c[t-1]=t;
        }
    }
        //去除数组中非零元素，即压缩数组
        int nused=0;
        for (int t:c) {
              if(t!=0)nused++;
        }
        int c1[]=new int[nused];
         int a=0;
        for (int t: c) {
            if(t!=0)  c1[a++]=t;
        }
        return c1;
    }
    //检测玩家所选数据是否为不可用数据
    protected boolean setTileIfValid(int x,int y,int value){
        //取出该单元格不可用数据
        int tiles[]=getUsedTiles(x,y);
        if(value!=0){
            for (int tile:tiles) {
                if(tile==value)
                   return false;
            }
        }
        //给数独数组增加数据
        setTile(x,y,value);
        //重新计算全部单元格不可用数据
        calculateAllUsedTiles();
        return true;
    }
    //获取该单元格不可用数据
    protected  int[] getUsedTiles(int x,int y){
        return used[x][y];
    }
    //删除数独数组的一个数据
    public void deleteTile(int selectedX,int selectedY){
        //该单元格设置为0
        setTile(selectedX,selectedY,0);
        //重新计算个单元格的不可用数据
        calculateAllUsedTiles();
    }
    //通关判断 全部数独中有无0，来判断这个数独是否完成
    public boolean ifPassGame(){
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (sudoku2[j * 9 + i] == 0) {
                    return false;
                }

            }
        }
            return  true;

    }

  }

