public class DiaryEntry{
    private String content;
    private Author author;
    private final int dd;
    private final int mm;
    private final int yyyy;

    DiaryEntry(String content, int dd, int mm, int yyyy){
        this.content = content;
        this.dd = dd;
        this.mm = mm;
        this.yyyy= yyyy;
    }

    public String getContent() {return content;}
    public int getDd() {return dd;}
    public int getMm() {return mm;}
    public int getYyyy() {return yyyy;}

    public String getDate(){
        return dd+"/"+mm+"-"+yyyy;
    }

    public void printEntry(){
        System.out.println(getDate());
        System.out.println(getContent());
    }

    
}