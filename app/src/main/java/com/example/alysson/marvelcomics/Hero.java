package com.example.alysson.marvelcomics;

import android.os.Parcel;
import android.os.Parcelable;

public class Hero implements Parcelable{
    private int id;
    private String name;
    private String description;
    private int comicsAvailable;
    private int seriesAvailable;
    private int storiesAvailable;
    private int eventsAvailable;
    private String onlineUrl;

    public Hero(int id, String name, String description, int comicsAvailable, int seriesAvailable, int storiesAvailable, int eventsAvailable, String onlineUrl, String thumbnail) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.comicsAvailable = comicsAvailable;
        this.seriesAvailable = seriesAvailable;
        this.storiesAvailable = storiesAvailable;
        this.eventsAvailable = eventsAvailable;
        this.onlineUrl = onlineUrl;
        this.thumbnail = thumbnail;
    }

    public Hero(Parcel source){
        String[] data = new String[9];

        source.readStringArray(data);
        this.id = Integer.parseInt(data[0]);
        this.name = data[1];
        this.description = data[2];
        this.comicsAvailable = Integer.parseInt(data[3]);
        this.seriesAvailable = Integer.parseInt(data[4]);
        this.storiesAvailable = Integer.parseInt(data[5]);
        this.eventsAvailable = Integer.parseInt(data[6]);
        this.onlineUrl = data[7];
        this.thumbnail = data[8];
    }

    public Hero(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public int getComicsAvailable() {
        return comicsAvailable;
    }

    public void setComicsAvailable(int comicsAvailable) {
        this.comicsAvailable = comicsAvailable;
    }

    public int getSeriesAvailable() {
        return seriesAvailable;
    }

    public void setSeriesAvailable(int seriesAvailable) {
        this.seriesAvailable = seriesAvailable;
    }

    public int getStoriesAvailable() {
        return storiesAvailable;
    }

    public void setStoriesAvailable(int storiesAvailable) {
        this.storiesAvailable = storiesAvailable;
    }

    public int getEventsAvailable() {
        return eventsAvailable;
    }

    public void setEventsAvailable(int eventsAvailable) {
        this.eventsAvailable = eventsAvailable;
    }

    public String getOnlineUrl() {
        return onlineUrl;
    }

    public void setOnlineUrl(String onlineUrl) {
        this.onlineUrl = onlineUrl;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    private String thumbnail;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{String.valueOf(this.id), this.name, this.description,
                String.valueOf(this.comicsAvailable), String.valueOf(this.seriesAvailable), String.valueOf(this.storiesAvailable),
                String.valueOf(this.eventsAvailable), this.onlineUrl, this.thumbnail});
    }

    public static final Parcelable.Creator<Hero> CREATOR = new Parcelable.Creator<Hero>(){
        @Override
        public Hero createFromParcel(Parcel source) {
            return new Hero(source);
        }

        @Override
        public Hero[] newArray(int size) {
            return new Hero[size];
        }
    };
}
