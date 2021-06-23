import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class WallaRobot extends BaseRobot {


    public WallaRobot() {
        super("https://www.walla.co.il/");
    }

    @Override
    public Map<String, Integer> getWordsStatistics() {
        Map<String, Integer> wordsMap = new HashMap<>();
        try {
            Document website = Jsoup.connect(getRootWebsiteUrl()).get();
            Elements allLinks = website.getElementsByAttribute("href");
            System.out.println("all links: " + allLinks.size());

            StringBuilder allText = new StringBuilder();
            for (int i = 0; i < allLinks.size(); i++){
                Element linkElement = allLinks.get(i);
                String link = linkElement.attr("href");
                if (i != allLinks.size()-1) {
                    if (link.contains(".walla.co.il/") && !link.equals(allLinks.get(i + 1).attr("href")) && scanLink(link) > 3
                            && !(link.contains("pdf")) && !(link.contains("break")) && !(link.contains("pdf")) && !(link.contains("vod"))
                            && !(link.contains("fun")) && !(link.contains("viva")) ) {
//                        System.out.println(link);
//                        System.out.println(linkElement.text());
                        Document wallaPage = Jsoup.connect(link).get();
                        Elements mainTitle = wallaPage.getElementsByTag("h1");
                        Elements textPage = wallaPage.getElementsByTag("p");
                        allText.append(mainTitle.text()).append(" ");
                        for (Element element : textPage) {
                            Element containerText = element.parent().parent();
                            String classValue = containerText.attr("class");
                            Element containerSubTitle = element.parent();
                            String tagName = containerSubTitle.tagName();
                            if (tagName.equals("header")) {
                                allText.append(element.text()).append(" ");
                            }
                            if (classValue.equals("css-onxvt4  ")) {
                                allText.append(element.text()).append(" ");
                            }
                        }
                    }
                }
            }
//            System.out.println(allTitlesText);
//            System.out.println("SubTitles: ");
//            System.out.println(allSubTitlesText);
//            System.out.println("ArticleText: ");
//            System.out.println(allArticleText);
//            System.out.println("all allTitlesText length: " + allTitlesText.length());
//            System.out.println("all allTextClass length: " + allSubTitlesText.length());
//            System.out.println("all allTextP length: " + allArticleText.length());
//            System.out.println("size now links: " + size);
//            System.out.println(allText.length());

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
                String link = linkElement.attr("href");
                if (i != allLinks.size()-1) {
                    if (link.contains(".walla.co.il/") && !link.equals(allLinks.get(i + 1).attr("href")) && scanLink(link) > 3
                            && !(link.contains("pdf")) && !(link.contains("break")) && !(link.contains("pdf")) && !(link.contains("vod"))
                            && !(link.contains("fun")) && !(link.contains("viva")) ) {
                        Document wallaPage = Jsoup.connect(link).get();
                        Elements mainTitle = wallaPage.getElementsByTag("h1");
                        Elements textPage = wallaPage.getElementsByTag("p");
                        allTitlesText.append(mainTitle.text()).append(" ");
                        for (Element element : textPage) {
                            Element containerSubTitle = element.parent();
                            String tagName = containerSubTitle.tagName();
                            if (tagName.equals("header")) {
                                allTitlesText.append(element.text()).append(" ");
                            }
                        }
                    }
                }
            }
            System.out.println("all titles of Articles text length: " + allTitlesText.length());


            String word = "";
            for (int i = 0; i < allTitlesText.length(); i++){
                char chekWord = allTitlesText.charAt(i);
                if ((chekWord > 1487 && chekWord < 1515) || chekWord == 34 ||
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
                StringBuilder allText = new StringBuilder();
                Element linkElement = allLinks.get(i);
                String link = linkElement.attr("href");
                if (i != allLinks.size() - 1) {
                    if (link.contains(".walla.co.il/") && !link.equals(allLinks.get(i + 1).attr("href")) && scanLink(link) > 3
                            && !(link.contains("pdf")) && !(link.contains("break")) && !(link.contains("pdf")) && !(link.contains("vod"))
                            && !(link.contains("fun")) && !(link.contains("viva"))) {
                        Document wallaPage = Jsoup.connect(link).get();
                        Elements textPage = wallaPage.getElementsByTag("p");
                        for (Element element : textPage) {
                            Element containerText = element.parent().parent();
                            String classValue = containerText.attr("class");
                            if (classValue.equals("css-onxvt4  ")) {
                                allText.append(element.text()).append(" ");
                            }
                        }
                        currentLength = allText.length();
                        if (currentLength > previousLength) {
                            linkOfLongestArticle = link;
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
