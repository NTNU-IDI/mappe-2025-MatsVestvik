package main.java.edu.ntnu.idi.idatt;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import java.io.FileWriter;
import java.io.IOException;

public class Author {
    private String name;
    //private String password;
    private List<Day> days;

    Author(String name){
        this.name = name;
        days = new ArrayList<>();

        try(FileWriter writer = new FileWriter("src/main/resources/entries/"+name+".csv")){
            
        }catch(IOException e){
            System.out.println("Something went wrong please try again");
        } 
    }

    public String getName() {return name;}
    public int getDaysSize() {return days.size();}

    public void addDay(Day day){
        days.add(day);
    }

    public void addEntry(String content){
        for(int i = 0; i < days.size(); i++){
            if(LocalDate.now().equals(days.get(i).getDate())){
                days.get(i).addEntry(content, this.name);
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
