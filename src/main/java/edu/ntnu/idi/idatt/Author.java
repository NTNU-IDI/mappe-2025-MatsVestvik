package main.java.edu.ntnu.idi.idatt;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.swing.text.html.HTMLDocument.Iterator;

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

    public Day getDayByDate(String date){
        for (Day day : days){
            if(day.getDate().equals(date)){
                return day;
            }
        }
        return null;
    }

    public void addDay(Day inputDay) {
        Day existingDay = null;
        for (Day day : days) {
            if (day.getDate().equals(inputDay.getDate())) {
                existingDay = day;
                break;
            }
        }
        if (existingDay != null) {
            days.remove(existingDay);
        }
        days.add(inputDay);
    }

    public List<Day> getSortDays(List<Day> unsortedDays) {
        unsortedDays.sort(Comparator.comparing(Day::getDate));
        return unsortedDays;
    }

    public void printAll(){
        for(Day day: days){
            System.out.println("    "+day.getDate());
        }
    }
}
