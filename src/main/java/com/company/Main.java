package com.company;

import java.io.*;
import java.net.URL;
import java.util.*;
import java.sql.*;

public class Main {



    public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {

// #############  Ερωτημα #3  ################
        ArrayList<Traveller> a=new ArrayList<Traveller>();
        a.add(new Tourist("Merry",21,4,10,15,6));
        a.add(new Tourist("Larry",40,15,4,15,3));
        a.add(new Traveller("Bob",30,2,20,5,20));
        a.add(new Traveller("Adam",25,0,15,5,10));
        a.add(new Business("Martin",28,"Amsterdam",0,7,10,20));
        a.add(new Business("Nikos",28,"Athens",0,20,5,29));

        City ticket = new City("New York");
        Integer[] similarity = new Integer[5];

        similarity[0] = a.get(0).Similarity(ticket);
        similarity[1] = a.get(1).Similarity(ticket);
        similarity[2] = a.get(2).Similarity(ticket);
        similarity[3] = a.get(3).Similarity(ticket);
        similarity[4] = a.get(4).Similarity(ticket);

        int max = Collections.max(Arrays.asList(similarity));

        LinkedList<Traveller> traveller= new LinkedList<Traveller>();
        System.out.println( a.get(Arrays.asList(similarity).indexOf(max)).getName() +" Won a free ticket");



// ############## Load objects from file ###############
        ObjectInputStream dpg7001;
        try{
            dpg7001=new ObjectInputStream(new FileInputStream("traveler.dat"));
            Scanner inpu=new Scanner("traveler.dat");
            while(inpu.hasNextLine()){
                traveller.add((Traveller) dpg7001.readObject());
            }
        }catch(Exception l){
        }

        HashMap<String,City> cityHash = new HashMap<String,City>();




// ############## add content from database #############
        Connection db_con_obj = null;
        PreparedStatement db_prep_obj = null;
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");

            db_con_obj = DriverManager.getConnection("jdbc:oracle:thin:@oracle12c.hua.gr:1521:orcl","it21840","it21840");
            if (db_con_obj != null) {
                System.out.println("Connection Successful! Enjoy. Now it's time to push data");
            } else {
                System.out.println("Failed to make connection!");
            }
        }catch (Exception e){
            System.out.println(e);
        }

        ResultSet rs;
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





