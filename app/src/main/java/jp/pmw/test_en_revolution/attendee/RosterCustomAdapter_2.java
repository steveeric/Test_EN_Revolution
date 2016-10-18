package jp.pmw.test_en_revolution.attendee;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
        import android.widget.TextView;


import org.w3c.dom.Text;

import jp.pmw.test_en_revolution.AttendanceObject;
import jp.pmw.test_en_revolution.CustomDialogFragment;
import jp.pmw.test_en_revolution.MainActivity;
import jp.pmw.test_en_revolution.R;
import jp.pmw.test_en_revolution.StudentObject;
import jp.pmw.test_en_revolution.TransmitStateObject;
import jp.pmw.test_en_revolution.dialog.StudentInfoDialogFragnemt;

/**
 * Created by si on 2016/01/29.
 */
public class RosterCustomAdapter_2 extends ArrayAdapter<StudentObject> {

        private Context _context;
        private int _textViewResourceId;
        private LayoutInflater _inflater;

        public static final int ALL_LAYOUT = 0;
        public static final int ONLY_STUDENT_NAME_LAYOUT = 1;
        //      送信状態オブジェクト
        private TransmitStateObject _transmitStateObject;

        /**
         * RosterCustomAdapter_1は、2か所のフラグメントが使用します.
         * requestNumberが
         * 0 : ESL忘れやメッセージレイアウトあり
         * 1 : 学籍番号と名前だけレイアウト
         **/
        private int _requestNumber;

        public RosterCustomAdapter_2(Context context, int resourceId, TransmitStateObject tso, StudentObject[] items, int requestNumber) {
                super(context, resourceId, items);
                _context = context;
                _textViewResourceId = resourceId;
                _inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                _requestNumber = requestNumber;
                _transmitStateObject = tso;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
                ViewHolder holder = null;
                View view = convertView;

                //      送信状態
                TransmitStateObject tso = this._transmitStateObject;
                //      ダミー(出席後)
                //tso.setAttendanceTranmitEndTime("");

                //      学生情報
                StudentObject so = getItem(position);
                //      出欠情報
                //AttendanceObject ao = so.getAttendanceObject();

                if(view == null){
                        view = _inflater.inflate(R.layout.row_roster_item_4, null);
                        holder = new ViewHolder(view);
                        view.setTag(holder);
                }else{
                        holder = (ViewHolder)view.getTag();
                }

                //      メッセージ非表示
                holder.messageTextView.setVisibility(View.INVISIBLE);
                if(so.getMessageObject().getMessageContent()!=null){
                        //      メッセージあれば
                        holder.messageTextView.setVisibility(View.VISIBLE);
                }

                //      メッセージとESL忘れ非表示
                initForgotESL(holder, so);

                //      送信信号機の初期化
                initTranmitSignal(holder);

                //      送信信号機に色をセットする.
                setColorOfSignal(holder, so);

                //


                //holder.subContentLinearLayout.setVisibility(View.INVISIBLE);
                //holder.forgotESLTextView.setVisibility(View.INVISIBLE);
                //holder.messageTextView.setVisibility(View.INVISIBLE);

                //holder.attendanceStateColorTextView.setVisibility(View.GONE);

                return view;
        }
        /**
         * Created by si on 2016/02/01.
         * setColorOfSignalメソッド
         * 送信シグナルの色をセットする.
         * **/
        private void setColorOfSignal(ViewHolder holder, StudentObject so){
                TransmitStateObject tso = this._transmitStateObject;
                AttendanceObject ao     = so.getAttendanceObject();

                int nowTransmitStatus = tso.getNowTransmitStatus();
                switch (nowTransmitStatus){
                        case TransmitStateObject.ENTERING_A_ROOM_STATUS:
                                firstTransmitRound(holder, ao);
                                chkBeforeAttendanceSetName(holder, so);
                                break;
                        case TransmitStateObject.ATTENDANCE_END_TIME_STATUS:
                                secondTransmitRound(holder, ao);
                                chkAfterAttendanceSetName(holder, so);
                                break;
                        case TransmitStateObject.RE_ATTENDANCE_END_TIME_STATUS:
                                thirdTransmitRound(holder, ao, tso);
                                chkAfterAttendanceSetName(holder, so);
                                break;
                        case TransmitStateObject.UNDO_TRANMIT_END_TIME_STATUS:
                                fourthTransmitRound(holder, ao, tso);
                                chkAfterAttendanceSetName(holder, so);
                                break;
                }
        }
        /**
         * Created by si on 2016/02/01.
         * initForgotESL
         * ESL忘れレイアウトをどうするか決める
         * **/
        private void initForgotESL(ViewHolder holder, StudentObject so){
                //      出欠情報
                AttendanceObject ao = so.getAttendanceObject();
                //      レイアウト初期動作
                holder.forgotESLTextView.setVisibility(View.INVISIBLE);
                //      ESL忘れ
                if(ao.getForgotApplyTime() !=null ){
                        holder.forgotESLTextView.setVisibility(View.VISIBLE);
                }
        }

