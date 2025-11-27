package edu.ntnu.idi.idatt.objects;

import java.time.LocalDate;
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
    public void addDay(String author, String date, String content, int rating){
        for(Author auth: authors){
            if(author.equals(auth.getName())){
                String ID = date + auth.getName();
                Day newDay = new Day(ID, date, content, rating);
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
    public void addDayToday(String author, String content, int rating){
        for(Author auth: authors){
            if(author.equals(auth.getName())){
                Day newDay = new Day(author, content, rating);
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
    public void editDay(String date, String entry, String name, int rating){
        for(Author author: authors){
            if(name.equals(author.getName())){
                Day day = author.getDayByDate(date);
                if (day != null) {
                    day.setEntry(entry);
                } else {
                    // Create new day if it doesn't exist
                    String ID = date + author.getName();
                    Day newDay = new Day(ID, date, entry, rating);
                    author.addDay(newDay);
                }
                return;
            }
        }
        System.out.println("Author not found: " + name);
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
     * Returns a list of formatted strings: "Author - YYYY-MM-DD: <content>".
     */
    public java.util.List<String> searchEntries(String keyword) {
        java.util.List<String> results = new java.util.ArrayList<>();
        if (keyword == null || keyword.isEmpty()) return results;
        for (Author author : authors) {
            java.util.List<String> dates = author.findDatesByKeyword(keyword);
            for (String date : dates) {
                Day d = author.getDayByDate(date);
                if (d != null) {
                    results.add(author.getName() + " - " + date + ": " + d.getContent());
                }
            }
        }
        return results;
    }

    /**
     * returns a formatted String that can be printed
     * takes startdate enddate and keyword adn searces all days for day
     * that macthes parameters
     *  
     */ 

    public java.util.List<String> searchEntriesInTimeSpan(String keyword, String startDate, String endDate) {
        java.util.List<String> results = new java.util.ArrayList<>();
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        for (Author author : authors) {
            java.util.List<Day> matches = author.findDaysByKeywordInRange(keyword, start, end);
            for (Day d : matches) {
                results.add(author.getName() + " - " + d.getDate() + ": " + d.getContent());
            }
        }
        return results;
    }

    /**
     * prints all the diary entries for all days lists
     */

    public void printAllDiaries(){
        for (Author author: authors){
            System.out.println("----------------------------------------");
            System.out.println(author.getName());
            author.printAllContent();
        }
    }

    
}
