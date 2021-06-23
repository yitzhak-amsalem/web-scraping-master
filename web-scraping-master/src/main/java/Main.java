import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        YnetRobot ynetRobot = new YnetRobot();
        HashMap <String, Integer> wordsMap = new HashMap<>();
        wordsMap = (HashMap<String, Integer>) ynetRobot.getWordsStatistics();
        System.out.println("im: ");
        System.out.println(wordsMap.get("אם"));


    }
}
