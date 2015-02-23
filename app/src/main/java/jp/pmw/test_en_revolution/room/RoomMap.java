package jp.pmw.test_en_revolution.room;

import java.util.List;
import java.util.Random;

import jp.pmw.test_en_revolution.room.dummy.DummyRoomMapContent;

/**
 * Created by scr on 2014/11/27.
 */
public class RoomMap {
    //有効行は2倍に
    private static final float ROW_HEIGHT_MAGNIFICAT = 5.0f;

    private static final String ROW_LINE_ENABLE_ITEM = "1";
    private static final String ROW_LINE_DiSENABLE_ITEM = "0";

    /*public static final int COLS = 21;
    public static final int ROWS = 34;*/
    //有効行数
    private int enableRowLineCount;
    //無効行数
    private int disableRowLineCount;
    private int rows;
    private int columns;
    private int width;
    private float height;
    private int top;
    private int left;
    private Cell cells[][];
    private String lineConfig[];

    /**
     * @param enableRowLineCount 有効行数
     * @param desableLineCount 無効行数
     * **/
    RoomMap(int desableLineCount,int enableRowLineCount,int rows, int columns){
        this.enableRowLineCount = enableRowLineCount;
        this.disableRowLineCount = desableLineCount;
        this.rows=rows;
        this.columns=columns;
        cells = new Cell[rows][columns];
    }
    /*RoomMap(){
        cells = new Cell[rows][columns];
    }*/

    public void setRoomMap(String roomId,String map){
        int riverceCellRow = 0;
        int riverceCellColumn = 0;
        String[] line = doSplite(";",map);
        lineConfig = new String[line.length];
        for(int i = 0; i < line.length; i++){
            //System.out.println(line[i]);
            String ll = line[i];
            //座席がある行かどうか
            String[] lineConf = doSplite(":",ll);
            lineConfig[i] = lineConf[0];
            String[] cs = doSplite(",",lineConf[1]);
            for(int j = 0; j < cs.length; j++){
                cells[i][j] = new Cell(cs[j]);
                //端末左からを右からの番号に変更
                riverceCellRow = (this.rows -1) - i;
                //端末上からを下からの番号に変更
                riverceCellColumn = (this.columns -1) - j;
                //座席クラスを取得する
                Seat seat = getSeatInfo(roomId,riverceCellRow,riverceCellColumn);
                //座席クラスをセットする
                cells[i][j].setSeat(seat);
            }
        }
    }
    public void setRoomMap(String map,List<Seat> seats){
        int riverceCellRow = 0;
        int riverceCellColumn = 0;
        String[] line = doSplite(";",map);
        lineConfig = new String[line.length];
        for(int i = 0; i < line.length; i++){
            //System.out.println(line[i]);
            String ll = line[i];
            //座席がある行かどうか
            String[] lineConf = doSplite(":",ll);
            lineConfig[i] = lineConf[0];
            String[] cs = doSplite(",",lineConf[1]);
            for(int j = 0; j < cs.length; j++){
                cells[i][j] = new Cell(cs[j]);
                //端末左からを右からの番号に変更
                riverceCellRow = (this.rows -1) - i;
                //端末上からを下からの番号に変更
                riverceCellColumn = (this.columns -1) - j;
                //座席クラスを取得する
                //Seat seat = getSeatInfo(riverceCellRow,riverceCellColumn);
                Seat seat = null;
                for(int k = 0;k < seats.size(); k++){
                    if(seats.get(k).getSeatRowNumber() == riverceCellRow &&
                            seats.get(k).getSeatColumnNumber() == riverceCellColumn){
                        seat = seats.get(k);
                        break;
                    }
                }
                //座席クラスをセットする
                cells[i][j].setSeat(seat);
            }
        }
    }


    /**
     * Created by Shota Ito on 2014/12/11～2014/12/11.
     * setSeatInfoメソッド
     * 座席情報を返すために必要
     * @param cellRow 現在のセル行数
     * @param cellColumn 現在のセル行数
     * @return 座席を管理するクラスを返す.
     */
    private Seat getSeatInfo(String roomId,int cellRow,int cellColumn){
        String row = doDoubleDigit(cellRow);
        String column = doDoubleDigit(cellColumn);

        /*現在はダミーデータですよ!*/
        //String seatId = getDummyNextNumberAndRoomId()+row+column;

        //ダミーキャンパスID
        String seatId = roomId + row + column;

        //return new Seat(seatId,cellRow,cellColumn);
        return new Seat(seatId,cellRow,cellColumn);
    }


    /**
     * Created by Shota Ito on 2014/12/11～2014/12/11.
     * doDoubleDigitメソッド
     * 2桁にする
     * @param value
     * @return 1桁の数値だった場合は二ケタにして返す.
     */
    private String doDoubleDigit(int value){
        String str = "";
        if(value < 10){
            str = "0"+value;
        }else{
            str = ""+value;
        }
        return str;
    }


    /**
     * Created by Shota Ito on 2014/12/11～2014/12/11.
     * getDummyNextNumberAndRoomIdメソッド
     * ダミーの大学文科省コードと教室IDを作成する
     * @return ダミーの大学文科省コードと教室IDを返す.
     */
    private String getDummyNextNumberAndRoomId(){
        return DummyRoomMapContent.MEXT_NUMBER+DummyRoomMapContent.ROOM_ID;
    }

    public void setTestOnePreAttendee(){
        boolean b = false;
        for(int i = 0; i < this.rows; i++){
            for(int j = 0; j < this.columns; j++){
                String item = cells[i][j].getItem();
                if(item.equals(MapConfig.SEAT) && b == false){
                    cells[i][j].setPreAttendeeFlag();
                    b = true;
                }
            }
        }
    }

