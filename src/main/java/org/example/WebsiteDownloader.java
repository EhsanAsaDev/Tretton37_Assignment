package org.example;

import lombok.AllArgsConstructor;
import org.example.model.DownloadList;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author Ehsan Sh
 */

@AllArgsConstructor
public class WebsiteDownloader {
    private final String url;
    private final Integer depth;

    public void run() throws ExecutionException, InterruptedException {

        //download list maintaining the status of download for each path
        DownloadList downloadList = new DownloadList();
        downloadList.add(url);

        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1);

        for (int i = 0; i < depth; i++) {
            downloadConcurrently(url, downloadList, executorService);
        }

    }

    private void downloadConcurrently(String baseURL, DownloadList downloadList, ExecutorService executorService) throws InterruptedException {
        List<PageDownloader> taskList = new ArrayList<>();
        downloadList.getUnDownloadList()
                .forEach(path -> {
                    taskList.add(new PageDownloader(path));
                    downloadList.setDownloaded(path);
                });

        List<Future<List<String>>> resultList ;
        try {
            resultList = executorService.invokeAll(taskList);
        } catch (InterruptedException e) {
            System.out.println("InterruptedException: Couldn't invokeAll tasks");
            throw e;
        }

        resultList.forEach(future -> {
            try {
                //Add new paths
                downloadList.add(baseURL, future.get());
            } catch (InterruptedException | ExecutionException e) {
                System.out.println("Couldn't get futures");
                throw new RuntimeException(e);
            }
        });
    }
}   
