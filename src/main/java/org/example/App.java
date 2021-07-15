package org.example;

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
        try {
            websiteDownloader.run();
        } catch (ExecutionException | InterruptedException e) {
            System.out.println("Download of " + url + " didn't execute successfully");
            e.printStackTrace();
        }
    }
}
