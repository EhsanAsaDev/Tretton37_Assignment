package org.example.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Ehsan Sh
 */

public class HtmlContentHelper {

    /**
     * extract url list from htmlContent
     * @param htmlContent as a String
     * @return url list
     *
     */
    public static List<String> extractUrls(String htmlContent) {
        Document document = Jsoup.parse(htmlContent);
        Elements elements = document.select("a[href]");
        List<String> urlList = new ArrayList<>();
        for (Element element: elements) {
            String href = element.attr("href");
            if (href.startsWith("/") && (href.length() > 1)) {
                urlList.add(href);
            }
        }
        urlList = urlList.stream().distinct().collect(Collectors.toList());

        return urlList;
    }
}   
