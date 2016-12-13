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

import java.util.ArrayList;
import java.util.List;

import jp.pmw.test_en_revolution.Assistant;
import jp.pmw.test_en_revolution.AttendanceObject;
import jp.pmw.test_en_revolution.MainActivity;
import jp.pmw.test_en_revolution.R;
import jp.pmw.test_en_revolution.SeatObject;
import jp.pmw.test_en_revolution.SeatSituationFragment;
import jp.pmw.test_en_revolution.StudentObject;
import jp.pmw.test_en_revolution.TransmitStateObject;
import jp.pmw.test_en_revolution.confirm_class_plan.Seat;
import jp.pmw.test_en_revolution.confirm_class_plan.SeatingChart;
import jp.pmw.test_en_revolution.confirm_class_plan.Student;
import jp.pmw.test_en_revolution.confirm_class_plan.ThisClassTime;
import jp.pmw.test_en_revolution.confirm_class_plan.RoomInfoObject;
import jp.pmw.test_en_revolution.grouping.GroupingManagement;

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
    //
    String mTapAttendanceId = "";
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
     * setDummyDataRoomMapメソッド
     * 教室方法を保持するクラスにダミーデータを保持させる.
     * テスト用に使用するためです
     */
   /* public void setDummyDataRoomMap(){
        String dummyroomId = this.activity.mTeacher.getClassPlan().getPlace().getRoom().getRoomId();
        if(dummyroomId.equals(DummyRoomMapContent.ROOM_135_ID)){
            //135教室
            this.mRoom = new RoomMap(DummyRoomMapContent.DISABLE_135_ROWS,DummyRoomMapContent.ENABLE_135_ROWS,
                    DummyRoomMapContent.ROWS_135,DummyRoomMapContent.COLUMN_135);
            this.mRoom.setRoomMap(DummyRoomMapContent.ROOM_135_ID,DummyRoomMapContent.CELL_ITEM_135);
        }else if(dummyroomId.equals(DummyRoomMapContent.ROOM_1317_ID)){
            //1317教室
            this.mRoom = new RoomMap(DummyRoomMapContent.DISABLE_1317_ROWS,DummyRoomMapContent.ENABLE_1317_ROWS,
                    DummyRoomMapContent.ROWS_1317,DummyRoomMapContent.COLUMN_1317);
            this.mRoom.setRoomMap(DummyRoomMapContent.ROOM_1317_ID,DummyRoomMapContent.CELL_ITEM_1317);
        }else{
            //241教室
            this.mRoom = new RoomMap(DummyRoomMapContent.DISABLE_241_ROWS,DummyRoomMapContent.ENABLE_241_ROWS,
                    DummyRoomMapContent.ROWS_241,DummyRoomMapContent.COLUMN_241);
            this.mRoom.setRoomMap(DummyRoomMapContent.ROOM_241_ID,DummyRoomMapContent.CELL_ITEM_241);
        }
    }*/
    /**
     * Created by scr on 2015/1/6.
     * setDummyGroupingメソッド
     * ダミーグルーピングをセット
     */
    public void setDummyGrouping(){
        GroupingManagement gpManagement = activity.mTeacher.getGroupingManagement();
        //setDummySitGrouping(gpManagement.getGroupings());
    }
    /**
     * Created by scr on 2015/1/6.
     * setDummySitGroupingメソッド
     * ダミーグルーピングデータを座席にセット
     */
    /*private void setDummySitGrouping(List<Student> grouping) {
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
    }*/

    /**
     * Created by scr on 2015/1/4.
     * setDummyAttendanceメソッド
     * 端末内のローカル内にあるダミー出席データをセットする
     */
    /*public void setDummyAttendance(){
        Roster roster = activity.mTeacher.getRoster();
        setDummySitInAttendance(roster);
    }*/
    /*private void setDummySitInAttendance(Roster roster) {
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
    }*/

    /**
     * setRoomMapメソッド
     * 教室方法を保持するクラスにダミーデータを保持させる.
     */
    /*public void setRoomMap(Room room){
        int desableRows = room.getDisabledRows();
        int enableRows = room.getEnableRows();
        int rows = room.getCellRows();
        int columns = room.getCellColumns();
        this.mRoom = new RoomMap(desableRows,enableRows,rows,columns);
        this.mRoom.setRoomMap(room.getRoomMaping(),room.getSeats());
        //onDraw(canvas);
        this.invalidate();
    }*/

    public void setRoomInfoObject(RoomInfoObject tmp){
        SeatingChart sc = tmp.seatcingChart;
        this.mRoom = new RoomMap(sc.getDisableRowLineCount(),sc.getEnabeRowLineCount(),sc.getMaxMapRow(),sc.getMaxMapColumn());
        this.mRoom.setRoomMap(sc.getSeatingChart(),tmp.seats);
    }


    public RoomMap getRoomMap(){
        return mRoom;
    }
    /**
     * Created by scr on 2014/12/12.
     * 2015/2/25改変
     * setAttendanceメソッド
     * ネットワークのデータベースにから取得した出席者の情報をRoomMapに保持させる
     */
    /*public void setAttendance(List<Student> attendance){
        for (int i = 0; i < this.mRoom.getRows(); i++) {
            for (int j = 0; j < this.mRoom.getColumns(); j++) {
                if (this.mRoom.getCells()[i][j].getSeat() != null) {
                    String seatId = this.mRoom.getCells()[i][j].getSeat().getSeatId();
                    for(int k = 0;k<attendance.size();k++){
                        //出席者一人一人が保持しているSeatId
                        if(attendance.get(k).getSitSeatInfo() != null) {
                            String attSeatPositionId = attendance.get(k).getSitSeatInfo().getSeatId();
                            if (seatId.equals(attSeatPositionId)) {
                                //着席者あり.
                                this.mRoom.getCells()[i][j].setAttendance(attendance.get(k));
                                break;
                            }
                        }
                    }
                }
            }
        }
    }*/
    /**
     * Created by scr on 2016/2/16.
     * seatedStudentsメソッド
     * 学生を着席させます.
     * @param sos 本日の履修者群
     */
    public void seatedStudents(StudentObject[] sos) {
        for (int i = 0; i < this.mRoom.getRows(); i++) {
            for (int j = 0; j < this.mRoom.getColumns(); j++) {
                if (this.mRoom.getCells()[i][j].getSeat() != null) {
                    String seatId = this.mRoom.getCells()[i][j].getSeat().getSeatId();

                    for(int k = 0; k < sos.length; k++){
                        //  学生
                        StudentObject so = sos[k];
                        if(seatedExsistance(seatId, so)){
                            mRoom.getCells()[i][j].setAttendance(so);
                        }
                    }
                }
            }
        }
        //  欠席者を出席者で上書きする.
        seatedAttendance(sos);
        //  再描画
        invalidate();
    }

    /**
     * Created by scr on 2016/5/20.
     * seatedAttendanceメソッド
     * 出席者を座席に着席させます.
     * ■欠席者が出席者より少ない場合(通常授業はこちらに該当)
     * グループ調整時、
     * 座席移動対象学生が欠席者の座席を上書きするため問題なかった.
     * ■欠席者が出席者より多い場合
     * グループ調整時、
     * 座席移動対象学生を描いた後に、欠席者で上書きされてしまいバグの原因になる.
     *
     * そのため、欠席者を出席者で上書きするために追加しました.
     *
     * @param sos 本日の履修者群
     */
    void seatedAttendance(StudentObject[] sos){
        for (int i = 0; i < this.mRoom.getRows(); i++) {
            for (int j = 0; j < this.mRoom.getColumns(); j++) {
                if (this.mRoom.getCells()[i][j].getSeat() != null) {
                    String seatId = this.mRoom.getCells()[i][j].getSeat().getSeatId();
                    for(int k = 0; k < sos.length; k++){
                        //  学生
                        StudentObject so = sos[k];
                        if(seatedExsistance(seatId, so)){
                            if( so.getAttendanceObject() != null ){
                                if( so.getAttendanceObject().getStateAttendance() == AttendanceObject.STATE_ATTENDANCE ){
                                    //  出席者
                                    mRoom.getCells()[i][j].setAttendance(so);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

        /**
         * Created by scr on 2016/2/16.
         * seatedStudentsメソッド
         * 学生を着席させます.
         * @param seatId    対象座席
         * @param so        学生(履修者)
         * {return false} 着席者なし,{return true}学生(履修者)が着席者
         */
    public boolean seatedExsistance(String seatId, StudentObject so){
        String sitPosition  =   so.getSeatObject().getSeatId();
        if(seatId.equals(sitPosition)){
            //  着席者
            return true;
        }
            //  着席者でない
            return false;
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
            /*if(activity.mTeacher.getEndAttendanceFlag() == false) {
                invalidate();
            }*/
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

                                        StudentObject so = cell.getAttendee();
                                        this.mTapAttendanceId = so.getAttendanceObject().getAttendanceId();
                                        this.invalidate();
                                        fragment.openDialogFragmentShowCellInfo(so);
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

        //  補助者達の着席状況を保持する変数 add 2016/04/24 05:23
        List<Assistant> assistantList = new ArrayList<Assistant>();

        for(int i = 0; i < mRoom.getRows(); i++){
            String conf = lineConf[i];
            for(int j = 0; j < mRoom.getColumns(); j++){

                Cell cell =cells[i][j];
                String item = cell.getItem();

                if(item.equals(MapConfig.FLOOR0)){
                    //  床
                    paint.setColor(r.getColor(R.color.brown));
                }else if(item.equals(MapConfig.SEAT)){
                    //  座席
                    paint.setColor(r.getColor(R.color.white) );
                }else if(item.equals(MapConfig.BLACK_BORD)){
                    //  黒板
                    paint.setColor(r.getColor( R.color.darkGreen) );
                }
               /*セル内を描く*/
               canvas.drawRect(cell.getCx() - cell.getWidth(), cell.getCy() - cell.getHeight(), cell.getCx(), cell.getCy(), paint);

                /*座席の境目を表示する*/
                //2015年1月13日
                /*if((cell.getSeat().getSeatRowNumber()-1)%10 == 0 && cell.getSeat().getSeatRowNumber()-1 != 0){
                    paint.setColor(r.getColor( R.color.navy) );
                    canvas.drawRect(cell.getCx() - cell.getWidth(), cell.getCy() - cell.getHeight() + cell.getHeight() / 3, cell.getCx(), cell.getCy()  - cell.getHeight() / 3, paint);
                }*/


                TransmitStateObject tso = activity.getClassObject().getTransmitStateObject();

                //  出席状況を色で表す
                if(cell.getAttendee() != null && tso != null) {
                    //  出席学生オブジェクトクラス
                    StudentObject studentObjecct = cell.getAttendee();

                    //  出席カラー
                    int attColor = cell.getAttendee().getAttendanceObject().getAttendanceStateColor(activity, tso);
                    paint.setColor(attColor);

                    float sitterVerticalSpace = (cell.getWidth() / this.SITTER_VERTICAL_SPACE);
                    float sitterHorizontalSpace = (cell.getHeight() / this.SITTER_HORIZONTAL_SPACE);
                    canvas.drawRect(cell.getCx() - cell.getWidth() + sitterVerticalSpace
                            , cell.getCy() - cell.getHeight() + sitterHorizontalSpace
                            , cell.getCx() - sitterVerticalSpace
                            , cell.getCy() - sitterHorizontalSpace
                            , paint);
                    //  中抜き用(タップされている座席をわかるようにするため)
                    if( this.mTapAttendanceId.equals( studentObjecct.getAttendanceObject().getAttendanceId() ) ){
                        float svs = (cell.getWidth() / this.SITTER_VERTICAL_SPACE) * 2 ;
                        float shs = (cell.getHeight() / this.SITTER_HORIZONTAL_SPACE) * 4.5f ;
                        paint.setColor(r.getColor(R.color.white) );
                        canvas.drawRect(cell.getCx() - cell.getWidth() + svs
                                , cell.getCy() - cell.getHeight() + shs
                                , cell.getCx() - svs
                                , cell.getCy() - shs
                                , paint);
                    }
                    //  補助者を引き連れいているかどうか
                    if(studentObjecct.getAssistants() != null){
                        //  補助者を引き連れている場合は、
                        //  後から補助者の場所を描くので情報を保持
                        Assistant[] assistants = studentObjecct.getAssistants();
                        for(int a = 0; a < assistants.length; a++){
                            Assistant assistant = assistants[a];
                            //  出席カラーも保持させる
                            assistant.setAttendanceColor(attColor);
                            assistantList.add(assistant);
                        }
                    }
                    /*if (cell.getAttendee().getGroup() != null) {
                        String groupName = cell.getAttendee().getGroup().getGroupName();
                        paint.setColor(r.getColor(R.color.black));
                        //フォントサイズ
                        float fontSize = activity.getResources().getDimension(R.dimen.textsize_large);
                        paint.setTextSize(fontSize);
                        canvas.drawText(groupName
                                , cell.getCx() - cell.getWidth() + sitterVerticalSpace
                                , cell.getCy() - cell.getHeight() / 2
                                , paint
                        );
                    }*/
                    /*
                    paint.setColor(r.getColor( R.color.white) );
                    //出席デバッグ用
                    canvas.drawText(""+ cell.getAttendee().getAttendance().getStatus()
                            ,cell.getCx() - cell.getWidth() + sitterVerticalSpace
                            ,cell.getCy() - cell.getHeight() + sitterHorizontalSpace
                            ,paint);
                    */
                }
               /*GroupingManagement gpManagement = activity.mTeacher.getGroupingManagement();
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
                }*/

                /*セルアイテム番号確認*/
                //  デバッグ用
                //paint.setColor(r.getColor(R.color.black));
                //canvas.drawText( cell.getItem()+":"+i+"-"+j, cell.getCx() - cell.getWidth(), cell.getCy(), paint);
                /*if(cell.getSeat() != null){
                    //String seatNumber =  cell.getSeat().getRow()+""+cell.getSeat().getColumn();
                    //String seatNumber = cell.getSeat().getSeatName();
                    String seatNumber = cell.getSeat().getSeatBlokName()+" "+cell.getSeat().getRow()+" "+cell.getSeat().getColumn();
                    canvas.drawText(seatNumber, cell.getCx() - cell.getWidth(), cell.getCy(), paint);
                }*/


                //  ノートテイカー情報をView画面上に「N」を表示します.
                /*paint.setTextSize(32.0f);
                String noteTakerName = "N";
                if(nextFlag == true){
                    paint.setColor(nextAttColor);
                    canvas.drawText( noteTakerName, cell.getCx() - (cell.getWidth()) / 2 -(cell.getWidth()) / 6 , cell.getCy() - cell.getHeight() / 4, paint);
                    nextFlag        =   false;
                }
                if( cell.getAttendee() != null ){
                    //canvas.drawText( cell.getAttendee().getAssistant()+"", cell.getCx() - cell.getWidth(), cell.getCy(), paint);
                    if( cell.getAttendee().getAssistant() == 1 ){
                        //
                        int attendColor = cell.getAttendee().getAttendanceObject().getAttendanceStateColor(activity, tso);
                        paint.setColor(attendColor);
                        //
                        canvas.drawText( noteTakerName, cell.getCx() - (cell.getWidth() * 2) , cell.getCy()  - cell.getHeight() / 4 , paint);
                        //
                        nextFlag        =   true;
                        //
                        nextAttColor    =   attendColor;
                    }
                }*/
            }
        }
        //  補助者の着席位置を描く (ノートテイカー)
        drawSeatOfNoteTaker(canvas, assistantList);
    }
    /**
     * drawSeatOfNoteTakerメソッド
     * 補助者が着席している座席に
     * 「N」を描きます.
     *  園田学園女子大学様では、
     *  身体に不自由がある学生には、
     *  代わりに授業用ノートを
     *  記述してあげる、
     *  ノートテイカーというアルバイト?学生が
     *  出席履修生の横に着席するそうです.
     *  そのため、座席指定の場合、ノートテイカーを
     *  必要としている学生の左右?が空席になるので、
     *  空席の理由がバグではなくノートテイカーの
     *  座席ですを表すために使用します.
     * @param canvas        キャンバス
     * @param assistantList 補助者群
     * @since 2016/04/24 05:56
     */
    private void drawSeatOfNoteTaker(Canvas canvas,List<Assistant> assistantList){
        //  補助者(ノートテイカー)
        paint.setTextSize(32.0f);
        String noteTakerName = "N";

        Cell[][] cells = mRoom.getCells();

        for(int i = 0; i < mRoom.getRows(); i++){
            for(int j = 0; j < mRoom.getColumns(); j++) {
                Cell cell = cells[i][j];
                if( this.mRoom.getCells()[i][j].getSeat() != null ){
                    //座席あり
                    String seatId = this.mRoom.getCells()[i][j].getSeat().getSeatId();

                    for(int a = 0; a < assistantList.size(); a++){

                        if( seatId.equals(assistantList.get(a).getSeatId()) ){
                            //
                            int attendanceColor = assistantList.get(a).getAttendanceColor();
                            paint.setColor(attendanceColor);

                            //  「N」を描く
                            canvas.drawText(
                                    noteTakerName
                                    ,cell.getCx() - cell.getWidth() + (cell.getWidth() / 4)
                                    , cell.getCy() - cell.getHeight() + (cell.getHeight() / 2)
                                    , paint);

                        }
                    }
                }
            }
        }
    }


    /**
     * drawAttendeeメソッド
     * 出席者を描きます.
     * グループ調整での不具合に対応するためです.
     * 通常は、履修者の配列には①欠席者②出席者の順に入ってきます.
     * ですが、出席者が少なかった場合は、①出席者
     * */
    /*private void drawAttendee(Canvas canvas){
        Cell[][] cells = mRoom.getCells();
        String[] lineConf = mRoom.getLineConfigs();
        for(int i = 0; i < mRoom.getRows(); i++) {
            String conf = lineConf[i];
            for (int j = 0; j < mRoom.getColumns(); j++) {

            }
        }
    }*/


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
