package org.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.example.model.DownloadList;
import org.junit.Test;

import java.util.concurrent.ExecutionException;

/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void downLoad_google_dot_com() throws Exception {

        String url = "https://www.google.com/";
        Integer depth = 1;


        WebsiteDownloader websiteDownloader = new WebsiteDownloader(url, depth);
        DownloadList downloadList = websiteDownloader.run();

        assertEquals(1, downloadList.getDownloadList().size());
        assertEquals("https://www.google.com/", downloadList.getDownloadList().get(0));
        assertEquals(6, downloadList.getUnDownloadList().size());
        assertTrue(downloadList.getUnDownloadList().contains("https://www.google.com//advanced_search?hl=en-IR&authuser=0"));
    }
}
