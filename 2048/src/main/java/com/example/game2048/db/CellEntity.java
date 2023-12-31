package com.example.game2048.db;

import androidx.annotation.NonNull;

public class CellEntity {

    private int x;
    private int y;
    private int num;

    public CellEntity(int x, int y, int num) {
        this.x = x;
        this.y = y;
        this.num = num;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getNum() {
        return num;
    }

    @NonNull
    @Override
    public String toString() {
        return "(" + x + "," + y + ")" + num + "\n";
    }
}
