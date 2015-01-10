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
import jp.pmw.test_en_revolution.SeatSituationFragment;
import jp.pmw.test_en_revolution.confirm_class_plan.Roster;
import jp.pmw.test_en_revolution.confirm_class_plan.Student;
import jp.pmw.test_en_revolution.confirm_class_plan.ThisClassTime;
import jp.pmw.test_en_revolution.grouping.GroupingManagement;
import jp.pmw.test_en_revolution.room.dummy.DummyRoomMapContent;

/**
 *
 * Created by scr on 2014/12/05.
 * Viewに教室マップを表示するクラス
 * @author Shota Ito
 * @version 1.0
 */
public class RoomView extends View {

    private static final int SITTER_VERTICAL_SPACE = 8;
    private static final int SITTER_HORIZONTAL_SPACE = 24;
    //
    MainActivity activity;
    //座席フラグメント
    private SeatSituationFragment fragment;
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

    public void setSeatSituationFragment(SeatSituationFragment fragment){
        this.fragment = fragment;
    }

    private void init(){
        activity = (MainActivity)this.getContext();
        setFocusable(true);
        paint = new Paint();
    }

    /**
     * setRoomMapメソッド
     * 教室方法を保持するクラスにダミーデータを保持させる.
     * テスト用に使用するためです
     */
    public void setRoomMap(){
        this.mRoom = new RoomMap(DummyRoomMapContent.DISABLE_ROWS,DummyRoomMapContent.ENABLE_ROWS,
                DummyRoomMapContent.ROWS,DummyRoomMapContent.COLUMN);
        this.mRoom.setRoomMap(DummyRoomMapContent.ITEM);
        //this.mRoom.setTes.tPreAttendee();
        //this.mRoom.setTestOnePreAttendee();
    }
    /**
     * Created by scr on 2015/1/6.
     * setDummyGroupingメソッド
     * ダミーグルーピングをセット
     */
    public void setDummyGrouping(){
        GroupingManagement gpManagement = activity.mTeacher.getGroupingManagement();
        setDummySitGrouping(gpManagement.getGroupings());
    }
    /**
     * Created by scr on 2015/1/6.
     * setDummySitGroupingメソッド
     * ダミーグルーピングデータを座席にセット
     */
    private void setDummySitGrouping(List<Student> grouping) {
       for(int k = 0; k < grouping.size(); k++) {
           for (int i = 0; i < this.mRoom.getRows(); i++) {
               for (int j = 0; j < this.mRoom.getColumns(); j++) {
                   if (this.mRoom.getCells()[i][j].getSeat() != null) {
                       String seatId = this.mRoom.getCells()[i][j].getSeat().getSeatId();
                       //出席者一人一人が保持しているSeatId
                       String groupingSitSeatId = grouping.get(k).getThisClassTime().getThisClassSittingPosition().getSeatId();
                       if (seatId.equals(groupingSitSeatId)) {
                           //着席者あり.
                           this.mRoom.getCells()[i][j].setAttendance(grouping.get(k));
                           break;
                       }
                   }
               }
           }
       }
        invalidate(); //画面を再描画
    }

    /**
     * Created by scr on 2015/1/4.
     * setDummyAttendanceメソッド
     * 端末内のローカル内にあるダミー出席データをセットする
     */
    public void setDummyAttendance(){
        Roster roster = activity.mTeacher.getRoster();
        setDummySitInAttendance(roster);
    }
    private void setDummySitInAttendance(Roster roster) {
        for (int k = 0; k < roster.getRosterList().size(); k++) {
            for (int i = 0; i < this.mRoom.getRows(); i++) {
                for (int j = 0; j < this.mRoom.getColumns(); j++) {
                    if (this.mRoom.getCells()[i][j].getSeat() != null) {
                        String seatId = this.mRoom.getCells()[i][j].getSeat().getSeatId();
                        //出席者一人一人が保持しているSeatId
                        String rosterInSeatId = roster.getRosterList().get(k).getThisClassTime().getThesClassSittingPositionId();
                        if (seatId.equals(rosterInSeatId)) {
                            //着席者あり.
                            this.mRoom.getCells()[i][j].setAttendance(roster.getRosterList().get(k));
                            break;
                        }
                    }
                }
            }
        }
        invalidate(); //画面を再描画
    }

