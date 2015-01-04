package jp.pmw.test_en_revolution.room;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import jp.pmw.test_en_revolution.Atteandance;
import jp.pmw.test_en_revolution.MainActivity;
import jp.pmw.test_en_revolution.R;
import jp.pmw.test_en_revolution.room.dummy.DummyRoomMapContent;

/**
 *
 * Created by scr on 2014/12/05.
 * Viewに教室マップを表示するクラス
 * @author Shota Ito
 * @version 1.0
 */
public class RoomView extends View {
    /*教室情報を保持するインスタンス*/
    private RoomMap mRoom;
    /*描くインスタンス*/
    private Paint paint;
    /*画面を再描画する*/
    Canvas canvas = new Canvas();
    /**
     * コンストラクタ
     * @param context
     * */
    public RoomView(Context context) {
        super(context);
        init();
    }
    public RoomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RoomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        setFocusable(true);
        paint = new Paint();
    }



    /**
     * setRoomMapメソッド
     * 教室方法を保持するクラスにダミーデータを保持させる.
     * テスト用に使用するためです
     */
    public void setRoomMap(){
        this.mRoom = new RoomMap(DummyRoomMapContent.ROWS,DummyRoomMapContent.COLUMN);
        this.mRoom.setRoomMap(DummyRoomMapContent.ITEM);
        //this.mRoom.setTes.tPreAttendee();
        this.mRoom.setTestOnePreAttendee();
    }
    /**
     * setRoomMapメソッド
     * 教室方法を保持するクラスにダミーデータを保持させる.
     */
    public void setRoomMap(Room room){
        int rows = room.getCellRows();
        int columns = room.getCellColumns();
        this.mRoom = new RoomMap(rows,columns);
        this.mRoom.setRoomMap(room.getRoomMaping(),room.getSeats());
        onDraw(canvas);
    }

    public RoomMap getRoomMap(){
        return mRoom;
    }
    /**
     * Created by scr on 2014/12/12.
     * setAttendanceメソッド
     * ネットワークのデータベースにから取得した出席者の情報をRoomMapに保持させる
     */
    public void setAttendance(List<Atteandance> attendance){
        /*for(int i = 0; i < this.mRoom.getRows(); i++){
            for(int j = 0; j < this.mRoom.getColumns(); j++){
                if( this.mRoom.getCells()[i][j].getSeat() != null) {
                    String seatId = this.mRoom.getCells()[i][j].getSeat().getSeatId();
                    //出欠席情報を展開
                    for (int k = 0; k < attendance.size(); k++) {
                        if (seatId.equals(attendance.get(k).mSeatid)) {
                            //誰かがこの座席にすわる.
                            this.mRoom.getCells()[i][j].setPreAttendeeFlag();
                            this.mRoom.getCells()[i][j].setAttendee(attendance.get(k).mStudent);
                            if(attendance.get(k).mTempAttedance == 1){
                                //仮出席状態
                                this.mRoom.getCells()[i][j].getSeat().setPreAttendeeState(0);
                            }else if(attendance.get(k).mTempAttedance == 0){
                                if(attendance.get(k).mAttendanceConfirmainReceiveTime!=null){
                                    //出席状態に
                                    this.mRoom.getCells()[i][j].getSeat().setPreAttendeeState(1);
                                }else{
                                    //欠席状態に
                                    this.mRoom.getCells()[i][j].getSeat().setPreAttendeeState(2);
                                }
                            }
                            break;
                        }
                    }
                }
            }
        }
        //再描画をする.
        invalidate();*/
    }




    /**
     * onDrawメソッド
     * RoomViewクラスに絵を描く
     * @param canvas キャンパス
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(mRoom != null) {
            mRoom.setSize(getWidth(), getHeight());
            drawRoom(canvas);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                int r = (int)(y / mRoom.getCellHeidht());
                int c = (int)(x / mRoom.getCellWidth());
                //System.out.println("NOW X:"+x+",Y:"+y);
                if (r < mRoom.getRows() && c < mRoom.getColumns()){
                    try {
                        Cell[][] cells = mRoom.getCells();
                        for(int i = 0; i < cells.length; i++){
                            for(int j = 0; j< cells[i].length; j++){
                                Cell cell = cells[i][j];
                                float sx = cell.getCx() - cell.getWidth();
                                float ex = cell.getCx();
                                float sy = cell.getCy() - cell.getHeight();
                                float ey = cell.getCy();
                                if( (sx < x && x < ex) && (sy < y && y < ey)){
                                    if(cell.getItem().equals(MapConfig.SEAT) && cell.getPreAttendee()==1){
                                        //出席者の情報を表示する
                                        MainActivity activity = (MainActivity)this.getContext();
                                        /*タップ出来るかを確認*/
                                        /*
                                            activity.openDialogMessageType();
                                        */

                                        /*セル情報を確認する*/
                                        activity.openDialogShowCellInfo(cell);
                                        break;
                                    }/*else{
                                        //座席のテスト用
                                        MainActivity activity = (MainActivity)this.getContext();
                                        activity.openDialogShowCellInfo(cell);
                                        break;
                                    }*/
                                }
                             }
                        }
                    } catch (Exception e) {
                        Toast.makeText(this.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    invalidate(); //画面を再描画
                }
                break;
            default:
                return true;
        }
        return true;
    }


    private void drawRoom(Canvas canvas){
        int bw = mRoom.getWidth();
        int bh = mRoom.getHeight();
        float cw = mRoom.getCellWidth();
        float ch = mRoom.getCellHeidht();

        if (mRoom.getWidth() <=0 ) return;

        //Paint paint = new Paint(); //本当はここでnewするのはパフォーマンス上良くない。後で直そう。
        /*画面全面*/
        /*paint.setColor(Color.rgb(255, 255, 0));
        canvas.drawRect( 0, 0, bw, bh, paint);
        */
        //paint.setColor(Color.rgb(40, 40, 40)); //罫線の色

        //縦線
        for (int i = 0; i < mRoom.getColumns(); i++) {
            canvas.drawLine(cw * (i+1), 0, cw * (i+1), bh, paint);
        }
        //横線
        for (int i = 0; i < mRoom.getRows(); i++) {
            canvas.drawLine(0, ch * (i+1), bw, ch * (i+1), paint);
        }

        //円を描く前にアンチエイリアスを指定。これをしないと円がギザギザになる。
        //paint.setAntiAlias(true);

        //教室の最大行数
        int maxCellRow = mRoom.getRows();
        //教室の最大列数
        int maxCellColumn = mRoom.getColumns();

        Cell[][] cells = mRoom.getCells();
        String[] lineConf = mRoom.getLineConfigs();

        Resources r = ((Activity) this.getContext()).getResources();
        //席の前後間隔
        float move = mRoom.getCellHeidht() / 1.2f;

        for(int i = 0; i < mRoom.getRows(); i++){
            String conf = lineConf[i];
            for(int j = 0; j < mRoom.getColumns(); j++){
                Cell cell =cells[i][j];
                String item = cell.getItem();
                if(item.equals(MapConfig.FLOOR0)){
                    paint.setColor(r.getColor(R.color.brown));
                }else if(item.equals(MapConfig.SEAT)){
                    paint.setColor(r.getColor(R.color.white) );
                }else if(item.equals(MapConfig.BLACK_BORD)){
                    paint.setColor(r.getColor( R.color.darkGreen) );
                }

                /*if(item.equals(MapConfig.FLOOR0)){
                    paint.setColor(r.getColor(R.color.brown));
                }else if(item.equals(MapConfig.FLOOR1)){
                    paint.setColor(r.getColor(R.color.brown));
                }else if(item.equals(MapConfig.FLOOR2)){
                    paint.setColor(r.getColor(R.color.brown));
                }else if(item.equals(MapConfig.SEAT)){
                    paint.setColor(r.getColor(R.color.white) );
                }else if(item.equals(MapConfig.WEST_EXIT)){
                    paint.setColor(r.getColor(R.color.black) );
                }else if(item.equals(MapConfig.EAST_EXIT)){
                    paint.setColor(r.getColor(R.color.black) );
                }else if(item.equals(MapConfig.BLACK_BORD)){
                    paint.setColor(r.getColor( R.color.darkGreen) );
                }*/

                /*セルアイテム番号確認*/
                //canvas.drawText( cell.getItem()+":"+i+"-"+j, cell.getCx() - cell.getWidth(), cell.getCy(), paint);
                /*セルアイテム番号リバース番号*/
                /*
                int riverceCellRow = (maxCellRow -1) - i;
                int riverceCellColumn = (maxCellColumn -1) - j;
                canvas.drawText( cell.getItem()+":"+riverceCellRow+"-"+riverceCellColumn, cell.getCx() - cell.getWidth(), cell.getCy(), paint);
                */
                /*if(cell.getItem().equals("1")) {
                    canvas.drawText(cell.getSeat().getSeatId() + ":" + cell.getSeat().getSeatRowNumber() + "-" + cell.getSeat().getSeatColumnNumber(), cell.getCx() - cell.getWidth(), cell.getCy(), paint);
                }*/

               /*セル内を描く*/
               //canvas.drawRect(cell.getCx() - cell.getWidth(), cell.getCy() - cell.getHeight(), cell.getCx(), cell.getCy(), paint);

                float resizeHeight;
                float startResizeHeight;

                if(conf.equals("0")){
                    startResizeHeight =  cell.getCy() - cell.getHeight();
                    //高さを半分にする
                    resizeHeight = cell.getCy() - move;
                }else{
                    startResizeHeight   =  cell.getCy() - cell.getHeight() - move;
                    resizeHeight = cell.getCy();
                }
                canvas.drawRect(cell.getCx() - cell.getWidth(), startResizeHeight, cell.getCx(),resizeHeight , paint);

                if(cell.getPreAttendee()==1){
                    paint.setColor(r.getColor( R.color.red) );
                    /*if(cell.getSeat().getPreAttendeeState()==1){
                        paint.setColor(r.getColor( R.color.green) );
                    }else if(cell.getSeat().getPreAttendeeState()==2){
                        paint.setColor(r.getColor( R.color.dimGray) );
                    }*/
                    int space = 8;
                    canvas.drawRect((cell.getCx() - cell.getWidth())+cell.getWidth()/space , startResizeHeight+cell.getHeight()/space*2, cell.getCx() - cell.getWidth()/space/2, resizeHeight - cell.getHeight()/space, paint);
                }
            }
        }

    }
    /**
     * checkBeforeAndAfterSeatIntervalメソッド
     * RoomViewクラスに絵を描く
     * @param beforeAndAfterSeatInterval この行が座席の前後をあらわしている.
     * @return {@code true} 前後の座席間である. {@code false} 座席と座席の間ではない.
     */
    private boolean checkBeforeAndAfterSeatInterval(String beforeAndAfterSeatInterval){
        boolean flag = false;
        if(beforeAndAfterSeatInterval.endsWith(MapConfig.BEFORE_AND_AFTER_SEAT)){
           flag = true;
        }
        return flag;
    }
}
