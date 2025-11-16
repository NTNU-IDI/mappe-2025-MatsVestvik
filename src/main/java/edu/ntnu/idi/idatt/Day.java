package main.java.edu.ntnu.idi.idatt;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

public class Day {
    private String entry;
    private LocalDate date;
    private final String id;

    Day(String id){
        this.id = id;
        this.date = LocalDate.now();
    }

    public String getId() {return id;}

    public void addEntry(String content, String name){
        try(FileWriter writer = new FileWriter("src/main/resources/entries/"+name+".csv")){
            writer.write(content);
        }catch(IOException e){
            System.out.println("Something went wrong please try again");
        } 
    }

    public LocalDate getDate() {return date;}

    public void printDay(){
        System.out.println(entry);
    }
}
