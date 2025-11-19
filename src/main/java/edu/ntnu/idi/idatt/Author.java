package main.java.edu.ntnu.idi.idatt;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Author {
    private String name;
    //private int pin;
    private List<Day> days;

    Author(String name){
        this.name = name;
        days = new ArrayList<>();
    }

    public String getName() {return name;}
    public int getDaysSize() {return days.size();}

    public List<Day> getListDays() {return days;}

    public boolean searchDays(String date) {
        for (Day day : days){
            if (day.getDate().equals(date)) {
                return true;
            }
        }
        return false;
    }

    public void addDay(Day day){
        if (searchDays(name)) {
            System.out.println("This day already exists");
        }
        else{
            days.add(day);
        }
    }

    public List<Day> getSortDays(List<Day> unsortedDays) {
        unsortedDays.sort(Comparator.comparing(Day::getDate));
        return unsortedDays;
    }

    public void printAll(){
        for(int i = 0 ; i < days.size(); i++){
            System.out.println(days.get(i).getDate());
            days.get(i).printDay();
        }
    }
}
