package main.java.edu.ntnu.idi.idatt;
import java.util.List;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Day {
    private List<DiaryEntry> entries;
    private LocalDateTime date;

    Day(){
        entries = new ArrayList<>();
        this.date = LocalDateTime.now(); 
    }

    public void addEntry(String content){
        DiaryEntry newEntry = new DiaryEntry(content);
        entries.add(newEntry);
    }

    public LocalDateTime getDate() {return date;}
    public List<DiaryEntry> getEntries() {return entries;}

    public void printDay(){
        for(int i = 0; i< entries.size(); i++){
            System.out.println("-----------------------------------");
            System.out.println(entries.get(i).getContent());
            System.out.println();
        }
    }
}
