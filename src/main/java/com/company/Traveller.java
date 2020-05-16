package com.company;

import java.io.*;
import java.util.*;
public class Traveller implements java.io.Serializable, Comparable<Traveller> {
    private   String name;
    private   int    age;
    protected double lat;
    protected double lon;
    private   String visit;
    static    int    counter;
    private   int    museums, cafes, restaurants, bars;

    public Traveller(String name, int age, int museums, int cafes, int restaurants, int bars) {
        this.name        = name;
        this.age         = age;
        this.museums     = museums;
        this.cafes       = cafes;
        this.restaurants = restaurants;
        this.bars        = bars;
        counter++;
    }

    public Traveller() {
        counter++;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getVisit() {
        return visit;
    }

    public void setVisit(String visit) {
        this.visit = visit;
    }

    public int getCafes() {
        return cafes;
    }

    public void setCafes(int cafes) {
        this.cafes = cafes;
    }

    public int getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(int restaurants) {
        this.restaurants = restaurants;
    }

    public int getBars() {
        return bars;
    }

    public void setBars(int bars) {
        this.bars = bars;
    }

    public int getAge() {
        return age;
    }

    public void setMuseums(int museums) {
        this.museums = museums;
    }

    public int getMuseums() {
        return museums;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        Traveller.counter = counter;
    }


    public static LinkedList loadingTravellersFromFiles(){

        LinkedList<Traveller> traveller= new LinkedList<Traveller>();
        ObjectInputStream dpg7001;
        try{
            dpg7001=new ObjectInputStream(new FileInputStream("traveler.dat"));
            Scanner input=new Scanner("traveler.dat");
            while(input.hasNextLine()){
                traveller.add((Traveller) dpg7001.readObject());
            }
        }catch(Exception l){
        }


        return traveller;
    }

public static void writeTravellerToFile(LinkedList<Traveller> traveller){
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
}


    public int Similarity(City a) {
        int similarity = 0;
        if (a.getCafes() > 0 && cafes > 0) {
            similarity += 1;
        }
        if (a.getRestaurants() > 0 && restaurants > 0) {
            similarity += 1;
        }
        if (a.getBars() > 0 && bars > 0) {
            similarity += 1;
        }
        if (a.getMuseums() > 0 && museums > 0) {
            similarity += 1;
        }

        return similarity;
    }

    public City CompareCities(ArrayList<City> a) {
        ArrayList<Integer> b = new ArrayList<Integer>();
        int j = 0;
        for (City i : a) {
            b.add(Similarity(i));
            j++;
        }
        int max = 0;
        for (int i = 0; i < j; i++) {
            if (b.get(i) > max) {
                max = b.get(i);
            }
        }
        return a.get(b.indexOf(max));
    }

    public void CompareCities(boolean weather) {
        if (weather) {
            System.out.println("No cities when the weather is rain!");
        } else {
            System.out.println("All cities accepted no matter the weather!");
        }
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        final Traveller trvlr = (Traveller)obj;
        return this.name.equals(trvlr.name);
    }

    @Override
    public int hashCode(){
    int hash = 3;
    hash = 53 * (this.age + this.name.hashCode());
    return hash;
    }

    @Override
    public String toString() {
        return name + " " + age;
    }




    public int compareTo(Traveller o) {
        return this.age - o.age;
    }
}