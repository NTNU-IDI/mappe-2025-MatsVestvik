package edu.ntnu.idi.idatt.objects;

import java.time.LocalDate;
import edu.ntnu.idi.idatt.util.DateUtils;
import java.util.ArrayList;
import java.util.List;

public class AuthorRegister {
    /**
     * this is a list of all authors in the system
     */
    private List<Author> authors;

    /**
     * creates a new authors list
     */
    public AuthorRegister(){
        authors = new ArrayList<>();
    }

    /**
     * get authors list
     * @return
     */
    public List<Author> getAuthors(){ return authors;}
    /**
     * returns a list of all days from all authors
     * @return
     */
    public java.util.List<Day> getAllDays(){
        java.util.List<Day> allDays = new java.util.ArrayList<>();
        for (Author author : authors) {
            allDays.addAll(author.getListDays());
        }
        return allDays;
    }

    public String getAuthorName(int pos){
        return authors.get(pos).getName();
    }

    /**
     * return author object of object with matching name
     * @param name
     * @return
     */
    public Author getAuthorByName(String name){
        for (Author author : authors){
            if (name.equals(author.getName())){
                return author;
            }
        }
        return null;
    }

        /**
     * get statistics for specified user
     * @param author
     */

    public void getStatistics(String author){
        double avg = getAuthorByName(author).getAvrgRating();
        int numDays = getAuthorByName(author).getDaysSize();
        System.out.println("Avrg rating: " + avg + " | Num of days: " + numDays);
    }

    /**
     * print all statistics in a nice easy to vie format
     */
    public void getStatisticsAll(){
        int totalDays = 0;
        double totalAvrgRating = 0;
        for (Author author:authors){
            System.out.println("----------------------------------------");
            System.out.println(author.getName());
            getStatistics(author.getName());
            totalAvrgRating += author.getAvrgRating();
            totalDays += author.getDaysSize();
        }
        System.out.println("----------------------------------------");
        System.out.println("Total num of days: "+totalDays +"\nAvrg avrg rating: " +totalAvrgRating/authors.size());
    }  

    /**
     * sanitizes input, ensures the author name does not exist in system. 
     * ensures name value is uniqe 
     * @param name
     * @param pin
     */
    public void addNewAuthor(String name, int pin) {
        for (Author author : authors) {
            if (author.getName().equals(name)) {
                System.out.println("This author already exists");
                return;
            }
        }
        
        Author newAuthor = new Author(name, pin);
        authors.add(newAuthor);
    }


    /**
     * allow to find day in specific authors days list
     * @param auth
     * @param date
     * @return
     */
    public boolean searchDays(String auth, LocalDate date){
        for(Author author:authors){
            if(author.getName().equals(auth)){
                return author.searchDays(date.toString());
            }
        }
        return false;
    }


    /**
     * Allows adding a day to specific author days list
     * @param author
     * @param date
     * @param content
     * @param rating
     */
    public void addDay(String author, String date, String content, int rating, String title){
        for(Author auth: authors){
            if(author.equals(auth.getName())){
                Day newDay = new Day(date, content, rating, title);
                auth.addDay(newDay);
                return;
            }
        }
        System.out.println("""
                    This author does not exist.
                    Please create new author or try again.
                    """); 
    }
    
    /**
     * add a day to specific days list with todays date
     * @param author
     * @param content
     * @param rating
     */
    public void addDayToday(String author, String content, int rating, String title){
        addDay(author, LocalDate.now().toString(), content, rating, title);
    } 


    /**
     * print all author names numbered to be used in a menu display. 
     */
    public void printAllAuthors(){
        for(int i = 0; i<authors.size(); i++){
            int num = i+1;
            System.out.println("    "+num+". "+authors.get(i).getName());
        }
    }
    /**
     * removes author with matching name from register
     * @param auth
     */

    public void deleteAuthor(String auth) {
        authors.removeIf(author -> author.getName().equals(auth));
    }
    /**
     * retrieces name of author in pos
     * used in for loops
     * @param pos
     * @return
     */



