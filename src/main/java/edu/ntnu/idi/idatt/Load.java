package main.java.edu.ntnu.idi.idatt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class Load {
    Load(List<Author> authors, HashMap<String, Day> days){
        loadAuthorsFromCSV(authors);
        loadDaysFromCSV(days, authors);
    }
    public void loadAuthorsFromCSV(List<Author> authors) {
        String entriesDirectory = "src/main/resources/entries/";
        File dir = new File(entriesDirectory);
        
        // Get all CSV files
        File[] csvFiles = dir.listFiles((d, name) -> name.toLowerCase().endsWith(".csv"));
        
        if (csvFiles == null || csvFiles.length == 0) {
            System.out.println("No author CSV files found.");
            return;
        }
        
        // Create Author objects for each CSV file
        for (File csvFile : csvFiles) {
            String fileName = csvFile.getName();
            String authorName = fileName.substring(0, fileName.lastIndexOf('.'));
            
            // Create and add the author
            Author author = new Author(authorName);
            authors.add(author);
            System.out.println("Loaded author: " + authorName);
        }
        
        System.out.println("AuthorRegister initialized with " + authors.size() + " authors.");
    }

    public void loadDaysFromCSV(HashMap<String, Day> days, List<Author> authors){
        for(Author author:authors){
            try(BufferedReader reader = new BufferedReader(new FileReader("src\\main\\resources\\entries\\"+author.getName()+".csv"))){
                String line;
        
                while ((line = reader.readLine()) != null) {
                    String[] values = line.split(",");
                    if (values.length > 0) {
                        String rowDate = values[0].trim();
                        String rowEntry = values[2].trim();
                        addDay(author.getName(), rowDate, rowEntry, days, authors);
                    }

                }
            }catch(IOException e){
                System.out.println("something went wrong");
            }
        }
    }

    public void addDay(String auth, String date, String content, HashMap<String, Day> days, List<Author> authors){
        for(int i = 0; i < authors.size(); i++){
            if (auth.equalsIgnoreCase(authors.get(i).getName())) {
                String ID = date + authors.get(i).getName();
                
                if (days.containsKey(ID)){
                    System.out.println("""
                        This day already exists for this author.
                        Please edit instead :)
                        """);
                }
                else{
                    Day newday = new Day(ID, date, content);
                    days.put(ID, newday); 
                    authors.get(i).addDay(newday); 
                    System.out.println("day added successfully to map");
                }
                return;
            }
        }
        System.out.println("""
                    This author does not exist.
                    Please create new author or try again.
                    """); 
    }

    
}
