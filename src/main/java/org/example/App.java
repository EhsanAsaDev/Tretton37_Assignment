package org.example;

import org.example.model.DownloadList;

import java.util.concurrent.ExecutionException;

/**
 * download website app
 */
public class App {
    public static void main(String[] args) {
        String url;
        int depth;
        if (args.length >= 2) {
            url = args[0];
            depth = Integer.parseInt(args[1]);
        } else {

            url = "https://tretton37.com";
            depth = 3;
        }

        WebsiteDownloader websiteDownloader = new WebsiteDownloader(url, depth);
        DownloadList downloadList = null;
        try {
            downloadList = websiteDownloader.run();
        } catch (ExecutionException | InterruptedException e) {
            System.out.println("Download of " + url + " didn't execute successfully");
            e.printStackTrace();
            return;
        }

        System.out.println(url + " was downloaded successfully!");
        System.out.println("To a depth of " +depth + ", " + downloadList.getDownloadList().size() + "paths detected and downloaded.");
        System.out.println("And for a next step to a depth of " + (depth+1) + ", " + downloadList.getUnDownloadList().size() + " new paths detected.");

    }
}
