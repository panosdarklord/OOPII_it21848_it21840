package com.company;

import java.io.*;
import java.util.*;
import java.sql.*;

import static com.company.City.loadingCitesFromDB;
import static com.company.City.loadingCitiesToDB;
import static com.company.Traveller.loadingTravellersFromFiles;
import static com.company.Traveller.writeTravellerToFile;

public class Main {

    public static Connection dbConnection(){
        Connection db_con_obj = null;

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

        return db_con_obj;
    }
    public static void ticketWinner() throws IOException {
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
        System.out.println( a.get(Arrays.asList(similarity).indexOf(max)).getName() +" Won a free ticket");
    }



    public static void main(String[] args) throws IOException, SQLException {


       final Gui ui = new Gui();

// #############  Ερωτημα #3  ################
     //   ticketWinner();


// ############## Load objects from file ###############
        LinkedList<Traveller> traveller= new LinkedList<Traveller>();
        traveller.addAll(loadingTravellersFromFiles());



// ############## add content from database #############
        Connection db_con_obj = dbConnection();
        HashMap<String,City> cityHash = new HashMap<String,City>();
        cityHash.putAll(loadingCitesFromDB(db_con_obj));


        PrintStream original = System.out;
        ui.mainGUI();
        while (!ui.closed) {


           System.setOut(new PrintStream(new OutputStream() {
               public void write(int b) {
                   //DO NOTHING
               }
           }));

           boolean cityExists = true;
           while (!ui.closed) {


               System.out.println(ui.isPressed());
               if (ui.isPressed()) {
                   System.setOut(original);


                   ArrayList<City> inputCities = new ArrayList<City>();
                   String io = ui.getIo();

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

                   } catch (Exception e) {
                       ui.resultNotification("City doesn't exist ");
                       cityExists = false;
                   }

                   if(cityExists) {
                       Traveller user = null;
                       if (ui.getChoice().equalsIgnoreCase("a")) {
                           user = new Tourist(ui.getName(), ui.getAge(), ui.getMuseums(), ui.getCafes(), ui.getRestaurants(), ui.getBars());
                           user.setVisit(user.CompareCities(inputCities).toString());
                           ui.resultNotification(user.getVisit() + " from your input fits best");
                       } else if (ui.getChoice().equalsIgnoreCase("b")) {
                           user = new Tourist(ui.getName(), ui.getAge(), ui.getMuseums(), ui.getCafes(), ui.getRestaurants(), ui.getBars());
                           user.setVisit(user.CompareCities(inputCities).toString());
                           ui.resultNotification(user.getVisit() + " from your input fits best");
                       } else if (ui.getChoice().equalsIgnoreCase("c")) {
                           user = new Tourist(ui.getName(), ui.getAge(), ui.getMuseums(), ui.getCafes(), ui.getRestaurants(), ui.getBars());
                           user.setVisit(user.CompareCities(inputCities).toString());
                           ui.resultNotification(user.getVisit() + " from your input fits best");
                       }

                       traveller.add(user);
                   }
                   ui.setPressed(false);
                   break;
               }



           }


       }

        System.setOut(original);


// ###########  εγγραφη των traveller στο αρχειο traveller.dat
        writeTravellerToFile(traveller);


// #############  κανω τους travellers μοναδικους  ####################
      Set<Traveller> set = new HashSet<Traveller>(traveller);
      traveller.clear();
      traveller.addAll(set);

//#########  εμαφανιση των traveller ταξινομημενους χωρις διπλοτυπα  ##########
      ListIterator itr2 = traveller.listIterator();
      Collections.sort(traveller);
      while(itr2.hasNext()){
          Object element = itr2.next();
          System.out.println(element + " ");
      }


// ########  αποθηκευση των city στην βαση δεδομενων  ###############
      loadingCitiesToDB(cityHash,db_con_obj);

    }
}