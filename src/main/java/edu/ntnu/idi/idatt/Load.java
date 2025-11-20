package main.java.edu.ntnu.idi.idatt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class Load {
    Load(List<Author> authors){
        loadAuthorsFromCSV(authors);
        loadDaysFromCSV(authors);
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

    public void loadDaysFromCSV(List<Author> authors){
        for(Author author:authors){
            try(BufferedReader reader = new BufferedReader(new FileReader("src\\main\\resources\\entries\\"+author.getName()+".csv"))){
                String line;
        
                while ((line = reader.readLine()) != null) {
                    String[] values = line.split(",");
                    if (values.length > 0) {
                        String rowDate = values[0].trim();
                        String rowEntry = values[2].trim();
                        addDay(authors, author.getName(), rowDate, rowEntry);
                    }

                }
            }catch(IOException e){
                System.out.println("something went wrong");
            }
        }
    }

    public void addDay(List<Author> authors, String author, String date, String content){
        for(Author auth: authors){
            if(author.equals(auth.getName())){
                String ID = date + auth.getName();
                Day newDay = new Day(ID, date, content);
                auth.addDay(newDay);
                return;
            }
        }
        System.out.println("""
                    This author does not exist.
                    Please create new author or try again.
                    """); 
    }

    
}
