package main.java.edu.ntnu.idi.idatt;

import java.time.LocalDate;

public class Day {
    private String entry;
    private String date;
    private final String id;

    Day(String author, String entry){
        this.id = LocalDate.now().toString() + author;
        this.entry = entry;
        this.date = LocalDate.now().toString();
    }

    Day(String id, String date, String entry){
        this.id = id;
        this.entry = entry;
        this.date = date;
    }

    public String getId() {return id;}
    public String getContent() {return this.entry;}

    public void setEntry(String entry) {this.entry = entry;}

    public void addEntry(String content, String name){
        
    }

    public String getDate() {return date;}

    public void printDay(){
        System.out.println(entry);
    }
}
