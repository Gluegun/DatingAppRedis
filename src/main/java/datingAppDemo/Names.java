package datingAppDemo;

import lombok.Getter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
* Класс, берущий имена с сайта
**/

public class Names {

    private static String url = "https://www.thoughtco.com/common-russian-names-4770041";

    @Getter
    private static List<String> femaleNames = femaleNamesGenerator();

    @Getter
    private static List<String> maleNames = maleNamesGenerator();


    private static List<String> femaleNamesGenerator() {

        List<String> femaleNames = new ArrayList<>();

        Document doc;
        try {
            doc = Jsoup.connect(url).maxBodySize(0).get();

            Elements femaleNamesElements = doc.selectFirst("table.mntl-sc-block-table__table").select("tr");
            femaleNamesElements.remove(0);
            for (Element element : femaleNamesElements) {
                String femaleName = element.child(0).text();
                if (femaleName.contains("/")) {
                    femaleName = femaleName.substring(0, femaleName.indexOf("/"));
                }
                femaleNames.add(femaleName);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return femaleNames;
    }

    private static List<String> maleNamesGenerator() {

        List<String> maleNames = new ArrayList<>();
        Document doc;
        try {
            doc = Jsoup.connect(url).maxBodySize(0).get();
            Elements maleNamesElements = doc.select("table.mntl-sc-block-table__table").get(1).select("tr");
            maleNamesElements.remove(0);
            for (Element maleNamesElement : maleNamesElements) {

                String maleName = maleNamesElement.child(0).text();
                if (maleName.contains("/")) {
                    maleName = maleName.substring(0, maleName.indexOf("/"));
                }
                maleNames.add(maleName);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return maleNames;
    }

    public static List<String> generateListWithLimitedAmountOfNames(int amount, List<String> names) {

        List<String> randomNames = new ArrayList<>();

        Random random = new Random();


        for (int i = 0; i < amount; i++) {
            int randomNameIndex = random.nextInt(names.size());
            randomNames.add(names.get(randomNameIndex));
        }

        return randomNames;
    }
}
