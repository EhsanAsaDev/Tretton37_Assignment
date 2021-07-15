package org.example;


import org.example.util.HtmlContentHelper;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;


public class PageDownloader implements Callable<List<String>> {

    private final String urlString;

    public PageDownloader(String urlString) {
        this.urlString = urlString;
    }

    @Override
    public List<String> call() {

        StringBuilder htmlContent;
        try {
            htmlContent = downloadFile();
        } catch (IOException e) {
            return Collections.emptyList();
        }

        return HtmlContentHelper.extractUrls(htmlContent.toString());
    }

    private StringBuilder downloadFile() throws IOException {
        System.out.println(Thread.currentThread().getName() + " downloading:" + urlString);

        URL url;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            System.out.println("Invalid URL");
            throw e;
        }

        try {
            Files.createDirectories(Paths.get(url.getHost() + url.getPath()));
        } catch (IOException e) {
            System.out.println("Couldn't create directory: " + url.getHost() + url.getPath());
            throw e;
        }

        StringBuilder htmlContent = writeAsaFile(url);

        System.out.println(Thread.currentThread().getName() + " finished downloading:" + urlString);
        return htmlContent;
    }

    private StringBuilder writeAsaFile(URL url) {
        StringBuilder htmlContent = new StringBuilder();
        try (
                BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(url).openStream()));
                BufferedWriter writer = new BufferedWriter(new FileWriter(url.getHost() + url.getPath() + "/index.html"))
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line);
                htmlContent.append(line);
            }
        } catch (IOException e) {
            System.out.println("Couldn't download from: " + urlString);
        }
        return htmlContent;
    }




}