    public void setTestPreAttendee(){
        Random r = new Random();
        for(int i = 0; i < this.rows; i++){
            for(int j = 0; j < this.columns; j++){
                String item = cells[i][j].getItem();
                if(item.equals(MapConfig.SEAT)){
                    int n = r.nextInt(20);
                    if(n > 10){
                        cells[i][j].setPreAttendeeFlag();
                    }
                }
            }
        }
    }

    private String[] doSplite(String str,String item){
        return item.split(str, 0);
    }

    public int getRows(){
        return this.rows;
    }
    public int getColumns(){
        return this.columns;
    }

    public void setSize(int width, int height){
        int sz = width < height ? width : height;
        setWidth(width);
        setHeight(height);
    }

    public void setWidth(int width) {
        //this.width = (int) (width / columns) * columns; //列数で割り切れない場合は余りを捨てる。
        this.width = width;
        float cellW = this.getCellWidth();
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.columns; j++) {
                cells[i][j].setWidth(cellW);
                cells[i][j].setLeft((int) (j * cellW));
            }
        }
    }
    public int getWidth() {
        return width;
    }
    /*public void setHeight(int height) {
        this.height = (int)(height / rows) * rows; //行数で割り切れない場合は余りを捨てる。
        float cellH = this.getCellHeidht();
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.columns; j++) {
                cells[i][j].setHeight(cellH);
                cells[i][j].setTop((int)(i * cellH));
            }
        }
    }*/

    /**
     * Created by Shota Ito on 2015/1/10
     * aerialTotalRowLineCountメソッド
     * 架空の行数を返す
     * @return 架空の行数
     */
    private int aerialTotalRowLineCount(){
        return this.disableRowLineCount + (this.enableRowLineCount*(int)this.ROW_HEIGHT_MAGNIFICAT);
    }

    /**
     * Created by Shota Ito on 2015/1/10
     * checkOverScreenHeightSizeメソッド
     * 作成したCELLの高さをトータルして画面の高さを超えていないかを、
     * チェックします.
     * @return false 超えていない
     * @return true 超えている
     */
    private boolean checkOverScreenHeightSize(float totalHeightSize){
        boolean b = false;

        if(this.height < totalHeightSize){
            //端末の縦サイズを超えている.
            b = true;
        }

        return b;
    }


    /**
     * Created by Shota Ito on 2015/1/10
     * calculateEnableAndDisableLineHeightメソッド
     * 着席可能な場所と、そうでない場所のCELL高を割り出す.
     */
    private float[] calculateEnableAndDisableLineHeight(int aerialTotalRows){
        float[] linesHeight = new float[2];
        float disableCellH = (float)(this.height / aerialTotalRows);
        //float enableCellH = disableCellH * ROW_HEIGHT_MAGNIFICAT;

        float enableZoneH = this.height - (disableCellH * this.disableRowLineCount);
        float enableCellH = enableZoneH / this.enableRowLineCount;
        /*
        BigDecimal bigDecimal = new BigDecimal(String.valueOf(enableCell));
        //==== 小数第2位で切り捨て ====//
        float enableCellH = bigDecimal.setScale(1,  RoundingMode.FLOOR).floatValue();
        */

        float totalHeightSize = (disableCellH * this.disableRowLineCount) + (enableZoneH);

        /*boolean checkerFlag = true;
        int counter = 1;
        while(checkerFlag){
            checkerFlag = checkOverScreenHeightSize(totalHeightSize);
            if(checkerFlag == true) {
                if (counter % 2 != 0) {
                    //
                    --disableCellH;
                } else {
                    --enableCellH;
                }
                //やり直す
                totalHeightSize = (disableCellH * this.disableRowLineCount) + (enableZoneH);
            }
            ++counter;
        }*/
        int disable = Integer.valueOf(ROW_LINE_DiSENABLE_ITEM);
        int enable = Integer.valueOf(ROW_LINE_ENABLE_ITEM);

        linesHeight[disable] = disableCellH;
        linesHeight[enable] = enableCellH;
        return linesHeight;
   }

    public void setHeight(int height) {
        //this.height = (int)(height / rows) * rows; //行数で割り切れない場合は余りを捨てる。
        int aerialRows = aerialTotalRowLineCount();
        this.height = height;
        //this.height = (height / aerialRows) * aerialRows;

        float[] lineHeight = calculateEnableAndDisableLineHeight(aerialRows);
        float beforCellTop = 0.0f;
         for (int i = 0; i < this.rows; i++) {
            String line = lineConfig[i];
            float cellH = 0.0f;
             if(line.equals(ROW_LINE_DiSENABLE_ITEM)){
                //無効行
                 cellH = lineHeight[0];
             }else{
                //有効行
                 cellH = lineHeight[1];
             }
            for (int j = 0; j < this.columns; j++) {
                cells[i][j].setHeight(cellH);
                int top = (int)(beforCellTop);
                cells[i][j].setTop(top);
            }
             beforCellTop = beforCellTop + cellH;
        }
    }

    /*public void setHeight(int height) {
        this.height = (int)(height / rows) * rows; //行数で割り切れない場合は余りを捨てる。
        float cellH = this.getCellHeidht();
        for (int i = 0; i < this.rows; i++) {
            String line = lineConfig[i];
            for (int j = 0; j < this.columns; j++) {
                cells[i][j].setHeight(cellH);
                cells[i][j].setTop((int)(i * cellH));
            }
        }
    }*/
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
    public Cell[][] getCells(){
        return cells;
    }
    public String[] getLineConfigs(){
        return this.lineConfig;
    }
    public float getCellWidth(){
        return (float)(this.width / columns);
    }
    public float getCellHeidht(){
        return (float)(this.height / rows);
    }
 }
