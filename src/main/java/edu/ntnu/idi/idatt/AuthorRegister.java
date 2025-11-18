package main.java.edu.ntnu.idi.idatt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

public class AuthorRegister {
    private List<Author> authors;
    private HashMap<String, Day> days;

    AuthorRegister(){
        authors = new ArrayList<>();
        days = new HashMap<>();

        Load load = new Load(authors, days);
    }

    public void addNewAuthor(String name) {
        for (Author author : authors) {
            if (author.getName().equals(name)) {
                System.out.println("This author already exists");
                return;
            }
        }
        
        Author newAuthor = new Author(name);
        authors.add(newAuthor);
        System.out.println("adding new author...");
    }

    public boolean searchDays(String auth, LocalDate date){
        for(Author author:authors){
            if(author.getName().equals(auth)){
                return author.searchDays(date.toString());
            }
        }
        return false;
    }
    public void addDay(String auth, String date, String content){
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
    

    public void addDayToday(String auth, String content){
        for(int i = 0; i < authors.size(); i++){
            if (auth.equalsIgnoreCase(authors.get(i).getName())) {
                String ID = LocalDate.now() + authors.get(i).getName();
                
                if (days.containsKey(ID)){
                    System.out.println("""
                        This day already exists for this author.
                        Please edit instead :)
                        """);
                }
                else{
                    Day newday = new Day(ID, content);
                    days.put(ID, newday); 
                    authors.get(i).addDay(newday); 
                    System.out.println();
                    System.out.println("day added successfully");
                }
                return;
            }
        }
        System.out.println("""
                    This author does not exist.
                    Please create new author or try again.
                    """); 
    }

    public void addContentToDay(String content, String auth){
        for(int i = 0; i < authors.size(); i++){
            if (auth.equalsIgnoreCase(authors.get(i).getName())) {
                authors.get(i).addEntry(content);
            }
        }
    } 

    public void printAllAuthors(){
        int exitcounter = 1;
        for(int i = 0; i<authors.size(); i++){
            int num = i+1;
            System.out.println("    "+num+". "+authors.get(i).getName());
            exitcounter++;
        }

        System.out.println("    "+exitcounter + ". Exit");
    }

    public void deleteAuthor(String auth) {
        
        File file = new File("src\\main\\resources\\entries\\"+auth+".csv");
        
        if (file.delete()) {
            System.out.println("File deleted successfully");
        } else {
            System.out.println("Failed to delete the file");
        }

        authors.removeIf(author -> author.getName().equals(auth));
    }

    public void reset() {
        // First clear the authors list to avoid potential ConcurrentModificationException
        List<Author> authorsToRemove = new ArrayList<>(authors);
        
        for (Author author : authorsToRemove) {
            String fileName = "src\\main\\resources\\entries\\" + author.getName() + ".csv";
            File file = new File(fileName);
            
            if (file.exists()) {
                if (file.delete()) {
                    System.out.println("File deleted successfully: " + author.getName());
                } else {
                    System.out.println("Failed to delete file: " + author.getName());
                    // Log the failure reason
                    if (file.exists()) {
                        System.out.println("File still exists - may be locked or permissions issue");
                    }
                }
            } else {
                System.out.println("File not found: " + fileName);
            }
        }
        
        // Clear the authors list
        authors.clear();
        System.out.println("All authors cleared from memory.");
    }

    public String getAuthor(int pos){
        return authors.get(pos).getName();
    }

    public void printDaysAuthor(String author){
        try(BufferedReader br = new BufferedReader(new FileReader("src\\main\\resources\\entries\\"+author+".csv"))){
            String line;
    
            while ((line = br.readLine()) != null) {
                
                String[] values = line.split(",");
                if (values.length > 0) {
                    String rowDate = values[0].trim();
                    System.out.println(rowDate);
                }
            }
        } catch(IOException e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }
    }

    public void printAll(){
        for(int i = 0; i< authors.size(); i++){
            System.out.println(authors.get(i).getName());
            printDaysAuthor(authors.get(i).getName());
        }
    }
}
