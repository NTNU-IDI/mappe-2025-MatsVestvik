package main.java.edu.ntnu.idi.idatt;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

public class AuthorRegister {
    private List<Author> authors;
    private HashMap<String, Day> days;

    AuthorRegister(){
        authors = new ArrayList<>();
        days = new HashMap<>();
    }

    public void addNewAuthor(String name){
        Author newAuthor = new Author(name);
        authors.add(newAuthor);
    }

    public void addDay(String auth){
        for(int i = 0; i < authors.size(); i++){
            if (auth.equalsIgnoreCase(authors.get(i).getName())) {
                String ID = authors.get(i).getDaysSize() + authors.get(i).getName();
                
                if (days.containsKey(ID)){
                    System.out.println("""
                        This day already exists for this author.
                        Please edit instead :)
                        """);
                }
                else{
                    Day newday = new Day(ID);
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
        for(Author author:authors){
            System.out.println(author.getName());
        }
    }

    public void printAll(){
        for(int i = 0; i< authors.size(); i++){
            authors.get(i).printAll();
        }
    }
}
