package com.company;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {

//###############  Ερωτημα #3  #################
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
// ############# Τελος τρητου ερωτηματος ###############

        Scanner input = new Scanner(System.in);
        String choice ;
        do {

            System.out.println("Choose a category:\n\t" +
                    "a )Tourist\n\t" +
                    "b )Traveler\n\t" +
                    "c )business\n\t");


            choice = input.nextLine();
        }while (!choice.equals("a") && !choice.equals("b") && !choice.equals("c"));


        System.out.print("Give name: ");
        String name = input.nextLine();

        int age;
        while (true) {
            try {
                System.out.print("Give age: ");
                age = Integer.parseInt(input.nextLine());
                break;
            }catch (Exception e){
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
                    String[] fWord = s.split(" ",2);  //Tα αρχικα τις καθε χωρας δεν μου χριαζονται πουθενα
                                                            //οποτε χωριζω καθενα απο τα s στα δυο και αγνοω το πρωτο

                    //Ελεγχος αν η πολη υπαρχει
                    String url0 = "http://api.openweathermap.org/data/2.5/weather?q=" + fWord[1].trim() + "&appid=2d7d2a563ef20bc9da529359e9e52a0e";
                    URL url = new URL(url0.replace(" ", "%20"));
                    inputCities.add(new City(fWord[1].trim()));

                }
                break;
            }catch (Exception e) {
                System.out.println("City doesn't exist ");
            }
        }

        System.out.println("for the next fields give a number greater than zero if you want it in your destination");

        int museums ,cafes ,restaurants ,bars ;
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



        Traveller user;
        if (choice.equalsIgnoreCase("a")){

        //Tourist
           user = new Tourist(name,age,museums,cafes,restaurants,bars);
            System.out.println(user.CompareCities(inputCities)+ " from your input fits best");
        } else if (choice.equalsIgnoreCase("b")){

        //Traveller
            user = new Traveller(name,age,museums,cafes,restaurants,bars);
            System.out.println(user.CompareCities(inputCities)+ " from your input fits best");
        }else {

        //Business
            String city;
            System.out.print("Give current city name:\n\t");
            city = Io.nextLine();
            user = new Business(name,age,city,museums,cafes,restaurants,bars);
            System.out.println(user.CompareCities(inputCities) + " from your input fits best");
        }

        boolean weather;
        while(true) {
            try {
                System.out.print("Do you want to exclude cities in which rains at this moment\n write true or false: ");
                Scanner bool = new Scanner(System.in);
                weather = bool.nextBoolean();
                break;
            }catch (Exception e){
                System.out.print("please write true or false\n");
            }
        }

        user.CompareCities(weather);


    }
}


