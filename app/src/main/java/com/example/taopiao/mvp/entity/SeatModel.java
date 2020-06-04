package com.example.taopiao.mvp.entity;

public class SeatModel {
    public String seat_name;//几排几座
    public String col_name;//排名
    public String row_name;//列名
    public Integer row;//行
    public Integer column;//列
    public Integer status;//状态
    public Integer seat_id;
   public Integer xIndex;
   public  Integer yIndex;

    public void setxIndex() {
        this.xIndex = this.row-1;
    }

    public void setyIndex() {
        this.yIndex = this.column-1;
    }

    public Integer getxIndex() {
        return xIndex;
    }



    public Integer getyIndex() {
        return yIndex;
    }



    public String getSeat_name() {
        return seat_name;
    }

    public void setSeat_name(String seat_name) {
        this.seat_name = seat_name;
    }

    public String getCol_name() {
        return col_name;
    }

    public void setCol_name(String col_name) {
        this.col_name = col_name;
    }

    public String getRow_name() {
        return row_name;
    }

    public void setRow_name(String row_name) {
        this.row_name = row_name;
    }

    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public Integer getColumn() {
        return column;
    }

    public void setColumn(Integer column) {
        this.column = column;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSeat_id() {
        return seat_id;
    }

    public void setSeat_id(Integer seat_id) {
        this.seat_id = seat_id;
    }

    @Override
    public String toString() {
        return "SeatModel{" +
                "seat_name='" + seat_name + '\'' +
                ", col_name='" + col_name + '\'' +
                ", row_name='" + row_name + '\'' +
                ", row=" + row +
                ", column=" + column +
                ", status=" + status +
                ", seat_id=" + seat_id +
                ", xIndex=" + xIndex +
                ", yIndex=" + yIndex +
                '}';
    }
}
