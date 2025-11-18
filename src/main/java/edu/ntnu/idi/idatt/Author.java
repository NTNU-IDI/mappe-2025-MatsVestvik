package main.java.edu.ntnu.idi.idatt;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Author {
    private String name;
    //private int pin;
    private List<Day> days;

    Author(String name){
        this.name = name;
        days = new ArrayList<>();

        try(FileWriter writer = new FileWriter("src/main/resources/entries/"+name+".csv", true)){
            
        }catch(IOException e){
            System.out.println("Something went wrong please try again");
        } 
    }

    public String getName() {return name;}
    public int getDaysSize() {return days.size();}

    public List<Day> getListDays() {return days;}

    public boolean searchDays(String date) {
        String targetDate = date;
        
        try(BufferedReader br = new BufferedReader(new FileReader("src\\main\\resources\\entries\\"+name+".csv"))){
            String line;
    
            while ((line = br.readLine()) != null) {
                
                String[] values = line.split(",");
                if (values.length > 0) {
                    String rowDate = values[0].trim();
                    if (rowDate.equals(targetDate)) {
                        return true; // Date exists
                    }
                }
            }
        } catch(IOException e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }
        return false; // Date doesn't exist
    }

    public void addDay(Day day){

        if (searchDays(day.getDate())) {
            System.out.println("this day is already in csv");
        }
        else{
                
            days.add(day);

            try(FileWriter writer = new FileWriter("src/main/resources/entries/"+name+".csv", true)){
                writer.write(day.getDate().toString() +","+ name+","+day.getContent()+"\n");
                System.out.println("succsessfully wrote to csv");
            }catch(IOException e){
                System.out.println("Something went wrong please try again");
            } 
        }
    
    }

    public void addEntry(String content){
        for(int i = 0; i < days.size(); i++){
            if(LocalDate.now().toString().equals(days.get(i).getDate())){
                days.get(i).addEntry(content, this.name);
            }
        }
    }

    public void printAll(){
        for(int i = 0 ; i < days.size(); i++){
            System.out.println(days.get(i).getDate());
            days.get(i).printDay();
        }
    }
}