        /**
         * Created by si on 2016/02/01.
         * initTranmitSignal
         * 送信信号の色を初期化.
         * **/
        private void initTranmitSignal(ViewHolder holder) {
                //int color = getColor(R.color.gray);
                Drawable signalGrayDrawable = ContextCompat.getDrawable(_context, R.drawable.signal_gray);
                holder.firstSignalTextView.setBackgroundDrawable(signalGrayDrawable);
                holder.secondSignalTextView.setBackgroundDrawable(signalGrayDrawable);
                holder.thirdSignalTextView.setBackgroundDrawable(signalGrayDrawable);
                holder.fourthSignalTextView.setBackgroundDrawable(signalGrayDrawable);

                /*holder.firstSignalTextView.setBackgroundColor(color);
                holder.secondSignalTextView.setBackgroundColor(color);
                holder.thirdSignalTextView.setBackgroundColor(color);
                holder.fourthSignalTextView.setBackgroundColor(color);*/
        }

        /**
         * Created by si on 2016/02/01.
         * firstTransmitRound
         * 初回送信.
         * **/
        private void firstTransmitRound(ViewHolder holder,AttendanceObject ao){
                if( ao.getFirstAccessTime() != null ){
                        Drawable r = ContextCompat.getDrawable(_context, R.drawable.signal_green);
                        holder.firstSignalTextView.setBackgroundDrawable(r);
                }

                /*if(ao.getFirstAccessTime() != null && ao.getForgotApplyTime() == null){
                        //      入室検出
                        //int color = getColor(R.color.green);
                        //holder.firstSignalTextView.setBackgroundColor(color);
                        Drawable r = ContextCompat.getDrawable(_context, R.drawable.signal_green);
                        holder.firstSignalTextView.setBackgroundDrawable(r);
                }else if(ao.getForgotApplyTime() != null){
                        //      ESL忘れ
                        forgotESLSignal(holder);
                }*/
        }
        /**
         * Created by si on 2016/06/28.
         * firstTransmitRoundNotAckメソッド
         * 入室送信時にACKがなかったので、ACKインジケーターの左から一つ目を赤色にする.
         * **/
        void firstTransmitRoundNotAck(ViewHolder holder,AttendanceObject ao){
                if( ao.getFirstAccessTime() == null ){
                        Drawable r = ContextCompat.getDrawable(_context, R.drawable.signal_red);
                        holder.firstSignalTextView.setBackgroundDrawable(r);
                }
        }

        /**
         * Created by si on 2016/02/01.
         * forgotESLSignal
         * ESL忘れシグナル
         * **/
        /*private void forgotESLSignal(ViewHolder holder){
                //      ESL忘れ
                int color = getColor(R.color.tin_green);
                holder.firstSignalTextView.setBackgroundColor(color);
                holder.secondSignalTextView.setBackgroundColor(color);
                /*Drawable r = ContextCompat.getDrawable(_context, R.drawable.signal_tin_green);
                holder.firstSignalTextView.setBackgroundDrawable(r);
                holder.secondSignalTextView.setBackgroundDrawable(r);
        }*/


