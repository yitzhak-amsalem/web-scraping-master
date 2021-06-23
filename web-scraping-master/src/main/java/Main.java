import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        YnetRobot ynetRobot = new YnetRobot();
        HashMap <String, Integer> wordsMap = new HashMap<>();
        wordsMap = (HashMap<String, Integer>) ynetRobot.getWordsStatistics();
        String wordInText = "קורונה";
        System.out.println(wordInText + ": " + wordsMap.get(wordInText) + " times in text");
        String textToFind = "מאומתים";
        System.out.println("The number of occurrences of the word '" + textToFind + "' in all titles: " + ynetRobot.countInArticlesTitles(textToFind));
        System.out.println("The main title of long article: " + ynetRobot.getLongestArticleTitle());


    }
}
