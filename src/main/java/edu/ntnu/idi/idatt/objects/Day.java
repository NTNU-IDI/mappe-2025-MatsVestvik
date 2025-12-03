package edu.ntnu.idi.idatt.objects;

public class Day {
    /**
     * handles Day object to be put in days list for author
     */
    private String entry;
    private String date;
    private String title;
    private int rating;

    /**
     * creates a day object
     * sets entry and rating to local variable
     * @param date
     * @param entry
     * @param rating
     */

    public Day(String date, String entry, int rating, String title){
        this.entry = entry;
        this.date = date;
        this.rating = rating;
        this.title = title;
    }

    
    /**
     * getter for all local variables
     * @return
     */
    public String getEntry() {return this.entry;}
    public int getRating() {return rating;}
    public String getDate() {return date;}
    public String getTitle() {return title;}

    /**
     * setters for contents in the day object
     * @param rating
     */
    public void setRating(int rating) {this.rating = rating;}
    public void setEntry(String entry) {this.entry = entry;}
    public void setTitle(String title) {this.title = title;}
    
    /**
     * appends additional text to the existing entry
     * @param additionalEntry
     */
    public void addToEntry(String additionalEntry) {
        if (this.entry == null || this.entry.isEmpty()) {
            this.entry = additionalEntry;
        } else {
            this.entry += "\n" + additionalEntry;
        }
    }
    /**
     * print the days entry
     */
    public void printEntry(){
        System.out.println(entry);
    }

    public void printDay(){
        System.out.println("----------------------------------------");
                System.out.println(getTitle());
                System.out.println(getDate() + "          Rating: " + getRating());
                System.out.println("\n" + getEntry() );
                System.out.println("----------------------------------------"); 
    }


    /**
     * Returns true if the day's content contains the given keyword (case-insensitive).
     * Returns false for null/empty keyword or null content.
     */
    public boolean containsKeyword(String keyword) {
        if (keyword == null || keyword.isEmpty()) return false;
        if (this.entry == null) return false;
        return this.entry.toLowerCase().contains(keyword.toLowerCase());
    }
}