        /**
         * Created by si on 2016/02/01.
         * secondTransmitRound
         * 2回目送信(そのだ教授)後.
         * **/
        private void secondTransmitRound(ViewHolder holder,AttendanceObject ao){
                //      一つ目ACKありシグナル
                firstTransmitRound(holder,ao);
                //      一つ目ACKなしシグナル
                firstTransmitRoundNotAck(holder, ao);

                //      IR1のACKありで、IR2のACK無し
                //      2016年10月17日に抜けていることに気付きました.
                if(ao.getFirstAccessTime() != null){
                        Drawable signalReadDrawable = ContextCompat.getDrawable(_context, R.drawable.signal_red);
                        holder.secondSignalTextView.setBackgroundDrawable(signalReadDrawable);
                }

                //      IR1のACKありで、IR2もACKあり
                if( ao.getFirstAccessTime() != null
                        && ao.getManualRequestAttendance() == AttendanceObject.MANUAL_NOT
                        && ao.getAttendanceTime() != null ){
                        //      IR2のACK応答あり
                        Drawable signalReadDrawable = ContextCompat.getDrawable(_context, R.drawable.signal_green);
                        holder.secondSignalTextView.setBackgroundDrawable(signalReadDrawable);
                }
                //      IR1のACKありで、IR2をandroidタブレットから出席
                if( ao.getFirstAccessTime() != null
                        && ao.getManualRequestAttendance() == AttendanceObject.MANUAL_ATTENDANCE
                        && ao.getAttendanceTime() != null ){
                        //      IR2の送信したがIR2ACKなし
                        Drawable signalReadDrawable = ContextCompat.getDrawable(_context, R.drawable.signal_tie_green);
                        holder.secondSignalTextView.setBackgroundDrawable(signalReadDrawable);
                }
        }

        /**
         * Created by si on 2016/02/04.
         * thirdTransmitRound
         * 出席調査(そのだ教授)と授業最終ACK確認(かなえちゃん)の間のACK状態を表示します.
         * **/
        private void thirdTransmitRound(ViewHolder holder,AttendanceObject ao, TransmitStateObject tso){
                if(tso.getReAttendanceEndTime() == null){
                        //      再出席調査を行っていないのでリターン
                        return;
                }
                //      以下以降は、在室調査確認を行っている.

                //      一つ目のACKシグナルを描く
                firstTransmitRound(holder, ao);
                //      二つ目のACKシグナルを描く
                secondTransmitRound(holder, ao);
                //      ESL忘れは処理をさせない
                if(ao.getForgotApplyTime() != null){return;}
                //      デフォルトではグレーシグナルを描く
                Drawable signalDrawable = ContextCompat.getDrawable(_context, R.drawable.signal_gray);
                //
                if(ao.getAttendanceTime() != null && ao.getReAttendancetime() == null){
                        //      出席認定ACKあり かつ　在室調査ACKなし
                        signalDrawable  =       ContextCompat.getDrawable(_context, R.drawable.signal_red);
                }else if(ao.getAttendanceTime() != null || ao.getReAttendancetime() != null){
                        //      出席認定ACKあり かつ　在室調査ACKあり
                        signalDrawable  =       ContextCompat.getDrawable(_context, R.drawable.signal_green);
                        /*if(ao.getManualRequestReAttendance() == StudentInfoDialogFragnemt.MANUAL_NOT_REQUEST){
                                //      ACKで再出席調査
                                signalDrawable  =       ContextCompat.getDrawable(_context, R.drawable.signal_green);
                        }else{
                                //      手動で再出席調査
                                signalDrawable  =       ContextCompat.getDrawable(_context, R.drawable.signal_tie_green);
                        }*/
                }
                holder.thirdSignalTextView.setBackgroundDrawable(signalDrawable);
        }

