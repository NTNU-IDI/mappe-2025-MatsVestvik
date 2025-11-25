package edu.ntnu.idi.idatt.objects;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Author {

    /**
     * handles the author object
     */

    private String name;
    private int pin;
    private List<Day> days;

    /**
     * creates author object with name and pin and days arraylist
     */
 
    public Author(String name, int pin){
        this.name = name;
        this.pin = pin;
        days = new ArrayList<>();
    }

    /**
     * all getters for variables in author class
     * @return
     */
    public String getName() {return name;}
    public int getDaysSize() {return days.size();}
    public List<Day> getListDays() {return days;}
    public int getPin() {return pin;}

    /**
     * checks input pin to pin in object fro verification
     * @param pin
     * @return
     */
    public boolean checkPin(int pin){
        if(pin == this.pin){
            return true;
        }
        return false;
    }

    /**
     * setters chages variables
     * @param pin
     * @param name
     */
    public void setPin(int pin){this.pin = pin;}
    public void setName(String newName){this.name = newName;}

    /**
     * serchdays checks if day with date exists in days
     * returns boolean
     * @param date
     * @return
     */
    public boolean searchDays(String date) {
        for (Day day : days){
            if (day.getDate().equals(date)) {
                return true;
            }
        }
        return false;
    }

    /**
     * returns day at specific date
     * @param date
     * @return
     */
    public Day getDayByDate(String date){
        for (Day day : days){
            if(day.getDate().equals(date)){
                return day;
            }
        }
        return null;
    }

    /**
     * checks if day with this date exists and deltes it
     * add new day to days list
     * @param inputDay
     */

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

    /**
     * remove day removes a day from days list
     * @param date
     */
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

    /**
     * get rating for day with specific date
     * return -1 if day with date does not exist
     * @param date
     * @return
     */

    public int getDayRating(String date){
        for(Day day : days){
            if(day.getDate().equals(date)){
                return day.getRating();
            }
        }
        return -1; // Return -1 if day not found
    }

    /**
     * readday prints the entry of the specified day
     * @param date
     * @return
     */

    public String readDay(String date){
        for(Day day : days){
            if(day.getDate().equals(date)){
                return day.getContent();
            }
        }
        return null;
    }

    /**
     * returns days list sorted by date
     * @param unsortedDays
     * @return
     */

    public List<Day> getSortDays(List<Day> unsortedDays) {
        unsortedDays.sort(Comparator.comparing(Day::getDate));
        return unsortedDays;
    }

    /**
     * prints all days in a list sorted by date
     */
    public void printAll(){
        List<Day> sortedDays = getSortDays(days);
        for(Day day: sortedDays){
            System.out.println("    "+day.getDate());
        }
    }

    /**
     * calculates the avrg rating of days in days returns avrg
     * @return
     */

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

    /**
     * function to print all entries
     */

    public void printAllContent(){
        for (Day day: days){
            System.out.println("_");
            System.out.println("    "+day.getDate() +"      "+ day.getRating());
            System.out.println("    "+day.getContent());
        }
    }

    /**
     * Return a list of Day objects from this author that match keyword in content.
     * If keyword is null or empty, returns an empty list.
     */
    public java.util.List<Day> findDaysByKeyword(String keyword) {
        java.util.List<Day> result = new java.util.ArrayList<>();
        if (keyword == null || keyword.isEmpty()) return result;
        for (Day day : days) {
            if (day.containsKeyword(keyword)) {
                result.add(day);
            }
        }
        return result;
    }

    /**
     * Return a list of date strings for days whose content matches the keyword.
     * Useful for building concise search results without exposing Day objects.
     */
    public java.util.List<String> findDatesByKeyword(String keyword) {
        java.util.List<String> result = new java.util.ArrayList<>();
        if (keyword == null || keyword.isEmpty()) return result;
        for (Day day : days) {
            if (day.containsKeyword(keyword)) {
                result.add(day.getDate());
            }
        }
        return result;
    }

    /**
     * Return a list of Day objects within the date range inclusive whose content matches the keyword.
     * If the keyword is empty or null, returns all days within the range.
     */
    public java.util.List<Day> findDaysByKeywordInRange(String keyword, java.time.LocalDate start, java.time.LocalDate end) {
        java.util.List<Day> result = new java.util.ArrayList<>();
        boolean matchAll = (keyword == null || keyword.isEmpty());
        for (Day day : days) {
            java.time.LocalDate dayDate = java.time.LocalDate.parse(day.getDate());
            if ((dayDate.isEqual(start) || dayDate.isAfter(start)) && (dayDate.isEqual(end) || dayDate.isBefore(end))) {
                if (matchAll || day.containsKeyword(keyword)) {
                    result.add(day);
                }
            }
        }
        return result;
    }

    
}
