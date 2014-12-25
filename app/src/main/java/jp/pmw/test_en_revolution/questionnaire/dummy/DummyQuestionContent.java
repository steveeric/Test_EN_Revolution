package jp.pmw.test_en_revolution.questionnaire.dummy;

import java.util.ArrayList;
import java.util.List;

import jp.pmw.test_en_revolution.questionnaire.Ask;
import jp.pmw.test_en_revolution.questionnaire.Question;

/**
 * Created by scr on 2014/12/25.
 * アンケートのダミー内容を格納するクラスです.
 */

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p/>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyQuestionContent {
    /**
     * An array of sample (dummy) items.
     */
    public static List<Question> ITEMS = new ArrayList<Question>();

    static {
        // Add 3 sample items.

        List<Ask> asks = new ArrayList<Ask>();
        asks.add(new Ask("1","環境汚染をどうにかしなければいけないと思いますか？"));
        asks.add(new Ask("2","あなたがすべきことはどれですか？"));
        asks.add(new Ask("3","授業の内容は面白かったですか?"));
        addItem(new Question("11","環境汚染について",asks));

        asks.clear();
        asks.add(new Ask("1","日本の総選挙はおこなうべきだったとおもいますか."));
        asks.add(new Ask("2","あなたが、選ぶ次期日本の首相はどなたですか?"));
        asks.add(new Ask("3","授業の内容は面白かったですか?"));
        addItem(new Question("122","日本情勢について",asks));
    }

    private static void addItem(Question item) {
        ITEMS.add(item);
    }

  public List<Question> getQuestions(){
      return this.ITEMS;
  }


}
