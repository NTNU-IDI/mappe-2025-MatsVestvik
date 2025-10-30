package main.java.edu.ntnu.idi.idatt;
import java.time.LocalDateTime;

public class Day {
    private String entry;
    private LocalDateTime date;
    private final String id;

    Day(String id){
        this.id = id;
        this.date = LocalDateTime.now(); 
    }

    public String getId() {return id;}

    public void addEntry(String content){
        this.entry += content;
    }

    public LocalDateTime getDate() {return date;}

    public void printDay(){
        System.out.println(this.entry);
    }
}
