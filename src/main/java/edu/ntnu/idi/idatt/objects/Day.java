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
     * Checks and sets rating between 1 and 10
     * @param rating
     */
    public void setRating(int rating) {
        if (rating < 1 || rating > 10) {
            throw new IllegalArgumentException("Rating must be between 1 and 10");
        }
        this.rating = rating;
    }

    /**
     * sets the entry content for the day adn checks for invalid characters
     * @param entry
     */
    public void setEntry(String entry) {
        if (entry.contains("|")) {
            throw new IllegalArgumentException("Entry cannot contain '|' character");
        }
        this.entry = entry;
    }

    /**
     * validates that title is not null and not too long
     * @param title
     */
    public void setTitle(String title) {
        if (title == null) {
            throw new IllegalArgumentException("Title cannot be null");
        }
        if (title.length() > 35) {
            throw new IllegalArgumentException("Title cannot exceed 35 characters. Length: " + title.length());
        }
        if (title.contains("|")) {
            throw new IllegalArgumentException("Title cannot contain '|' character");
        }
        if (title.contains("\n")) {
            throw new IllegalArgumentException("Title cannot contain newline characters");
        }
        this.title = title;
    }
        
    /**
     * appends additional text to the existing entry
     * @param additionalEntry
     */
    public void addToEntry(String additionalEntry) {
        if (this.entry == null || this.entry.isEmpty()) {
            setEntry(additionalEntry);
        } else {
            setEntry(this.entry + "\n\n" + additionalEntry);
        }
    }

    public static String addTimeToEntry(String entry) {
        String timeStamp = java.time.LocalTime.now().withNano(0).toString();
        String dateStamp = java.time.LocalDate.now().toString();
        return "-Written: [" + dateStamp + "] [" + timeStamp + "]\n" + entry;
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
