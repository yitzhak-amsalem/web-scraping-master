import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Bot {
    public static void main(String[] args) {
        try {
            Document website = Jsoup.connect("https://www.walla.co.il/").get();
            Elements mainArticles = website.getElementsByClass("with-roof ");
            System.out.println("Found " + mainArticles.size());

            Element mainArticle = mainArticles.get(0);
            Element mainTitle = mainArticle.getElementsByTag("h2").get(0);
            System.out.println(mainTitle.text());

            Element linkElement = mainArticle.getElementsByTag("a").get(0);
            String link = linkElement.attr("href");
            System.out.println("The link: " + link);

            Document mainArticlePage = Jsoup.connect(link).get();
            Elements headerElements = mainArticlePage.getElementsByTag("header");
            System.out.println("headerElements: " + headerElements.size());
            for (Element headerElement : mainArticles) {
                System.out.println(headerElement.text());
            }




//            for (Element element : mainArticles){
//                Element container = element.parent().parent();
//                String classValue = container.attr("class");
//                if (classValue.equals("with-roof ")) {
//                    System.out.println("Article: " + element.text());
//                }
//            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
