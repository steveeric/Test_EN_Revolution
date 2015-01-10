package jp.pmw.test_en_revolution.room;

import jp.pmw.test_en_revolution.confirm_class_plan.Student;

/**
 * Created by scr on 2014/11/27.
 * 教室マップレイアウトのセル一つ一つを管理するクラス
 * @author Shota Ito
 * @version 1.0
 */
public class Cell {
    private int preAttendeeFlag = 0;
    private String item;
    private float width;
    private float height;
    private int top;
    private int left;

    private Seat seat;
    private Student student;

    Cell(String item){
        this.item = item;
    }



    /**
     * 座席情報のセッター
     * **/
    public void setSeat(Seat seat){
        this.seat=seat;
    }
    /**
     * 座席情報のゲッター
     * **/
    public Seat getSeat(){return this.seat;}
    /**
     * 出席者のセッター
     * **/
    public void setAttendance(Student student){
        this.student = student;
    }
    /**
     * 出席者のゲッター
     * **/
    public Student getAttendee(){return this.student;}

    /**
     * 座る予定の人がいるかいないか
     * **/
    public boolean getSitterFlag(){
        boolean flag = false;
        if(this.student!=null){
            flag = true;
        }
        return flag;
    }


    public String getItem(){return this.item;}

    public void setPreAttendeeFlag(){
        this.preAttendeeFlag=1;
    }
    public int getPreAttendee(){
        return this.preAttendeeFlag;
    }


    public void setWidth(float width) {
        this.width = width;
    }
    public float getWidth() {
        return width;
    }
    public void setHeight(float height) {
        this.height = height;
    }
    public float getHeight() {
        return height;
    }
    public void setTop(int top) {
        this.top = top;
    }
    public int getTop() {
        return top;
    }
    public void setLeft(int left) {
        this.left = left;
    }
    public int getLeft() {
        return left;
    }
    public float getCx(){
        //return (float) ((float)this.left + (float)this.width/2.0);
        return (float) ((float)this.left + width);
    }
    public float getCy(){
        //return (float) ((float)this.top + (float)this.height/2.0);
        return (float) ((float)this.top + height);
    }


}
