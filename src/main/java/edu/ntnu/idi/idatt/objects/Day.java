package edu.ntnu.idi.idatt.objects;

import java.time.LocalDate;

public class Day {
    private String entry;
    private String date;
    private int rating;
    private final String id;

    public Day(String author, String entry, int rating){
        this.id = LocalDate.now().toString() + author;
        this.entry = entry;
        this.date = LocalDate.now().toString();
        this.rating = rating;
    }

    public Day(String id, String date, String entry, int rating){
        this.id = id;
        this.entry = entry;
        this.date = date;
        this.rating = rating;
    }

    public String getId() {return id;}
    public String getContent() {return this.entry;}
    public int getRating() {return rating;}
    public void setRating(int rating) {this.rating = rating;}

    public void setEntry(String entry) {this.entry = entry;}

    public void addEntry(String content, String name){
        
    }

    public String getDate() {return date;}

    public void printDay(){
        System.out.println(entry);
    }
}
