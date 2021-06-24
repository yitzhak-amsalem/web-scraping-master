import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class YnetRobot extends BaseRobot {


    public YnetRobot() {
        super("https://www.ynet.co.il/home/0,7340,L-8,00.html");
    }

    @Override
    public Map<String, Integer> getWordsStatistics() {
        Map<String, Integer> wordsMap = new HashMap<>();
        try {
            Document website = Jsoup.connect(getRootWebsiteUrl()).get();
            Elements allLinks = website.getElementsByAttribute("href");

            StringBuilder allText = new StringBuilder();
            for (int i = 0; i < allLinks.size(); i++){
                Element linkElement = allLinks.get(i);
                String link = linkElement.attr("href");
                if (i != allLinks.size() - 1) {
                    if (link.contains("https://www.ynet.co.il/") && !link.equals(allLinks.get(i + 1).attr("href")) && scanLink(link) > Def.SLASH_PAGE_YNET) {
                        Document ynetPage = Jsoup.connect(link).get();
                        Elements mainTitle = ynetPage.getElementsByClass("mainTitle");
                        Elements subTitle = ynetPage.getElementsByClass("subTitle");
                        Elements textPage = ynetPage.getElementsByAttribute("data-text");
                        allText.append(mainTitle.text());
                        allText.append(subTitle.text());
                        for (Element element : textPage) {
                            allText.append(element.text());
                        }
                    }
                }
            }

            String word = "";
            for (int i = 0; i<allText.length(); i++){
                char checkWord = allText.charAt(i);
                if ((checkWord >= 'א' && checkWord <= 'ת') || checkWord == '"' ||
                        (checkWord >= '0' && checkWord <= '9') || (checkWord >= 'A' && checkWord <= 'Z') || (checkWord >= 'a' && checkWord <= 'z')){
                    if (!(checkWord == '"')) {
                        word += checkWord;
                    }
                }
                else {
                    if (word.length() != Def.MIN_WORD) {
                        if (wordsMap.get(word) != null) {
                            Integer value = wordsMap.get(word) + Def.ONE_SHOW;
                            wordsMap.put(word, value);
                        }
                        else {
                            wordsMap.put(word, Def.ONE_SHOW);
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
                String link = linkElement.attr("href");
                if (i != allLinks.size() - 1) {
                    if (link.contains("https://www.ynet.co.il/") && !link.equals(allLinks.get(i + 1).attr("href")) && scanLink(link) > Def.SLASH_PAGE_YNET) {
                        Document ynetPage = Jsoup.connect(link).get();
                        Elements mainTitle = ynetPage.getElementsByClass("mainTitle");
                        Elements subTitle = ynetPage.getElementsByClass("subTitle");
                        allTitlesText.append(mainTitle.text());
                        allTitlesText.append(subTitle.text());
                    }
                }
            }

            StringBuilder word = new StringBuilder();
            for (int i = 0; i < allTitlesText.length(); i++){
                char checkWord = allTitlesText.charAt(i);
                if ((checkWord >= 'א' && checkWord <= 'ת') || checkWord == '"' || checkWord == ' ' ||
                        (checkWord >= '0' && checkWord <= '9') || (checkWord >= 'A' && checkWord <= 'Z') || (checkWord >= 'a' && checkWord <= 'z')){
                    if (!(checkWord == '"')) {
                        word.append(checkWord);
                        if (word.toString().contains(text)){
                            count++;
                            word = new StringBuilder();
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
            String linkOfLongestArticle = "";
            for (int i = 0; i < allLinks.size(); i++){
                StringBuilder allText = new StringBuilder();
                Element linkElement = allLinks.get(i);
                String link = linkElement.attr("href");
                if (i != allLinks.size() - 1) {
                    if (link.contains("https://www.ynet.co.il/") && !link.equals(allLinks.get(i + 1).attr("href")) && scanLink(link) > Def.SLASH_PAGE_YNET) {
                        Document ynetPage = Jsoup.connect(link).get();
                        Elements textPage = ynetPage.getElementsByAttribute("data-text");
                        for (Element element : textPage) {
                            allText.append(element.text());
                        }
                        currentLength = allText.length();
                        if (currentLength > previousLength) {
                            linkOfLongestArticle = link;
                            previousLength = currentLength;
                        }
                    }
                }
            }
            Document ynetPage = Jsoup.connect(linkOfLongestArticle).get();
            Elements mainTitle = ynetPage.getElementsByClass("mainTitle");
            longestArticleTitle = mainTitle.text();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return longestArticleTitle;
    }
}
