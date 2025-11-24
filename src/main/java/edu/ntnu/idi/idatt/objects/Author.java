package edu.ntnu.idi.idatt.objects;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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

    public void removeDay(String date){
        Day dayToRemove = null;
        for(Day day: days){
            if(day.getDate().equals(date)){
                dayToRemove = day;
                break;
            }
        }
        if(dayToRemove != null){
            days.remove(dayToRemove);
        }
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

    public double getAvrgRating(){
        if (days.size() == 0) {
            return 0;
        }
        else{
            int tot = 0;
            for (Day day: days){
                tot+=day.getRating();
            }
            return tot/days.size();
        }
        
    }

    public void printAllContent(){
        for (Day day: days){
            System.out.println(day.getDate() + day.getRating());
            System.out.println(day.getContent());
        }
    }

    
}
