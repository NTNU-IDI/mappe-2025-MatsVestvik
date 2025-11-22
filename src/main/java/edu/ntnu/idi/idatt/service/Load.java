package edu.ntnu.idi.idatt.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import edu.ntnu.idi.idatt.objects.Author;
import edu.ntnu.idi.idatt.objects.Day;

public class Load {
    public Load(List<Author> authors){
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
            
            // Read the PIN from the CSV header
            int authorPin = readPinFromCSV(csvFile);
            
            // Create and add the author
            Author author = new Author(authorName, authorPin);
            authors.add(author);
            System.out.println("Loaded author: " + authorName + " with PIN: " + authorPin);
        }
        
        System.out.println("AuthorRegister initialized with " + authors.size() + " authors.");
    }

    private int readPinFromCSV(File csvFile) {
        int defaultPin = 1111; // Default PIN if not found
        
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
            String firstLine = reader.readLine();
            if (firstLine != null && firstLine.startsWith("#")) {
                // Parse the header line to extract PIN
                if (firstLine.contains("PIN:")) {
                    String pinPart = firstLine.split("PIN:")[1].trim();
                    // Extract just the numeric part
                    String pinString = pinPart.split("[,\\s]")[0].trim();
                    try {
                        return Integer.parseInt(pinString);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid PIN format in file: " + csvFile.getName() + ", using default PIN");
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading PIN from file: " + csvFile.getName());
        }
        
        return defaultPin;
    }

    public void loadDaysFromCSV(List<Author> authors){
        for(Author author : authors){
            String filePath = "src/main/resources/entries/" + author.getName() + ".csv";
            try(BufferedReader reader = new BufferedReader(new FileReader(filePath))){
                String line;
                
                while ((line = reader.readLine()) != null) {
                    // Skip header lines (lines starting with #)
                    if (line.startsWith("#")) {
                        continue;
                    }
                    
                    // Split by pipe separator instead of comma
                    String[] values = line.split("\\|", 2); // Split into max 2 parts
                    if (values.length >= 2) {
                        String rowDate = values[0].trim();
                        String rowEntry = values[1].trim();
                        // Replace escaped newlines with actual newlines
                        rowEntry = rowEntry.replace("\\n", "\n");
                        addDay(authors, author.getName(), rowDate, rowEntry);
                    } else {
                        System.out.println("Invalid line format in " + author.getName() + ".csv: " + line);
                    }
                }
            } catch(IOException e){
                System.out.println("Error loading days for author " + author.getName() + ": " + e.getMessage());
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
        System.out.println("This author does not exist.");
        System.out.println("Please create new author or try again.");
    }
}