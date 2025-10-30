package main.java.edu.ntnu.idi.idatt;
import java.util.List;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Day {
    private List<String> entries;
    private LocalDateTime date;
    private final String id;

    Day(String id){
        this.id = id;
        entries = new ArrayList<>();
        this.date = LocalDateTime.now(); 
    }

    public String getId() {return id;}

    public void addEntry(String content){
        entries.add(content);
    }

    public LocalDateTime getDate() {return date;}

    public void printDay(){
        for(int i = 0; i< entries.size(); i++){
            System.out.println("-----------------------------------");
            System.out.println(entries.get(i));
            System.out.println();
        }
    }
}
