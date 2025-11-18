package main.java.edu.ntnu.idi.idatt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class Initialize {

    Initialize(){
    }
    public void initializeAuthorsFromCSV(List<Author> authors) {
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

    

    
}
