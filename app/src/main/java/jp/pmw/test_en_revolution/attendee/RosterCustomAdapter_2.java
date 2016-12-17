package jp.pmw.test_en_revolution.attendee;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
        import android.widget.TextView;


import io.realm.Realm;
import io.realm.RealmResults;
import jp.pmw.test_en_revolution.AttendanceObject;
import jp.pmw.test_en_revolution.MainActivity;
import jp.pmw.test_en_revolution.R;
import jp.pmw.test_en_revolution.StudentObject;
import jp.pmw.test_en_revolution.TransmitStateObject;

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
        Handler handler = new Handler(Looper.getMainLooper()); // (1)
        int counter = 0;
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
                ViewHolder holder = null;
                View view = convertView;
                //      学生情報
                StudentObject so = getItem(position);
                AttendanceObject ao = so.getAttendanceObject();
                if(view == null){
                        view = _inflater.inflate(R.layout.row_roster_item_5, null);
                        holder = new ViewHolder(view);
                        view.setTag(holder);
                }else{
                        holder = (ViewHolder)view.getTag();
                }
                String url = so.mFaceUrl;
                holder.mLoaderFaceImageView.setUrl(url);
                holder.mLoaderFaceImageView.setTag(url);
                Bitmap bitmap = null;
                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                RealmResults<FaceImageRealmObject> results = realm.where(FaceImageRealmObject.class)
                        .equalTo("url", url)
                        .findAll();
                int registeredCount = results.size();
                if(registeredCount == 1){
                        byte[] faceImageByts = results.get(0).getFaceImage();
                        if( faceImageByts != null ){
                                bitmap = BitmapFactory.decodeByteArray(faceImageByts, 0, faceImageByts.length);
                        }
                }
                realm.commitTransaction();
                realm.close();
                if( bitmap != null ){
                        holder.mLoaderFaceImageView.setImageBitmap( bitmap );
                        holder.mLoaderFaceImageView.setVisibility(View.VISIBLE);
                }else{
                        Drawable drawable = _context.getResources().getDrawable(R.drawable.img_sonoda_professor);
                        holder.mLoaderFaceImageView.setImageDrawable(drawable);
                        FaceImageTask fit = new FaceImageTask(getContext(), holder.mLoaderFaceImageView, url);
                        fit.execute(url);
                }

                //      学籍番号とフリガナと氏名
                chkAfterAttendanceSetName(holder, so);

                //      メッセージ非表示
                holder.messageTextView.setVisibility(View.INVISIBLE);
                if(so.getMessageObject().getMessageContent()!=null){
                        //      メッセージあれば
                        holder.messageTextView.setVisibility(View.VISIBLE);
                }

                //      メッセージとESL忘れ非表示
                initForgotESL(holder, so);

                //      送信信号機に色をセットする.
                setColorOfSignal(holder, ao);
                return view;
        }
        /**
         * Created by si on 2016/02/01.
         * setColorOfSignalメソッド
         * 送信シグナルの色をセットする.
         * **/
        private void setColorOfSignal(ViewHolder holder, AttendanceObject ao){
                Indicator indicator = ao.mIndicator;
                //      座席指定シグナル
                Drawable seatIndicatorDrawable = IndicatorEnum.getSignalDrawable(_context, indicator.mSeat);
                holder.firstSignalTextView.setBackgroundDrawable( seatIndicatorDrawable );
                //      出席認定シグナル
                Drawable attendanceIndicatorDrawable = IndicatorEnum.getSignalDrawable(_context, indicator.mAttendance);
                holder.secondSignalTextView.setBackgroundDrawable( attendanceIndicatorDrawable );
                //      在室確認シグナル
                Drawable reAttendanceIndicatorDrawable = IndicatorEnum.getSignalDrawable(_context, indicator.mReAttendance);
                holder.thirdSignalTextView.setBackgroundDrawable( reAttendanceIndicatorDrawable );
                //      プライバシーシグナル
                Drawable privacyIndicatorDrawable = IndicatorEnum.getSignalDrawable(_context, indicator.mPrivacy);
                holder.fourthSignalTextView.setBackgroundDrawable( privacyIndicatorDrawable );
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
                LoaderFaceImageView mLoaderFaceImageView;
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
                        this.mLoaderFaceImageView = (LoaderFaceImageView) view.findViewById(R.id.roster_face_iv);
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