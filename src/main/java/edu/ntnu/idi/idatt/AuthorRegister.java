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
                String ID =  authors.get(i).getDaysSize() + authors.get(i).getName();
                if (days.containsKey(ID)){
                    System.out.println("""
                        This day already exists for this author.
                        Please edit instead :)
                        """);
                }
                else{
                    authors.get(i).addDay();
                    System.out.println(authors.get(i).getDaysSize());
                    System.out.println("day added succesfully");
                }
                return;
            }

        }
       System.out.println("""
                    This author does not exist.
                    Please create new author or try again.
                    """); 
    }

    public void addContentToDay(String auth, String entry){
        for(int i = 0; i < authors.size(); i++){// går igjennom alle authors
            if (auth.equalsIgnoreCase(authors.get(i).getName())) {//author exists
                String ID =  authors.get(i).getDaysSize() + authors.get(i).getName();//creates the day ID
                if (days.containsKey(ID)){ //day with ID
                    authors.get(i).addEntry(entry);
                    System.out.println("entry added succesfully");
                }
                else{
                    System.out.println("""
                            This day does not exist for this author.
                            Please create new day.
                            """);
                }
            }
        }
    } 

    public void printAllAuthors(){
        for(Author author:authors){
            System.out.println(author.getName());
        }
    }

    public void printAll(){
        System.out.println("Printing....");
        for(int i = 0; i< authors.size(); i++){
            System.out.println("Printing....");
            authors.get(i).printAll();
        }
    }
}
