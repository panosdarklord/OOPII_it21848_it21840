package com.company;

import org.json.JSONObject;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class City{

    private int museums,cafes,restaurants,bars;
    private double lat,lon;
    private String weather;
    private String mediaWiki;
    private String cityName;

    public City(int museums,int cafes, int restaurants,int bars,double lat,double lon,String weather,String mediaWiki){
    	this.museums=museums;
    	this.cafes = cafes;
        this.restaurants = restaurants;
        this.bars=bars;
    	this.lat=lat;
    	this.lon=lon;
    	this.weather=weather;
    	this.mediaWiki=mediaWiki;
    }
    public City(){}

    public City (String city) throws IOException {
        setMuseums(city);
        setLat(city);
        setLon(city);
        setWeather(city);
        setMediaWiki(city);
        setCityName(city);

    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setMediaWiki(String city) throws IOException {
       this.mediaWiki = mediaWiki(city);
    }

    public String getMediaWiki(){
        return mediaWiki;
    }

    public int getMuseums(){
    	   return museums;
    }
    public void setMuseums(String city) throws IOException {
        String WikiContent = mediaWiki(city);
        this.museums= countCriterionfCity(WikiContent,"museums");;
    }

    public int getCafes() {
        return cafes;
    }

    public void setCafes(String city) throws IOException {
        String WikiContent = mediaWiki(city);
        this.museums= countCriterionfCity(WikiContent,"cafes");;
    }
    public int getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(String city) throws IOException {
        String WikiContent = mediaWiki(city);
        this.museums= countCriterionfCity(WikiContent,"restaurants");;
    }

    public int getBars() {
        return bars;
    }

    public void setBars(String city) throws IOException {
        String WikiContent = mediaWiki(city);
        this.museums= countCriterionfCity(WikiContent,"bars");;
    }

    public double getLat(){
    	   return lat;
    }
    public void setLat(String city) throws IOException {
        JSONObject obj = openWeather(city);
        this.lat = obj.getJSONObject("coord").getFloat("lat");
    }

    public double getLon(){
    	   return lon;
    }
    public void setLon(String city) throws IOException {
        JSONObject obj = openWeather(city);
        this.lon = obj.getJSONObject("coord").getFloat("lon");
    }

    public String getWeather(){
    	   return weather;
    }
    public void setWeather(String city) throws IOException {
        JSONObject obj = openWeather(city);
        this.weather = obj.getJSONArray("weather").getJSONObject(0).getString("main");
    }

    private String mediaWiki(String city) throws IOException {
        // build a URL
            String s = "https://en.wikipedia.org/w/api.php?action=query&prop=extracts&titles=" + city + "&format=json&formatversion=2";

        URL url = new URL(s.replace(" ", "%20"));

        // read from the URL
        Scanner scan = new Scanner(url.openStream());
        String str = "";
        while (scan.hasNext())
            str += scan.nextLine();
        scan.close();

        // build a JSON object
        JSONObject obj = new JSONObject(str);

        return  Jsoup.parse(obj.getJSONObject("query").getJSONArray("pages").getJSONObject(0).getString("extract")).text();
    }



    public static int countCriterionfCity(String cityArticle, String criterion) {
        cityArticle=cityArticle.toLowerCase();
        int index = cityArticle.indexOf(criterion);
        int count = 0;
        while (index != -1) {
            count++;
            cityArticle = cityArticle.substring(index + 1);
            index = cityArticle.indexOf(criterion);
        }
        return count;
    }



    private JSONObject openWeather (String city) throws IOException {

        // build a URL
        String s = "http://api.openweathermap.org/data/2.5/weather?q="+ city +"&appid=2d7d2a563ef20bc9da529359e9e52a0e";
        URL url = new URL(s.replace(" ", "%20"));

        // read from the URL
        Scanner scan = new Scanner(url.openStream());
        String str = "";
        while (scan.hasNext())
            str += scan.nextLine();
        scan.close();

        // build a JSON object
        return new JSONObject(str);
    }
    @Override
    public String toString(){
        return cityName;
    }

}