        while(true) {
            Scanner input = new Scanner(System.in);
            String choice;
            do {
                System.out.println("Choose a category:\n\t" +
                        "a )Tourist\n\t" +
                        "b )Traveler\n\t" +
                        "c )business\n\t" +
                        "d )Exit\n\t");


                choice = input.nextLine();
            } while (!choice.equals("a") && !choice.equals("b") && !choice.equals("c") && !choice.equals("d"));

            if (choice.equals("d")) {
                break;
            }

            System.out.print("Give name: ");
            String name = input.nextLine();

            int age;
            while (true) {
                try {
                    System.out.print("Give age: ");
                    age = Integer.parseInt(input.nextLine());
                    break;
                } catch (Exception e) {
                    System.out.println("Incorrect input: an integer is required");
                }
            }


            Scanner Io = new Scanner(System.in);
            ArrayList<City> inputCities = new ArrayList<City>();

            while (true) {
                System.out.print("give cities (format:GR Athens,FR Paris):\n\t");
                String io = Io.nextLine();

                String[] cities = io.split(","); //χωριζω την ισωδο με βαση το ','

                try {
                    for (String s : cities) {
                        String[] fWord = s.split(" ", 2);        //Tα αρχικα τις καθε χωρας δεν μου χριαζονται πουθενα
                                                                       //οποτε χωριζω καθενα απο τα s στα δυο και αγνοω το πρωτο
                        String city = fWord[1].trim();
                        if (!cityHash.containsKey(city)) {
                            cityHash.put(city, new City(city));
                        }
                        inputCities.add(cityHash.get(city));
                    }
                    break;
                } catch (Exception e) {
                    System.out.println("City doesn't exist ");
                }
            }

            System.out.println("for the next fields give a number greater than zero if you want it in your destination");

            int museums, cafes, restaurants, bars;
            while (true) {
                try {

                    System.out.println("Do you want museums?");
                    museums = Integer.parseInt(Io.nextLine());

                    System.out.println("Do you want Cafes?");
                    cafes = Integer.parseInt(Io.nextLine());

                    System.out.println("Do you want restaurants?");
                    restaurants = Integer.parseInt(Io.nextLine());

                    System.out.println("Do you want bars?");
                    bars = Integer.parseInt(Io.nextLine());
                    break;
                } catch (Exception e) {
                    System.out.println("Incorrect input: an integer is required");
                }
            }



// #############  δημιουργια του σωστου αντικειμενου  ################
            Traveller user;
            if (choice.equalsIgnoreCase("a")) {

                //Tourist
                user = new Tourist(name, age, museums, cafes, restaurants, bars);
                user.setVisit(user.CompareCities(inputCities).toString());
                System.out.println(user.getVisit() + " from your input fits best");
            } else if (choice.equalsIgnoreCase("b")) {

                //Traveller
                user = new Traveller(name, age, museums, cafes, restaurants, bars);
                user.setVisit(user.CompareCities(inputCities).toString());
                System.out.println(user.getVisit() + " from your input fits best");
            } else {

                //Business
                String city;
                System.out.print("Give current city name:\n\t");
                city = Io.nextLine();
                user = new Business(name, age, city, museums, cafes, restaurants, bars);
                user.setVisit(user.CompareCities(inputCities).toString());
                System.out.println(user.getVisit() + " from your input fits best");
            }
            traveller.add(user);
            boolean weather;
            while (true) {
                try {
                    System.out.print("Do you want to exclude cities in which rains at this moment\n write true or false: ");
                    Scanner bool = new Scanner(System.in);
                    weather = bool.nextBoolean();
                    break;
                } catch (Exception e) {
                    System.out.print("please write true or false\n");
                }
            }

            user.CompareCities(weather);
        }



// #############  κανω τους travellers μοναδικους  ####################
        Set<Traveller> set = new HashSet<Traveller>(traveller);
        traveller.clear();
        traveller.addAll(set);



// ###########  εγγραφη των traveller στο αρχειο traveller.dat
        ListIterator itr = traveller.listIterator();
        try{
            FileOutputStream out=new FileOutputStream(new File("traveler.dat"));
            ObjectOutputStream dpg7000=new ObjectOutputStream(out);
            while(itr.hasNext()){
                Object element = itr.next();
                dpg7000.writeObject(element);
            }
            dpg7000.close();
        }catch(Exception c){
            System.out.println("error handling file");
        }




// #########  εμαφανιση των traveller ταξινομημενους χωρις διπλοτυπα  ##########
        ListIterator itr2 = traveller.listIterator();
        Collections.sort(traveller);
        while(itr2.hasNext()){
            Object element = itr2.next();

            System.out.println(element + " ");
        }


// ########  αποθηκευση των city στην βαση δεδομενων  ###############
        for (City value: cityHash.values()) {


            try {
                db_prep_obj = db_con_obj.prepareStatement("insert into cities(cityName, cafes, museums, restaurants, bars, lat, lon, weather, wordCount)" +
                        "values('"+ value.getCityName() + "','" + value.getCafes() + "','" + value.getMuseums() + "','" + value.getRestaurants() + "','" +
                                value.getBars() + "','" + value.getLat() + "','" + value.getLon() + "','" + value.getWeather() + "','" + value.getWorldCount() + "')");
                db_prep_obj.executeQuery();
            } catch (Exception e) {  //αν η πολη υπαρχει στην βαση τοτε πεταει exception δεν χριαζεται να διχνει κατι στον χρηστη
            }
        }
    }
}


