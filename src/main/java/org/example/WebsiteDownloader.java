package org.example;

import lombok.AllArgsConstructor;
import org.example.model.DownloadList;

import java.util.*;
import java.util.concurrent.*;

/**
 * @author Ehsan Sh
 */

@AllArgsConstructor
public class WebsiteDownloader {
    private final String url;
    private final Integer depth;

    public DownloadList run() throws ExecutionException, InterruptedException {

        //download list maintaining the status of download for each path
        DownloadList downloadList = new DownloadList();
        downloadList.add(url);

        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1);

        for (int i = 0; i < depth; i++) {
            downloadConcurrently(url, downloadList, executorService);
        }

        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(60, TimeUnit.MILLISECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }

        return downloadList;
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
