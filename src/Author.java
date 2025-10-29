import java.util.ArrayList;
import java.util.List;

public class Author {
    private String name;
    private String password;
    private List<Day> days;

    Author(String name){
        this.name = name;
        days = new ArrayList<>();
    }

    public String getName() {return name;}

    public void addDay(){
        Day newDay = new Day();
    }

    public void addEntry(String content){
        
    }
}
