package jp.pmw.test_en_revolution.questionnaire.dummy;

import java.util.ArrayList;
import java.util.List;

import jp.pmw.test_en_revolution.questionnaire.Answer;
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

        List<Ask> asks1 = new ArrayList<Ask>();
        List<Answer> answer1 = new ArrayList<Answer>();
        List<Ask> asks2 = new ArrayList<Ask>();
        List<Answer> answer21 = new ArrayList<Answer>();
        List<Answer> answer22 = new ArrayList<Answer>();
        List<Answer> answer23 = new ArrayList<Answer>();
        /*asks.add(new Ask("1","環境汚染をどうにかしなければいけないと思いますか？"));
        asks.add(new Ask("2","あなたがすべきことはどれですか？"));
        asks.add(new Ask("3","授業の内容は面白かったですか?"));
        addItem(new Question("11","環境汚染について",asks));*/

        /*answer1.add(new Answer("はい",22.2f));
        answer1.add(new Answer("いいえ",77.8f));*/
        answer1.add(new Answer("①","#99CC00","はい",22,22));
        answer1.add(new Answer("②","#FFBB33","いいえ",78,78));
        asks1.add(new Ask("1","[スノーデン，NSA，PRISM]のキーワードが想起する事件について，以前から知っていたか？",answer1));
        addItem(new Question("11","1", "スノーデンについて", asks1));

        answer21.add(new Answer("①","#99CC00","10分",135,52));
        answer21.add(new Answer("②","#FFBB33","20分",85,32));
        answer21.add(new Answer("③","#0000FF","30分",30,11));
        answer21.add(new Answer("④","#FF4500","40分",8,3));
        answer21.add(new Answer("⑤","#800080","50分",1,1));
        answer21.add(new Answer("⑥","#ff0000","60分",1,1));
        asks2.add(new Ask("1","今回の討論に自分としては本当に必要だと思う時間はどれほどか.",answer21));

        /*answer22.add(new Answer("#99CC00","1分",3,3));
        answer22.add(new Answer("#FFBB33","3分",15,15));
        answer22.add(new Answer("#0000FF","5分",74,74));
        answer22.add(new Answer("#FF4500","10分",8,8));*/
        answer22.add(new Answer("①","#99CC00","10分",7,2));
        answer22.add(new Answer("②","#FFBB33","20分",19,7));
        answer22.add(new Answer("③","#0000FF","30分",85,33));
        answer22.add(new Answer("④","#FF4500","40分",104,41));
        answer22.add(new Answer("⑤","#800080","50分",39,16));
        answer22.add(new Answer("⑥","#ff0000","60分",5,1));
        asks2.add(new Ask("2","このレポートに自分としては本当に必要だと思う時間はどれほどか．",answer22));

        answer23.add(new Answer("①","#99CC00","個人",15,5));
        answer23.add(new Answer("②","#FFBB33","個人寄り",42,16));
        answer23.add(new Answer("③","#0000FF","どちらかといえば個人",40,15));
        answer23.add(new Answer("④","#FF4500","どちらかといえば国家",62,24));
        answer23.add(new Answer("⑤","#800080","国家より",84,33));
        answer23.add(new Answer("⑥","#ff0000","国家",17,7));
        asks2.add(new Ask("3","議論の後，個人の立場と国家（政府）の立場，結局あなたはどの立場を支持するか",answer23));
        addItem(new Question("122","2","授業内容について",asks2));

    }

    private static void addItem(Question item) {
        ITEMS.add(item);
    }

  public static List<Question> getQuestions(){
      return ITEMS;
  }


}
