package main.java.edu.ntnu.idi.idatt;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Author {
    private String name;
    //private String password;
    private List<Day> days;

    Author(String name){
        this.name = name;
        days = new ArrayList<>();
    }

    public String getName() {return name;}
    public int getDaysSize() {return days.size();}

    public void addDay(Day day){
        days.add(day);
    }

    public void addEntry(String content){
        for(int i = 0; i < days.size(); i++){
            if(LocalDateTime.now().equals(days.get(i).getDate())){
                days.get(i).addEntry(content);
            }
        }
    }

    public void printAll(){
        for(int i = 0 ; i < days.size(); i++){
            System.out.println(days.get(i).getDate());
            days.get(i).printDay();
        }
    }
}
