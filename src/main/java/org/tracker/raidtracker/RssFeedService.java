package org.tracker.raidtracker;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import java.io.*;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Set;

public class RssFeedService {
    private RbTrackerTgBot rbTrackerTgBot = new RbTrackerTgBot();

    private final Set<String> rbList = new HashSet<>();  // Changed to modifiable set
    private final String processedEntriesFile = "processed_entries.txt";  // File name

    // Constructor
    public RssFeedService() {
        // Load initial values if needed
        rbList.add("Cabrio");
        rbList.add("Hallate");
        rbList.add("Golkonda");
        rbList.add("Kernon");
    }

    public void addBoss(String bossName) {
        rbList.add(bossName);
    }

    public void removeBoss(String bossName) {
        rbList.remove(bossName);
    }

    public void monitorRssFeed(ConsoleOutputHandler consoleHandler) {
        String feedUrl = "https://asterios.tm/index.php?cmd=rss&serv=0&filter=keyboss&out=xml";
        consoleHandler.print("Fetching RSS at " + LocalDateTime.now());
        try {
            URL url = new URL(feedUrl);
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(url));

            for (SyndEntry entry : feed.getEntries()) {
                boolean entryProcessed = false;
                try (BufferedReader br = new BufferedReader(new FileReader(processedEntriesFile))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        if (line.equals(entry.getUri())) {
                            entryProcessed = true;
                            break;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (!entryProcessed) {
                    try (FileWriter writer = new FileWriter(processedEntriesFile, true)) {
                        writer.write(entry.getUri() + "\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    consoleHandler.print("New Entry: " + entry.getTitle());

                    if (rbList.stream().anyMatch(entry.getTitle()::contains)
                            && LocalDateTime.ofInstant(entry.getPublishedDate().toInstant(), ZoneId.systemDefault()).isAfter(LocalDateTime.now().minusDays(1))) {

                        String message = entry.getTitle() + " " + entry.getPublishedDate();
                        rbTrackerTgBot.sendMessage("819794069", message);
                        consoleHandler.print("Boss Detected: " + message);
                    }
                }
            }
        } catch (Exception e) {
            consoleHandler.print("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public String getRbList() {
        return String.join("\n", rbList);
    }
}