    /**
     * setRoomMapメソッド
     * 教室方法を保持するクラスにダミーデータを保持させる.
     */
    public void setRoomMap(Room room){
        int desableRows = room.getDisabledRows();
        int enableRows = room.getEnableRows();
        int rows = room.getCellRows();
        int columns = room.getCellColumns();
        this.mRoom = new RoomMap(desableRows,enableRows,rows,columns);
        this.mRoom.setRoomMap(room.getRoomMaping(),room.getSeats());
        //onDraw(canvas);
        this.invalidate();
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
            //画面サイズをセットする.
            mRoom.setSize(getWidth(), getHeight());
            //部屋の有効行(着席又は主用な行通路)、無効行に応じて
            //cell一つ一つのサイズを割り出す.

            drawRoom(canvas);
            if(activity.mTeacher.getEndAttendanceFlag() == false) {
                invalidate();
            }
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
            case MotionEvent.ACTION_DOWN:
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
                                    if(cell.getItem().equals(MapConfig.SEAT) && cell.getAttendee() != null){
                                        //出席者の情報を表示する
                                        //MainActivity activity = (MainActivity)this.getContext();
                                        //SeatSituationFragment fragment = SeatSituationFragment.getSeatSituationFragmentInstance();
                                        /*タップ出来るかを確認*/
                                        /*
                                            activity.openDialogMessageType();
                                        */

                                        /*セル情報を確認する*/
                                        //activity.openDialogShowCellInfo(cell);
                                        Student student = cell.getAttendee();
                                        fragment.openDialogFragmentShowCellInfo(student);
                                        break;
                                    }
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
        if (mRoom.getWidth() <=0 ) return;

        //リソース
        Resources r = ((Activity) this.getContext()).getResources();

        //教室すべての床を塗りつぶす
        paint.setColor(r.getColor(R.color.brown));
        canvas.drawRect(0, 0, getWidth(), getHeight(),paint);


        //円を描く前にアンチエイリアスを指定。これをしないと円がギザギザになる。
        //paint.setAntiAlias(true);

        Cell[][] cells = mRoom.getCells();
        String[] lineConf = mRoom.getLineConfigs();

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

               /*セル内を描く*/
               canvas.drawRect(cell.getCx() - cell.getWidth(), cell.getCy() - cell.getHeight(), cell.getCx(), cell.getCy(), paint);

                //着席者描く
                if(cell.getAttendee() != null){
                    paint.setColor(r.getColor(R.color.darkRed));
                    if(ThisClassTime.UNCONFIRMED_ATTENDANCE_STATE == cell.getAttendee().getThisClassTime().getAttendanceState()){
                        //未確認
                        paint.setColor(r.getColor( R.color.dimGray) );
                    }else if(ThisClassTime.ABSENTEE_ATTENDANCE_STATE == cell.getAttendee().getThisClassTime().getAttendanceState()){
                        //欠席
                        paint.setColor(r.getColor(R.color.darkRed));
                    }else if(ThisClassTime.ATTENDEE_ATTENDANCE_STATE == cell.getAttendee().getThisClassTime().getAttendanceState()){
                        //出席
                        paint.setColor(r.getColor( R.color.green) );
                    }else{
                        //不明
                        paint.setColor(r.getColor( R.color.dimGray) );
                    }

                    float sitterVerticalSpace = (cell.getWidth() / this.SITTER_VERTICAL_SPACE);
                    float sitterHorizontalSpace = (cell.getHeight() / this.SITTER_HORIZONTAL_SPACE);
                    canvas.drawRect(cell.getCx() - cell.getWidth() + sitterVerticalSpace
                            ,cell.getCy() - cell.getHeight() + sitterHorizontalSpace
                            ,cell.getCx() - sitterVerticalSpace
                            ,cell.getCy() - sitterHorizontalSpace
                            ,paint);
                }


                GroupingManagement gpManagement = activity.mTeacher.getGroupingManagement();
                //グルーピングモードがONで出席者がいた場合
                if(gpManagement.getDoGroupingFlag() == true
                        && cell.getAttendee()!=null) {
                    //modeGrouping(r,cell);
                    int herfSpace = 2;
                    //グループ名を取得
                    String groupName = cell.getAttendee().getThisClassTime().getThisClassGroup().getGroupName();
                    paint.setColor(r.getColor( R.color.black) );
                    //フォントサイズ
                    float fontSize = activity.getResources().getDimension(R.dimen.textsize_large);
                    paint.setTextSize (fontSize);
                    canvas.drawText( groupName, cell.getCx() - cell.getWidth() + (cell.getWidth()/(herfSpace*herfSpace) ), cell.getCy() -(cell.getHeight()/(herfSpace*herfSpace)), paint);
                }

                /*セルアイテム番号確認*/
                //paint.setColor(r.getColor(R.color.black));
                //canvas.drawText( cell.getItem()+":"+i+"-"+j, cell.getCx() - cell.getWidth(), cell.getCy(), paint);

            }
        }
    }

    private void modeGrouping(Resources r,Cell cell){
         /*グループ番号を描く*/
        /*MainActivity activity = (MainActivity)this.getContext();
        if(activity.mTeacher.getGroupingManagement().getDoGroupingFlag() == true){
          }*/
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
