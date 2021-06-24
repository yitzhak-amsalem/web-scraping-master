import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("ENTER NUM: ");
        int userNumber = scanner.nextInt();
        switch (userNumber){
            case 1:
                WallaRobot wallaRobot = new WallaRobot();
                game(wallaRobot);
                break;
            case 2:
                YnetRobot ynetRobot = new YnetRobot();
                game(ynetRobot);
                break;
            case 3:
                InnRobot innRobot = new InnRobot();
                game(innRobot);
                break;
        }


    }
    public static <T extends BaseRobot> void game(T bot){
        HashMap <String, Integer> wordsMap;
        wordsMap = (HashMap<String, Integer>) bot.getWordsStatistics();
        String wordInText = "אביתר";
        System.out.println(wordInText + ": " + wordsMap.get(wordInText) + " times in text");
        String textToFind = "את זכות השיבה";
        System.out.println("The number of occurrences of the word '" + textToFind + "' in all titles: " + bot.countInArticlesTitles(textToFind));
        System.out.println("The main title of long article: " + bot.getLongestArticleTitle());


    }
}
