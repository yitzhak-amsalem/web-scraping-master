import java.util.Map;

public class WallaRobot extends BaseRobot {


    public WallaRobot() {
        super("https://www.walla.co.il/");
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
