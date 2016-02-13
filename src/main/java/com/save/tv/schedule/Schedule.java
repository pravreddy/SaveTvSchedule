package com.save.tv.schedule;

/**
 * Created by preddy on 09/02/2016.
 */

public class Schedule {
    private String channel;
    private String startTime;
    private String endTime;
    private String title;
    private String localEpisodeTitle;
    private String genre;

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocalEpisodeTitle() {
        return localEpisodeTitle;
    }

    public void setLocalEpisodeTitle(String localEpisodeTitle) {
        this.localEpisodeTitle = localEpisodeTitle;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
