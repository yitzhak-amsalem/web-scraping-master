import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class InnRobot extends BaseRobot {


    public InnRobot() {
        super("https://www.inn.co.il/");
    }
    @Override
    public Map<String, Integer> getWordsStatistics() {
        Map<String, Integer> wordsMap = new HashMap<>();
        int size = 0;
        try {
            Document website = Jsoup.connect(getRootWebsiteUrl()).get();
            Elements allLinks = website.getElementsByAttribute("href");
            System.out.println("all links: " + allLinks.size());

            StringBuilder allText = new StringBuilder();
            for (int i = 0; i < allLinks.size(); i++){
                Element linkElement = allLinks.get(i);
                String preLink = "https://www.inn.co.il/";
                String link = linkElement.attr("href");
                String linkToScraping = preLink + link;
                if (i != allLinks.size()-1) {
                    if (!link.equals(allLinks.get(i + 1).attr("href"))
                            && scanLink(link) == 2 && (link.contains("/news/")) ) {
                        Document innPage = Jsoup.connect(linkToScraping).get();
                        Elements mainTitle = innPage.getElementsByTag("h1");
                        Elements subTitle = innPage.getElementsByTag("h2");
                        Elements textPage = innPage.getElementsByTag("p");
                        allText.append(mainTitle.text()).append(" ");
                        allText.append(subTitle.text()).append(" ");
                        for (Element element : textPage) {
                            allText.append(element.text()).append(" ");
                        }
                    }
                }
            }
            System.out.println(allText.length());


            String word = "";
            for (int i = 0; i<allText.length(); i++){
                char chekWord = allText.charAt(i);
                if ((chekWord > 1487 && chekWord < 1515) || chekWord == 34 ||
                        (chekWord >= '0' && chekWord <= '9') || (chekWord >= 'A' && chekWord <= 'Z') || (chekWord >= 'a' && chekWord <= 'z')){
                    if (!(chekWord == 34)) {
                        word += chekWord;
                    }
                }
                else {
                    if (word.length() != 0) {
                        if (wordsMap.get(word) != null) {
                            Integer value = wordsMap.get(word) + 1;
                            wordsMap.put(word, value);
                        }
                        else {
                            wordsMap.put(word, 1);
                        }
                    }
                    word = "";
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return wordsMap;
    }

    public int scanLink (String linkToScan){
        int count = 0;
        for (int i = 0; i < linkToScan.length(); i++){
            char check = linkToScan.charAt(i);
            if (check == '/'){
                count++;
            }
        }
        return count;
    }

    @Override
    public int countInArticlesTitles(String text) {
        int count = 0;
        StringBuilder allTitlesText = new StringBuilder();
        try {
            Document website = Jsoup.connect(getRootWebsiteUrl()).get();
            Elements allLinks = website.getElementsByAttribute("href");

            for (int i = 0; i < allLinks.size(); i++){
                Element linkElement = allLinks.get(i);
                String preLink = "https://www.inn.co.il/";
                String link = linkElement.attr("href");
                String linkToScraping = preLink + link;
                if (i != allLinks.size()-1) {
                    if (!link.equals(allLinks.get(i + 1).attr("href"))
                            && scanLink(link) == 2 && (link.contains("/news/")) ) {
                        Document innPage = Jsoup.connect(linkToScraping).get();
                        Elements mainTitle = innPage.getElementsByTag("h1");
                        Elements subTitle = innPage.getElementsByTag("h2");
                        allTitlesText.append(mainTitle.text()).append(" ");
                        allTitlesText.append(subTitle.text()).append(" ");
                    }
                }
            }
            System.out.println("all titles of Articles text length: " + allTitlesText.length());

            String word = "";
            for (int i = 0; i < allTitlesText.length(); i++){
                char chekWord = allTitlesText.charAt(i);
                if ((chekWord > 1487 && chekWord < 1515) || chekWord == 34 || chekWord == ' ' ||
                        (chekWord >= '0' && chekWord <= '9') || (chekWord >= 'A' && chekWord <= 'Z') || (chekWord >= 'a' && chekWord <= 'z')){
                    if (!(chekWord == 34)) {
                        word += chekWord;
                        if (word.contains(text)){
                            count++;
                            word = "";
                        }
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return count;
    }

    @Override
    public String getLongestArticleTitle() {
        String longestArticleTitle = "";
        try {
            Document website = Jsoup.connect(getRootWebsiteUrl()).get();
            Elements allLinks = website.getElementsByAttribute("href");

            int previousLength = 0;
            int currentLength;
            int mostChars = 0;
            String linkOfLongestArticle = "";
            for (int i = 0; i < allLinks.size(); i++) {
                StringBuilder allArticleText = new StringBuilder();
                Element linkElement = allLinks.get(i);
                String preLink = "https://www.inn.co.il/";
                String link = linkElement.attr("href");
                String linkToScraping = preLink + link;
                if (i != allLinks.size()-1) {
                    if (!link.equals(allLinks.get(i + 1).attr("href"))
                            && scanLink(link) == 2 && (link.contains("/news/")) ) {
                        Document innPage = Jsoup.connect(linkToScraping).get();
                        Elements textPage = innPage.getElementsByTag("p");
                        for (Element element : textPage) {
                            allArticleText.append(element.text()).append(" ");
                        }
                        currentLength = allArticleText.length();
                        if (currentLength > previousLength) {
                            linkOfLongestArticle = preLink + link;
                            mostChars = currentLength;
                            previousLength = currentLength;
                        }
                    }
                }
            }
            Document wallaPage = Jsoup.connect(linkOfLongestArticle).get();
            Elements mainTitle = wallaPage.getElementsByTag("h1");
            longestArticleTitle = mainTitle.text();
            System.out.println("The link of the long article: " + linkOfLongestArticle);
            System.out.println("The number of chars of the long article: " + mostChars);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return longestArticleTitle;
    }
}
