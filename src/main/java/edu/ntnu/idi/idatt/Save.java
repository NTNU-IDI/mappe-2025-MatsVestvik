package main.java.edu.ntnu.idi.idatt;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class Save {
    public static void saveToCSV(List<Author> authors, HashMap<String, Day> days){
        for (Author author : authors){
            try(FileWriter writer = new FileWriter("src/main/resources/entries/"+author.getName()+".csv", true)){
                for (int i = 0; i < author.getDaysSize(); i++){
                    writer.write(author.getListDays().get(i).getDate() + "," + author.getName() + ","+ author.getListDays().get(i).getContent()+"\n");
                }
            }catch(IOException e){
                System.out.println("Something went wrong please try again");
            } 
        }
    }
}
