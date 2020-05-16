package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionListener;

public class Gui extends JFrame{
    JTextField tName,tAge,tCities,tMuseums,tCafes,tRestaurants,tBars;
    JButton b;
    DefaultListModel<String> category= new DefaultListModel<>();
    JList<String> tCategory=new JList<>();
    DefaultListModel<String> tt10= new DefaultListModel<>();
    JList <String> tWeather=new JList<>();


    public boolean pressed = false;


    private JFrame frame ;
    private String choice;
    private String name;
    private int age;
    private String io;
    private int museums;
    private int cafes;
    private int restaurants;
    private int bars;
    private String weather;
    public boolean closed = false;


    public String getChoice() {
        return choice;
    }

    public String getName() {
        return name;
    }

    public void setPressed(boolean pressed) {
        this.pressed = pressed;
    }

    public boolean isPressed() {
        return pressed;
    }

    public int getAge() {
        return age;
    }

    public String getIo() {
        return io;
    }

    public int getMuseums() {
        return museums;
    }

    public int getCafes() {
        return cafes;
    }

    public int getRestaurants() {
        return restaurants;
    }

    public int getBars() {
        return bars;
    }

    public String getWeather() {
        return weather;
    }

    public Gui(){}



    JPanel mainPanel;

    public void mainGUI(){


        frame = new JFrame("Java SWING Examples");
        frame.setSize(900,800);
        frame.setLayout(null);


        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
                closed = true;
                frame.dispose();
            }
        });



        ioInit();

       frame.setVisible(true);




    }




    private final JLabel resultNotification =new JLabel("",SwingConstants.CENTER);




    public void ioInit(){



        tt10= new DefaultListModel<>();
        tt10.addElement("true");
        tt10.addElement("false");
        tWeather=new JList<>(tt10);

        tCategory.setBounds(100,100, 75,75);

        tName=new JTextField(10);

        tAge=new JTextField(2);

        tCities=new JTextField(30);
        tMuseums=new JTextField(2);

        tCafes=new JTextField(2);

        tRestaurants=new JTextField(2);

        tBars=new JTextField(2);

        DefaultListModel<Boolean> rain= new DefaultListModel<>();
        rain.addElement(true);
        rain.addElement(false);
        tWeather.setBounds(100,100, 75,75);

        b=new JButton("Submit");


        mainPanel = new JPanel();
        mainPanel.setBounds(40,30,800,500);

        JPanel resultPanel = new JPanel();
        resultPanel.setBounds(40,550,800,200);



        JLabel lCategory=new JLabel("<html><h3>Choose a category:a )Tourist,<br>b )Traveler,c )business:</h3></html>");
        category=new DefaultListModel<>();
        category.addElement("a");
        category.addElement("b");
        category.addElement("c");
        tCategory=new JList<>(category);

        tt10= new DefaultListModel<>();
        tt10.addElement("true");
        tt10.addElement("false");
        tWeather=new JList<>(tt10);

        tCategory.setBounds(100,100, 75,75);
        JLabel lName=new JLabel("<html><h3>Give name:</h3></html>");

        tName=new JTextField(10);
        JLabel lAge=new JLabel("<html><h3>Give age:</h3></html>");

        tAge=new JTextField(2);
        JLabel lCities=new JLabel("<html><h3>give cities (format:GR Athens,FR Paris):</h3></html>");

        tCities=new JTextField(30);
        JLabel lTips=new JLabel("<html><h3>for the next fields give a number greater than zero if you want it in your destination:</h3></html>");
        JLabel lMuseums=new JLabel("<html><h3>Do you want museums?</h3></html>");
        tMuseums=new JTextField(2);

        JLabel lCafes=new JLabel("<html><h3>Do you want Cafes?</h3></html>");
        tCafes=new JTextField(2);

        JLabel lRestaurants=new JLabel("<html><h3>Do you want restaurants?</h3></html>");
        tRestaurants=new JTextField(2);

        JLabel lBars=new JLabel("<html><h3>Do you want bars?</h3></html>");
        tBars=new JTextField(2);

        JLabel lWeather=new JLabel("<html><h3>Do you want to exclude cities in which rains at this moment. Write true or false:</h3></html>");
         rain= new DefaultListModel<>();
        rain.addElement(true);
        rain.addElement(false);
        tWeather.setBounds(100,100, 75,75);

        b=new JButton("Submit");

        mainPanel.add(lCategory);
        mainPanel.add(tCategory);
        mainPanel.add(new JLabel("<html><br><hr></html"));
        mainPanel.add(lName);
        mainPanel.add(tName);
        mainPanel.add(lAge);
        mainPanel.add(tAge);
        mainPanel.add(lCities);
        mainPanel.add(tCities);
        mainPanel.add(lTips);
        mainPanel.add(lMuseums);
        mainPanel.add(tMuseums);
        mainPanel.add(lCafes);
        mainPanel.add(tCafes);
        mainPanel.add(lRestaurants);
        mainPanel.add(tRestaurants);
        mainPanel.add(lBars);
        mainPanel.add(tBars);
        mainPanel.add(lWeather);
        mainPanel.add(tWeather);
        mainPanel.add(b);
        mainPanel.setLayout(new FlowLayout());

        resultPanel.add(resultNotification);
        mainPanel.setLayout(new FlowLayout());
        frame.add(mainPanel);
        frame.add(resultPanel);




        b.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent e) {

                try {
                    choice      = tCategory.getSelectedValue();
                    name        = tName.getText();
                    age         = Integer.parseInt(tAge.getText());
                    io          = tCities.getText();
                    museums     = Integer.parseInt(tMuseums.getText());
                    cafes       = Integer.parseInt(tCafes.getText());
                    restaurants = Integer.parseInt(tRestaurants.getText());
                    bars        = Integer.parseInt(tBars.getText());
                    weather     = tWeather.getSelectedValue();

                    if(age <= 18 || age > 90){
                        resultNotification.setText("<html><h1>Please give valid age</h1></html>");
                    }else {
                        pressed = true;
                    }

            }catch(NumberFormatException e1){
                    resultNotification.setText("<html><h1>Please give a number</h1></html>");
                }
            }
        });

    }


    void resultNotification(String s){
        resultNotification.setText("<html><h1>"+s +"</h1></html>");
    }

}