import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        WallaRobot wallaRobot = new WallaRobot();
        HashMap <String, Integer> wordsMap;
        wordsMap = (HashMap<String, Integer>) wallaRobot.getWordsStatistics();
        String wordInText = "בנט";
        System.out.println(wordInText + ": " + wordsMap.get(wordInText) + " times in text");
        String textToFind = "לא";
        System.out.println("The number of occurrences of the word '" + textToFind + "' in all titles: " + wallaRobot.countInArticlesTitles(textToFind));
        System.out.println("The main title of long article: " + wallaRobot.getLongestArticleTitle());
        YnetRobot ynetRobot = new YnetRobot();
        System.out.println("The main title of long article: " + ynetRobot.getLongestArticleTitle());




    }
}
