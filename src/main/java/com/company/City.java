package com.company;

import org.json.JSONObject;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

public class City{

    private int museums,cafes,restaurants,bars;
    private double lat,lon;
    private String weather;
    private String mediaWiki;
    private String cityName;
    private int worldCount;

    public int getWorldCount() {
        return worldCount;
    }

    public void setWorldCount() {
        String[] s =mediaWiki.split(" ");
        this.worldCount = s.length;;
    }

    public City(int museums, int cafes, int restaurants, int bars, double lat, double lon, String weather, String mediaWiki){
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
        setCityName(city);
        setMediaWiki(city);
        setMuseums(city);
        setBars(city);
        setCafes(city);
        setRestaurants(city);
        setLat(city);
        setLon(city);
        setWeather(city);
        setWorldCount();
    }

    public City (String cityName, int museums,int cafes, int restaurants,int bars,double lat,double lon,String weather,int worldCount){
        this.cityName = cityName;
        this.museums=museums;
        this.cafes = cafes;
        this.restaurants = restaurants;
        this.bars=bars;
        this.lat=lat;
        this.lon=lon;
        this.weather=weather;
        this.worldCount = worldCount;
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
        this.cafes= countCriterionfCity(WikiContent,"cafe");;
    }
    public int getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(String city) throws IOException {
        String WikiContent = mediaWiki(city);
        this.restaurants= countCriterionfCity(WikiContent,"restaurants");;
    }

    public int getBars() {
        return bars;
    }

    public void setBars(String city) throws IOException {
        String WikiContent = mediaWiki(city);
        this.bars= countCriterionfCity(WikiContent,"bars");;
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

    public static HashMap loadingCitesFromDB(Connection db_con_obj) throws SQLException {
        ResultSet rs;
        PreparedStatement db_prep_obj;
        try {
            db_prep_obj = db_con_obj.prepareStatement("CREATE TABLE cities(cityName varchar(50) unique," +
                    "cafes int," +
                    "museums int," +
                    "restaurants int," +
                    "bars int," +
                    "lat BINARY_DOUBLE," +
                    "lon BINARY_DOUBLE," +
                    "weather varchar(30)," +
                    "wordCount int)");
            db_prep_obj.executeQuery();
        }catch (SQLSyntaxErrorException e) {
            System.out.println("Table is already made or name is taken");
        }
        db_prep_obj = db_con_obj.prepareStatement("select * from cities");
        rs = db_prep_obj.executeQuery();

        HashMap<String,City> cityHash = new HashMap<String,City>();
        while (rs.next()){
            String cityName = rs.getString("cityName");
            int museums = rs.getInt("museums");
            int cafes = rs.getInt("cafes");
            int restaurants = rs.getInt("restaurants");
            int bars = rs.getInt("bars");
            double lat = rs.getDouble("lat");
            double lon = rs.getFloat("lon");
            String weather = rs.getString("weather");
            int wordCount = rs.getInt("wordCount");
            cityHash.put(cityName,new City(cityName, museums, cafes, restaurants, bars, lat, lon, weather, wordCount));
        }
        return  cityHash;
    }



    public static void loadingCitiesToDB(HashMap cityHash,Connection db_con_obj){
        PreparedStatement db_prep_obj;


        Iterator<Map.Entry<String,City>> it = cityHash.entrySet().iterator();
        while (it.hasNext()) {
            City value = (City) it.next().getValue();
            try {
                db_prep_obj = db_con_obj.prepareStatement("insert into cities(cityName, cafes, museums, restaurants, bars, lat, lon, weather, wordCount)" +
                        "values('"+ value.getCityName() + "','" + value.getCafes() + "','" + value.getMuseums() + "','" + value.getRestaurants() + "','" +
                        value.getBars() + "','" + value.getLat() + "','" + value.getLon() + "','" + value.getWeather() + "','" + value.getWorldCount() + "')");
                db_prep_obj.executeQuery();
            } catch (Exception e) {  //αν η πολη υπαρχει στην βαση τοτε πεταει exception δεν χριαζεται να διχνει κατι στον χρηστη
            }
        }
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