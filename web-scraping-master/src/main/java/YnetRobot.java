

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
        int size = 0;
        try {
            Document website = Jsoup.connect(getRootWebsiteUrl()).get();

            Elements allLinks = website.getElementsByAttribute("href");

//            Element linkElement = mainArticles5.get(0);
//            String link1 = linkElement.attr("href");
//            System.out.println("The link: " + link1);


//            classALL19 = layoutContainer
//            classB = tbl-feed-container tbl-feed-frame-NONE  render-late-effect
//            classA = trc_related_container trc_spotlight_widget tbl-rtl tbl-feed-container tbl-feed-frame-NONE
//            System.out.println("Found " + mainArticles5.size());

            StringBuilder allText = new StringBuilder();
            for (int i = 0; i < allLinks.size(); i++){
                Element linkElement = allLinks.get(i);
                String link = linkElement.attr("href");
                if (link.contains("https://www.ynet.co.il/judaism/article/r1PEIlTsO#autoplay")){
                }
                if (i != allLinks.size()-1) {
                    if (link.contains("https://www.ynet.co.il/") && !link.equals(allLinks.get(i + 1).attr("href")) && scanLink(link) > 4) {
                        Document ynetPage = Jsoup.connect(link).get();
                        Elements mainTitle = ynetPage.getElementsByClass("mainTitle");
                        Elements subTitle = ynetPage.getElementsByClass("subTitle");
                        Elements textPage = ynetPage.getElementsByAttribute("data-text");
                        allText.append(mainTitle.text());
                        allText.append(subTitle.text());
                        for (Element element : textPage) {
                            allText.append(element.text());
                        }
                        size++;
                    }
                }
            }

            System.out.println(size);
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
        return 0;
    }

    @Override
    public String getLongestArticleTitle() {
        return null;
    }
}
