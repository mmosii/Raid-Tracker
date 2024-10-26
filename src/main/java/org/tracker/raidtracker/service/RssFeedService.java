package org.tracker.raidtracker.service;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.tracker.raidtracker.handler.ConsoleOutputHandler;
import org.tracker.raidtracker.telegram.RbTrackerTgBot;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class RssFeedService {
    private final RbTrackerTgBot rbTrackerTgBot = new RbTrackerTgBot();

    private final Set<String> rbList = new HashSet<>();
    private final Set<String> subRbSet = Set.of("Cabrio", "Golkonda", "Kernon", "Hallate");
    private final HashMap<String, LocalDateTime> rbTime = new HashMap<>();
    private String serverID = "0";
    private String userId = "0";

    public Set<String> getRbList() {
        return rbList;
    }

    public void setServerID(String serverID) {
        this.serverID = serverID;
    }

    public HashMap<String, LocalDateTime> getRbTime() {
        return rbTime;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public RssFeedService() {
    }

    public void addBoss(String bossName) {
        rbList.add(bossName);
    }

    public void removeBoss(String bossName) {
        rbList.remove(bossName);
    }

    public void monitorRssFeed(ConsoleOutputHandler consoleHandler) {
        String feedUrl = "https://asterios.tm/index.php?cmd=rss&serv=" + serverID + "&count=100&out=xml";
        try {
            URL url = new URL(feedUrl);
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(url));

            for (SyndEntry entry : feed.getEntries()) {
                String bossName = entry.getTitle().substring(10);
                final LocalDateTime publishTime = LocalDateTime.ofInstant(entry.getPublishedDate().toInstant(), ZoneId.systemDefault());
                if (rbList.stream().anyMatch(entry.getTitle()::contains)
                        && publishTime.isAfter(LocalDateTime.now().minusDays(2)) && (!rbTime.containsKey(bossName) ||
                        rbTime.get(bossName).isBefore(publishTime))) {
                    rbTime.put(bossName, publishTime);
                    String message = entry.getTitle().substring(10) + " killed " + publishTime.format(DateTimeFormatter.ofPattern("dd.MM HH:mm:ss"));
                    if (subRbSet.stream().anyMatch(entry.getTitle()::contains)) {
                        message = entry.getTitle().substring(10) + " killed " + publishTime.format(DateTimeFormatter.ofPattern("dd.MM HH:mm:ss"))
                                + ". Respawn: " + publishTime.plusDays(1).minusHours(6).format(DateTimeFormatter.ofPattern("dd.MM HH:mm"))
                                + " - " + publishTime.plusDays(1).plusHours(6).format(DateTimeFormatter.ofPattern("dd.MM HH:mm"));
                    }
                    consoleHandler.print(message);
                    if (!userId.equals("0")) {
                        rbTrackerTgBot.sendMessage(userId, message);
                    }
                }
            }
        } catch (Exception e) {
            consoleHandler.print("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public boolean isBossInList(String bossName) {
        return rbList.contains(bossName);
    }

    public LocalDateTime getRespawnTime(String bossName) {
        return switch (bossName) {
            case "Cabrio" -> rbTime.get("Shilen's Messenger Cabrio");
            case "Golkonda" -> rbTime.get("Longhorn Golkonda");
            case "Hallate" -> rbTime.get("Death Lord Hallate");
            default -> rbTime.get(bossName);
        };
    }
}