        /**
         * Created by si on 2016/02/02.
         * fourthTransmitRoundメソッド
         * 左から4つめのシグナル
         * **/
        private void fourthTransmitRound(ViewHolder holder,AttendanceObject ao, TransmitStateObject tso){
                //      2つめのシグナル
                secondTransmitRound(holder,ao);
                //      3つめのシグナル
                thirdTransmitRound(holder, ao,  tso);
                //      ESL忘れは処理をさせない
                if(ao.getForgotApplyTime() != null){return;}
                //      出席・遅刻のどちらかでない場合は、グレーのままにする.
                if(ao.getAttendanceTime() == null){return;}
                //      デフォルトではグレーシグナルを描く
                Drawable signalDrawable = ContextCompat.getDrawable(_context, R.drawable.signal_gray);
                //
                if(this._transmitStateObject.getUndoTransmitEndTime() != null){
                        //      授業最終送信が終了している
                        if(ao.getAttendanceTime() != null && ao.getClassLastAckTime() != null){
                                //      出席認定ACKあり かつ 授業終了ACKあり.
                                signalDrawable = ContextCompat.getDrawable(_context, R.drawable.signal_green);
                        }else{
                                //      出席認定ACKあり かつ 授業終了ACKあり.
                                signalDrawable = ContextCompat.getDrawable(_context, R.drawable.signal_red);
                        }
                }
                //      4つ目のシグナルを描く
                holder.fourthSignalTextView.setBackgroundDrawable(signalDrawable);
        }

        /**
         * Created by si on 2016/02/01.
         * chkBeforeAttendanceSetName
         * 出席確認前に名前をセット
         * **/
        private void chkBeforeAttendanceSetName(ViewHolder holder,StudentObject so){
                holder.orijinalStudentIdTextView.setText(so.getStudentIdNumber());
                holder.furiganaTextView.setText(so.getFurigana());
                holder.fullNameTextView.setText(so.getFullName());
        }
        /**
         * Created by si on 2016/02/01.
         * chkAfterAttendanceSetName
         * 出席確認後に名前をセット
         * **/
        private void chkAfterAttendanceSetName(ViewHolder holder,StudentObject so){
                MainActivity mainActivity = (MainActivity)_context;
                TransmitStateObject tso = mainActivity.getClassObject().getTransmitStateObject();
               int color = so.getAttendanceObject().getAttendanceStateColor(mainActivity, tso);

                //      学生情報をテキストビューにセット
                chkBeforeAttendanceSetName(holder, so);

                holder.orijinalStudentIdTextView.setTextColor(color);
                holder.furiganaTextView.setTextColor(color);
                holder.fullNameTextView.setTextColor(color);

        }

        private static class ViewHolder {
                //      1番目信号
                TextView firstSignalTextView;
                //      2番目信号
                TextView secondSignalTextView;
                //      3番目信号
                TextView thirdSignalTextView;
                //      4番目信号
                TextView fourthSignalTextView;

                //      学籍番号
                TextView orijinalStudentIdTextView;
                //      フリガナ
                TextView furiganaTextView;
                //      氏名
                TextView fullNameTextView;
                TextView attendanceStateColorTextView;
                LinearLayout subContentLinearLayout;
                ImageView forgotESLTextView;
                ImageView messageTextView;

                public ViewHolder(View view) {
                        //      1番目信号
                        this.firstSignalTextView = (TextView) view.findViewById(R.id.roster_first_signal_textView);
                        //      2番目信号
                        this.secondSignalTextView = (TextView) view.findViewById(R.id.roster_second_signal_textView);
                        //      3番目信号
                        this.thirdSignalTextView = (TextView) view.findViewById(R.id.roster_third_signal_textView);
                        //      4番目信号
                        this.fourthSignalTextView = (TextView) view.findViewById(R.id.roster_fourth_signal_textView);

                        this.orijinalStudentIdTextView = (TextView) view.findViewById(R.id.roster_orijinal_student_id_textView);
                        this.furiganaTextView = (TextView) view.findViewById(R.id.roster_furigana_textView);
                        this.fullNameTextView = (TextView) view.findViewById(R.id.roster_full_name_textView);
                        //this.attendanceStateColorTextView = (TextView) view.findViewById(R.id.roster_attendance_state_color_textView);
                        //this.subContentLinearLayout = (LinearLayout) view.findViewById(R.id.roster_attendance_sub_content_linearLayout);
                        this.forgotESLTextView = (ImageView) view.findViewById(R.id.roster_forgot_esl_textView);
                        this.messageTextView = (ImageView) view.findViewById(R.id.roster_have_messge_textView);
                }
        }
}