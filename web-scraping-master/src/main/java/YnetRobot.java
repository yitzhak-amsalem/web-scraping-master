

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

            Elements mainArticles5 = website.getElementsByAttribute("href");

//            Element linkElement = mainArticles5.get(0);
//            String link1 = linkElement.attr("href");
//            System.out.println("The link: " + link1);



            System.out.println("Found " + mainArticles5.size());

            int size = 0;
            for (int i = 0; i < mainArticles5.size(); i++){
                Element linkElement = mainArticles5.get(i);
                String link = linkElement.attr("href");
                if (i != mainArticles5.size()-1) {
                    if (link.contains("https://www.ynet.co.il/") && !link.equals(mainArticles5.get(i + 1).attr("href")) && scanLink(link) > 4) {
                        Document ynetPage = Jsoup.connect(link).get();
                        Elements mainTitle = ynetPage.getElementsByClass("mainTitle");
                        Elements subTitle = ynetPage.getElementsByClass("subTitle");
                        Elements textPage = ynetPage.getElementsByAttribute("data-text");
                        System.out.println();
                        System.out.println("-------------");
                        System.out.println();
                        System.out.println(mainTitle.text());
                        System.out.println(subTitle.text());
                        System.out.println();
                        System.out.println("-------------");
                        System.out.println();
                        for (Element element : textPage) {
                            System.out.println(element.text());
                            System.out.println();
                            System.out.println("-------------");
                            System.out.println();
                        }
//                        System.out.println("Link: " + link);
//                        System.out.println("Article: " + linkElement.text());
//                        size++;
                    }
                }
            }

//            class = mainTitle
//            class = subTitle
//            class = text_editor_paragraph rtl
//            key = data-text



//            for (Element element : mainArticles5) {
//                String link = element.attr("href");
//                if (link.contains("www.ynet.co.il")) {
//
//                }
//                if (link.contains("www.ynet.co.il") && size == 0){
//                    System.out.println("Link: " + link);
//                    System.out.println("Article: " + element.text());
//                    size = 0;
//                }
//            }
            System.out.println(size);




        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
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
