package edu.ntnu.idi.idatt.objects;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.swing.text.html.HTMLDocument.Iterator;

public class Author {
    private String name;
    private int pin;
    private List<Day> days;

    public Author(String name, int pin){
        this.name = name;
        this.pin = pin;
        days = new ArrayList<>();
    }

    public String getName() {return name;}
    public int getDaysSize() {return days.size();}
    public List<Day> getListDays() {return days;}
    public int getPin() {return pin;}

    public boolean checkPin(int pin){
        if(pin == this.pin){
            return true;
        }
        return false;
    }

    public void setPin(int pin){
        this.pin = pin;
    }

    public void setName(String newName){
        this.name = newName;
    }

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

    public int getDayRating(String date){
        for(Day day : days){
            if(day.getDate().equals(date)){
                return day.getRating();
            }
        }
        return -1; // Return -1 if day not found
    }

    public String readDay(String date){
        for(Day day : days){
            if(day.getDate().equals(date)){
                return day.getContent();
            }
        }
        return null;
    }

    public List<Day> getSortDays(List<Day> unsortedDays) {
        unsortedDays.sort(Comparator.comparing(Day::getDate));
        return unsortedDays;
    }

    public void printAll(){
        List<Day> sortedDays = getSortDays(days);
        for(Day day: sortedDays){
            System.out.println("    "+day.getDate());
        }
    }

    
}
