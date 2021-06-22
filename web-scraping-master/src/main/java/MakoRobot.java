import java.util.Map;

public class MakoRobot extends BaseRobot {


    public MakoRobot() {
        super("https://www.mako.co.il/");
    }

    @Override
    public Map<String, Integer> getWordsStatistics() {
        return null;
    }

    @Override
    public int countInArticlesTitles(String text) {
        return 0;
    }

    @Override
    public String getLongestArticleTitle() {
        return null;
    }
}
