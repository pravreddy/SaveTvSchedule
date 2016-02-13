package com.save.tv.schedule;

/**
 * Created by preddy on 09/02/2016.
 */


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;


public class ScheduleMain {

    public static void main(String[] args) {
        String url = "http://www.programme-tv.net/programme/chaine/2016-01-30/programme-m6-12.html";
        String fileName = "2016-01-30.csv";
        List<Schedule> schedules = new ArrayList<Schedule>();
        addTitleLine(schedules);

        try {
            ReadDataFromUrlAndSave(url, schedules, fileName);
            saveFile(schedules, fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void addTitleLine(List<Schedule> schedules) {
        Schedule title = new Schedule();
        title.setChannel("Channel");
        title.setEndTime("End");
        title.setStartTime("Start");
        title.setGenre("Genre");
        title.setLocalEpisodeTitle("Local Episode Title");
        title.setTitle("Local Title");
        schedules.add(title);
    }
    private static void ReadDataFromUrlAndSave(String urlStr,  List<Schedule> schedules, String fileName) throws IOException {

        Document doc;
        String title = "";
        try {
            doc = Jsoup.connect(urlStr).get();
            title = doc.title();
            int arrayIndex = 1;
            System.out.println("STARTED");
            int  index = title.indexOf(":");  // Gets the first index where a : occours
            String Channel = title.substring(0, index).trim();
            int  indexDate = fileName.indexOf(".");
            String Date = fileName.substring(0, indexDate).trim();

            Elements broadcasts = doc.select("div.broadcasts");
            for (Element broadcast : broadcasts) {
                Elements broadcastElements = broadcast.select("div.broadcast");
                for (Element elements : broadcastElements) {

                    String time = elements.select("span.hour").text();
                    String titleLocal = elements.select("a.title").text();
                    if(titleLocal.isEmpty())
                    {
                        titleLocal = elements.select("span.title").text();
                    }
                    String localEpisodeTitle = elements.select("span.subtitle").text();;
                    String genreType = elements.select("span.type").text();;
                    int  indexGenre = genreType.indexOf("(");
                    String genre = genreType.substring(0, indexGenre).trim();
                    String startDateTime = Date + " " + time;

                    Schedule schedule = new Schedule();
                    schedule.setChannel(Channel);
                    schedule.setStartTime(startDateTime);
                    schedule.setEndTime("");
                    schedule.setGenre(genre);
                    schedule.setLocalEpisodeTitle(localEpisodeTitle);
                    schedule.setTitle(titleLocal);
                    schedules.add(schedule);
                    Schedule prevSchedule = schedules.get(arrayIndex-1);
                    if(prevSchedule.getEndTime().isEmpty()) {
                        prevSchedule.setEndTime(startDateTime);
                        schedules.set(arrayIndex-1, prevSchedule);
                    }
                    arrayIndex++;
                }
            }
        } catch (IOException e) {
            System.out.println("File not found.");
            throw e;
        }
    }

    public static void saveFile(List<Schedule> schedules,  String file) throws IOException{
        try{
            PrintWriter writer = new PrintWriter(file);
            for (Schedule schedule : schedules){
                StringJoiner sj = new StringJoiner(",");
                sj.add(addQuotesToString(schedule.getChannel()));
                sj.add(addQuotesToString(schedule.getStartTime()));
                sj.add(addQuotesToString(schedule.getEndTime()));
                sj.add(addQuotesToString(schedule.getTitle()));
                sj.add(addQuotesToString(schedule.getLocalEpisodeTitle()));
                sj.add(addQuotesToString(schedule.getGenre()));
                writer.println(sj.toString());
            }
            writer.close();
        } catch (FileNotFoundException e){
            System.out.println("File not found.");
            throw e;
        }
    }

    public static String addQuotesToString(String input){
        String quoteText = "";
        quoteText = "\"" + input + "\"";
        return quoteText;
    }
}
