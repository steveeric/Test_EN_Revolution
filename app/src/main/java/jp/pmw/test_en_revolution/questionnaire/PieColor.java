package jp.pmw.test_en_revolution.questionnaire;

import android.content.Context;

import jp.pmw.test_en_revolution.R;

/**
 * Created by scr on 2014/12/30.
 * 円グラフで使用する色を管理するクラスです.
 * 5色までしかありません.
 */
public class PieColor {
    private int[] COLORS = new int[5];
    private static final PieColor instance = new PieColor();

    private PieColor() {
    }

    public static PieColor getInstance() {
        return PieColor.instance;
    }


    public int[] pieColors(Context context){
        int first = context.getResources().getColor(R.color.firstPieGraph);
        int second = context.getResources().getColor(R.color.secondPieGraph);
        int third = context.getResources().getColor(R.color.thirdPieGraph);
        int fourth = context.getResources().getColor(R.color.fourthPieGraph);
        int fifth = context.getResources().getColor(R.color.fifthPieGraph);

        this.COLORS[0] = first;
        this.COLORS[1] = second;
        this.COLORS[2] = third;
        this.COLORS[3] = fourth;
        this.COLORS[4] = fifth;

        return COLORS;
    }
}
