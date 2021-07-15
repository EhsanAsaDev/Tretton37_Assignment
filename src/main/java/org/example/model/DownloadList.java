package org.example.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Ehsan Sh
 */

public class DownloadList {
    private Map<String, Boolean> pathMap = new HashMap<>();


    public List<String> getUnDownloadList() {
        return pathMap.entrySet().stream()
                .filter(map -> !map.getValue())
                .map(map -> map.getKey())
                .collect(Collectors.toList());
    }

    public List<String> getDownloadList() {
        return pathMap.entrySet().stream()
                .filter(map -> map.getValue())
                .map(map -> map.getKey())
                .collect(Collectors.toList());
    }

    public void add(String path) {
        pathMap.putIfAbsent(path, false);
    }

    public void add(String baseURL,List<String> paths) {
        for (String path : paths) {
            pathMap.putIfAbsent(baseURL+path, false);
        }
    }

    public void setDownloaded(String path) {
        pathMap.replace(path, true);
    }
}
