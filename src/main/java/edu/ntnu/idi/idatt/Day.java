package main.java.edu.ntnu.idi.idatt;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

public class Day {
    private String entry;
    private String date;
    private final String id;

    Day(String id, String entry){
        this.id = id;
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

    public void addEntry(String content, String name){
        try(FileWriter writer = new FileWriter("src/main/resources/entries/"+name+".csv", true)){
            writer.write(","+content+"\n");
        }catch(IOException e){
            System.out.println("Something went wrong please try again");
        } 
    }

    public String getDate() {return date;}

    public void printDay(){
        System.out.println(entry);
    }
}
