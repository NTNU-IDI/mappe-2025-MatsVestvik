public class DiaryEntry {
    private final String author;
    private final String entry;
    private final int dayinmonth;
    private final int month;
    private final int year;

    DiaryEntry(String author, String entry, int dayinmonth, int month, int year){
        this.author = author;
        this.entry = entry;
        this.dayinmonth = dayinmonth;
        this.month = month;
        this.year = year;
    }

    //get motoder sortert som variablene på toppen
    public String getAuthor(){return this.author;}

    public String getEntry(){return this.entry;}

    public int getDayinmonth(){return this.dayinmonth;}

    public int getMonth(){return this.month;}

    public int getYear(){return this.year;}

    //quality of life metoder
    public String getDate(){
        return getDayinmonth()+" / "+getMonth()+" / "+getYear();
    }

    public void printDiary(){
        System.out.println(this.author + "     " + getDate() +  "\n\n" + this.entry);
    }


}
