package com.example.com.Objects;

public class Winner {


    private String name;
    private String date;
    private int score;
    private double latitude;
    private double longitude;

    public Winner(String name, String date, int score, double latitude, double longitude) {
        this.name = name;
        this.date = date;
        this.score = score;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Winner() {

    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


}