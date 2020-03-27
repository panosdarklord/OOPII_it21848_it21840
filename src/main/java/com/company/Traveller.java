package com.company;

import java.util.*;
public class Traveller{
       private String name;
       private int age;
       protected double lat;
       protected double lon;
       static int counter;
       private int museums,cafes,restaurants,bars;
       public Traveller(String name,int age,int museums,int cafes,int restaurants, int bars){
    	   this.name=name;
    	   this.age=age;
    	   this.museums=museums;
    	   this.cafes = cafes;
           this.restaurants = restaurants;
           this.bars=bars;
    	   counter++;
       }
       public Traveller(){
    	   counter++;
       }
       public void setName(String name){
    	   this.name=name;
       }
       public String getName(){
    	   return name;
       }
       public void setAge(int age){
    	   this.age=age;
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

    public int getAge(){
    	   return age;
       }
       public void setMuseums(int museums){
    	   this.museums=museums;
       }
       public int getMuseums(){
    	   return museums;
       }
       public int getCounter(){
    	   return counter;
       }
       public void setCounter(int counter){
    	   Traveller.counter=counter;
       }


     public int Similarity(City a){
         int similarity = 0;
     	if (a.getCafes() > 0 && cafes > 0){
     	    similarity += 1;
        }
         if (a.getRestaurants() > 0 && restaurants > 0){
             similarity += 1;
         }
         if (a.getBars() > 0 && bars > 0){
             similarity += 1;
         }
         if (a.getMuseums() > 0 && museums > 0){
             similarity += 1;
         }
     	return similarity;
	 }

       public City CompareCities(ArrayList<City> a){
    	   ArrayList<Integer> b=new ArrayList<Integer>();
    	   int j=0;
    	   for(City i:a){
    		   b.add(Similarity(i));
    		   j++;
    	   }
    	   int max=0;
    	   for(int i=0;i<j;i++){
    		   if(b.get(i)>max){
    			   max=b.get(i);
    		   }
    	   }
    	   return a.get(b.indexOf(max));
       }
       public void CompareCities(boolean weather){
              if(weather){
            	 System.out.println("No cities when the weather is rain!"); 
              }else{
            	 System.out.println("All cities accepted no matter the weather!");
              }
       }

}