    /**
     * returns int of position to author object in authors list
     * @param name
     * @return
     */

    public int getAuthorPos(String name){
        int pos = 0;
        for (Author author : authors){
            pos++;
            if (name.equals(author.getName())){
                return pos;
            }
        }
        return pos + 1;
    }

    /**
     * 
     * @param date
     * @param entry
     * @param name
     * @param rating
     */
    public void editDay(String date, String entry, String name, int rating, String title){
        for(Author author: authors){
            if(name.equals(author.getName())){
                Day day = author.getDayByDate(date);
                if (day != null) {
                    day.setEntry(entry);
                } else {
                    Day newDay = new Day(date, entry, rating, title);
                    author.addDay(newDay);
                }
                return;
            }
        }
        System.out.println("Author not found: " + name);
    }



    /**
     * print all the days assosiated with a author. 
     * @param author
     */

    public void printDaysAuthor(Author author){
        for (int i = 0; i < author.getDaysSize(); i++){
            System.out.println(author.getListDays().get(i).toString());
        }
    }

    /**
     * Search across all authors' days for diary entries containing the given keyword.
     * The search is case-insensitive and matches if the entry content contains the keyword as a substring.
     * Returns a list of Day objects. The caller may use `getAuthorOfDay(Day)` if author info is required.
     */
    public java.util.List<Day> searchEntries(String keyword, java.util.List<Day> days) {
        java.util.List<Day> results = new java.util.ArrayList<>();
        if (keyword == null || keyword.isEmpty()) return new java.util.ArrayList<>(days);
        for (Author author : authors) {
            java.util.List<Day> matches = author.findDaysByKeyword(keyword);
            for (Day d : matches) {
                results.add(d);
            }
        }
        return results;
    }

    /** Convenience overload that searches across all days in the register. */
    public java.util.List<Day> searchEntries(String keyword) {
        return searchEntries(keyword, getAllDays());
    }

    /**
     * returns a formatted String that can be printed
     * takes startdate enddate and keyword adn searces all days for day
     * that macthes parameters
     *  
     */ 

    public java.util.List<Day> searchEntriesInTimeSpan(String keyword, String startDate, String endDate, java.util.List<Day> days) {
        java.util.List<Day> results = new java.util.ArrayList<>();
        // Validate and parse dates; DateUtils will throw IllegalArgumentException on invalid input
        LocalDate start = DateUtils.parseIsoDate(startDate);
        LocalDate end = DateUtils.parseIsoDate(endDate);
        if (end.isBefore(start)) {
            throw new IllegalArgumentException("End date must be the same or after start date");
        }
        for (Author author : authors) {
            java.util.List<Day> matches = author.findDaysByKeywordInRange(keyword, start, end);
            for (Day d : matches) {
                results.add(d);
            }
        }
        return results;
    }

    /** Convenience overload that searches across all days in the register. */
    public java.util.List<Day> searchEntriesInTimeSpan(String keyword, String startDate, String endDate) {
        return searchEntriesInTimeSpan(keyword, startDate, endDate, getAllDays());
    }

    public void printDays(java.util.List<Day> days){
        for (Day day: days){
            String authorName = getAuthorOfDay(day);
            System.out.println("----------------------------------------");
            System.out.println("Author: " + authorName);
            day.printDay();
        }
    }

    /**
     * prints all the diary entries for all days lists
     */

    public void printAllDiaries(){
        for (Author author: authors){
            System.out.println("----------------------------------------");
            System.out.println("              "+author.getName());

            author.printAllContent();
        }
    }
    
    /**
     * Returns the author name for the specified Day object if it exists in the register,
     * otherwise returns null. This is useful when searches return Day objects but callers
     * still need to display the author associated with a day.
     */
    public String getAuthorOfDay(Day day) {
        if (day == null) return null;
        for (Author author : authors) {
            for (Day d : author.getListDays()) {
                if (d == day) { // identity comparison is valid because we return the same Day instances
                    return author.getName();
                }
            }
        }
        return null;
    }
}
