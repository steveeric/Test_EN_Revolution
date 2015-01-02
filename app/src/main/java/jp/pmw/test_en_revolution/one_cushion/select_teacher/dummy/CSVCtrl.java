package jp.pmw.test_en_revolution.one_cushion.select_teacher.dummy;

/**
 * Created by scr on 2015/01/01.
 */

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/***************************************************************************
 * ＣＳＶコントロールクラス
 ***************************************************************************/
public class CSVCtrl
{
    /***************************************************************************
     * ＣＳＶファイルから読み込んだデータリストを取得
     *
     * @param csContext        コンテキスト
     * @param strCsvFileName   ＣＳＶファイル名
     * @return 読み込んだＣＳＶデータリスト
     *
     * @author k0j1
     ***************************************************************************/
    static public List<StringTokenizer> readCSV(Context csContext, String strCsvFileName)
    {
        InputStream csInputStream = null;
        BufferedReader csBufReader = null;
        List<StringTokenizer> sCsvList = new ArrayList<StringTokenizer>();

        try
        {
            AssetManager csAsset = csContext.getResources().getAssets();
            csInputStream = csAsset.open(strCsvFileName);
            csBufReader = new BufferedReader(new InputStreamReader(csInputStream));

            // タイトル処理
            // タイトルは読み捨てる
            csBufReader.readLine();

            // データ処理
            {
                // 最終行まで読み込む
                for( String line = ""; (line = csBufReader.readLine()) != null; )
                {

                    // 1行をデータの要素に分割
                    StringTokenizer csStringToken = new StringTokenizer(line, ",");

                    // リストに保持
                    sCsvList.add(csStringToken);
                }
                csBufReader.close();
            }

        } catch (FileNotFoundException e) {
            // Fileオブジェクト生成時の例外捕捉
            e.printStackTrace();
        } catch (IOException e) {
            // BufferedReaderオブジェクトのクローズ時の例外捕捉
            e.printStackTrace();
        }

        return sCsvList;
    }
}
