package main.java.edu.ntnu.idi.idatt;

public class Main{
    public static void main(String[] args) {
        Author mats = new Author("Mats");

        mats.addDay();
        mats.addEntry("this is content. this would be on of the entrie for this day");

        mats.printAll();
    }
}