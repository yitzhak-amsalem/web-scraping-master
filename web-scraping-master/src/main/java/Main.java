import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int userNumber = 0;
        do {
            boolean doAgain = false;
            while (!doAgain) {
                try {
                    menu();
                    userNumber = scanner.nextInt();
                    doAgain = true;
                } catch (Exception exception) {
                    exception.printStackTrace();
                    scanner.nextLine();
                    System.out.println("Enter *a number* of the available options!");
                }
            }
            switch (userNumber) {
                case Def.WALLA_MENU:
                    WallaRobot wallaRobot = new WallaRobot();
                    game(wallaRobot);
                    break;
                case Def.YNET_MENU:
                    YnetRobot ynetRobot = new YnetRobot();
                    game(ynetRobot);
                    break;
                case Def.INN_MENU:
                    InnRobot innRobot = new InnRobot();
                    game(innRobot);
                    break;
                default:
                    System.out.println("The number is incorrect. Please try again");
                    break;
            }
        } while (userNumber < Def.WALLA_MENU || userNumber > Def.INN_MENU);


    }
    public static void menu(){
        System.out.println("Welcome to the great guessing game of news sites in Israel.\n" +
                "Select one of the sites you want to scan (1-3): ");
        System.out.println("1. Walla\n2. Ynet\n3. Inn");
    }

    public static <T extends BaseRobot> void game(T bot){
        Scanner scanner = new Scanner(System.in);
        Scanner str = new Scanner(System.in);
        int points = 0;
        String userText;
        int userGuessNumber = 0;
        System.out.println("Please wait... Page loading...");
        HashMap <String, Integer> wordsMap;
        wordsMap = (HashMap<String, Integer>) bot.getWordsStatistics();
        System.out.println("Hint: The title of the longest article is: " + bot.getLongestArticleTitle());
        System.out.println("Guess what are the common words on the site:\n" +
                "You have 5 guesses!");
        boolean moreThanOneWord = true;
        String userGuess = "";
        for (int i = 1; i <= Def.USER_GUESS; i++) {
            while (moreThanOneWord) {
                System.out.println("Guess number " + i + " (Enter just one word): ");
                userGuess = str.nextLine();
                if (userGuess.contains(" ")){
                    moreThanOneWord = true;
                    System.out.println("I only asked for one word!");
                }
                else {
                    moreThanOneWord = false;
                }
            }
            moreThanOneWord = true;
            if (wordsMap.get(userGuess) != null) {
                points += wordsMap.get(userGuess);
                System.out.println(userGuess + " : " + wordsMap.get(userGuess));
            }
        }
        System.out.println("Guess how many times the text you choose appears in the site titles.\n" +
                          "You can select 1-20 character text");
        boolean cheater;
        do {
            System.out.println("Enter your text: ");
            userText = str.nextLine();
            if (userText.length() < Def.MIN_TEXT_LENGTH || userText.length() > Def.MAX_TEXT_LENGTH){
                System.out.println("Are you cheater?! please enter text between 1-20 character!");
                cheater = true;
            }
            else {
                cheater = false;
            }
        } while (cheater);
        boolean doAgain = false;
        while (!doAgain) {
            try {
                System.out.println("How many times does the text you selected appear in the site titles?\n" +
                        "Enter a number: ");
                userGuessNumber = scanner.nextInt();
                doAgain = true;
            } catch (Exception exception) {
                exception.printStackTrace();
                scanner.nextLine();
                System.out.println("Enter *a number* of the available options!");
            }
        }
        System.out.println("Please wait... Page loading...");
        int appearanceNumber = bot.countInArticlesTitles(userText);
        if (userGuessNumber <= appearanceNumber + Def.REASONABLE_DISTANCE && userGuessNumber >= appearanceNumber - Def.REASONABLE_DISTANCE){
            System.out.println("Well done! You won the big prize");
            points += Def.BIG_PRIZE;
            if (userGuessNumber == appearanceNumber){
                System.out.println("You guessed it!");
            }
            else {
                System.out.println("A close guess! it's was: " + appearanceNumber);
            }
        }
        else {
            System.out.println("maybe next time... it's was: " + appearanceNumber);
        }
        if (points < Def.MINIMUM_EXPECTED){
            System.out.println("Your points: " + points + " Too bad, you could have put in more effort");
        }
        else {
            System.out.println("Your points: " + points + " Congratulations! Great result!");
        }
    }
}