package jp.pmw.test_en_revolution;

import android.os.Bundle;

import jp.pmw.test_en_revolution.dialog.StudentInfoDialogFragnemt;

/**
 * Created by si on 2016/02/16.
 */
public class StudentInfoCustomDialog {
    StudentInfoCustomDialog(){

    }

    /*public void show(MyMainFragment fragment){
        MainActivity activity = (MainActivity)fragment.getActivity();
        String sameClassNumber  =   activity.getClassObject().getSameClassNumber();
        TransmitStateObject tso = activity.getClassObject().getTransmitStateObject();
        StudentInfoDialogFragnemt dialogFragment = StudentInfoDialogFragnemt.newInstance();
        if(dialogFragment != null){
            dialogFragment.setTargetFragment(fragment, 0);
            Bundle bundle = new Bundle();
            bundle.putSerializable(StudentInfoDialogFragnemt.SAME_CLASS_NUMBER, sameClassNumber);
            bundle.putSerializable(StudentInfoDialogFragnemt.STUDENT_INFO, so);
            bundle.putSerializable(StudentInfoDialogFragnemt.TRANMIST_STAT_IFNO, tso);
            dialogFragment.setArguments(bundle);
            dialogFragment.setAttendeeFragment(this);
            dialogFragment.show(activity.getSupportFragmentManager(), StudentInfoDialogFragnemt.STUDENT_INFO_DIALOG_FRAGMENT);
        }
    }*/

    public StudentInfoDialogFragnemt studentInfoDialogFragnemt;
    public void showForAttendeeFragment(AttendeeFragment fragment,StudentObject so){
        MainActivity activity = (MainActivity)fragment.getActivity();
        String sameClassNumber  =   activity.getClassObject().getSameClassNumber();
        TransmitStateObject tso = activity.getClassObject().getTransmitStateObject();
        studentInfoDialogFragnemt = StudentInfoDialogFragnemt.newInstance();
        if(studentInfoDialogFragnemt != null){
            studentInfoDialogFragnemt.setTargetFragment(fragment, 0);
            Bundle bundle = new Bundle();
            bundle.putSerializable(StudentInfoDialogFragnemt.SAME_CLASS_NUMBER, sameClassNumber);
            bundle.putSerializable(StudentInfoDialogFragnemt.STUDENT_INFO, so);
            bundle.putSerializable(StudentInfoDialogFragnemt.TRANMIST_STAT_IFNO, tso);
            studentInfoDialogFragnemt.setArguments(bundle);
            studentInfoDialogFragnemt.setAttendeeFragment(fragment);
            studentInfoDialogFragnemt.show(activity.getSupportFragmentManager(), StudentInfoDialogFragnemt.STUDENT_INFO_DIALOG_FRAGMENT);
        }
    }



    public void showForSeatSituationFragment(SeatSituationFragment fragment,StudentObject so){
        MainActivity activity = (MainActivity)fragment.getActivity();
        String sameClassNumber  =   activity.getClassObject().getSameClassNumber();
        TransmitStateObject tso = activity.getClassObject().getTransmitStateObject();
        studentInfoDialogFragnemt = StudentInfoDialogFragnemt.newInstance();
        if(studentInfoDialogFragnemt != null){
            studentInfoDialogFragnemt.setTargetFragment(fragment, 0);
            Bundle bundle = new Bundle();
            bundle.putSerializable(StudentInfoDialogFragnemt.SAME_CLASS_NUMBER, sameClassNumber);
            bundle.putSerializable(StudentInfoDialogFragnemt.STUDENT_INFO, so);
            bundle.putSerializable(StudentInfoDialogFragnemt.TRANMIST_STAT_IFNO, tso);
            studentInfoDialogFragnemt.setArguments(bundle);
            studentInfoDialogFragnemt.setSeatSituationFragment(fragment);
            studentInfoDialogFragnemt.show(activity.getSupportFragmentManager(), StudentInfoDialogFragnemt.STUDENT_INFO_DIALOG_FRAGMENT);
        }
    }
    //  ダイアログフラグメントを閉じる
    public void dismiss(){
        studentInfoDialogFragnemt.dismiss();
    }

}
