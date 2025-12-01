package edu.ntnu.idi.idatt.objects;

import java.time.LocalDate;

public class Day {
    /**
     * handles Day object to be put in days list for author
     */
    private String entry;
    private String date;
    private String title;
    private int rating;
    private final String id;

    /**
     * creates a day object with author for id creation
     * takes entry and rating and assosiates them to local variable
     * @param author
     * @param entry
     * @param rating
     */
    public Day(String author, String entry, int rating){
        this.id = LocalDate.now().toString() + author;
        this.entry = entry;
        this.date = LocalDate.now().toString();
        this.rating = rating;
    }

    /**
     * creates a day object with id
     * sets entry and rating to local variable
     * @param id
     * @param date
     * @param entry
     * @param rating
     */

    public Day(String id, String date, String entry, int rating, String title){
        this.id = id;
        this.entry = entry;
        this.date = date;
        this.rating = rating;
        this.title = title;
    }

    
    /**
     * getter for all local variables
     * @return
     */
    public String getId() {return id;}
    public String getContent() {return this.entry;}
    public int getRating() {return rating;}
    public String getDate() {return date;}
    public String getTitle() {return title;}

    /**
     * setters for contents in the day object
     * @param rating
     */
    public void setRating(int rating) {this.rating = rating;}
    public void setEntry(String entry) {this.entry = entry;}

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
                System.out.println("----------------------------------------");
                System.out.println(getContent() );
                System.out.println("----------------------------------------"); 
    }
    /*
    */

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
