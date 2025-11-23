package edu.ntnu.idi.idatt.objects;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AuthorRegister {
    private List<Author> authors;

    public AuthorRegister(){
        authors = new ArrayList<>();
    }

    public List<Author> getAuthors(){ return authors;}

    public void addNewAuthor(String name, int pin) {
        for (Author author : authors) {
            if (author.getName().equals(name)) {
                System.out.println("This author already exists");
                return;
            }
        }
        
        Author newAuthor = new Author(name, pin);
        authors.add(newAuthor);
    }

    public boolean searchDays(String auth, LocalDate date){
        for(Author author:authors){
            if(author.getName().equals(auth)){
                return author.searchDays(date.toString());
            }
        }
        return false;
    }

    public void addDay(String author, String date, String content, int rating){
        for(Author auth: authors){
            if(author.equals(auth.getName())){
                String ID = date + auth.getName();
                Day newDay = new Day(ID, date, content, rating);
                auth.addDay(newDay);
                return;
            }
        }
        System.out.println("""
                    This author does not exist.
                    Please create new author or try again.
                    """); 
    }
    

    public void addDayToday(String author, String content, int rating){
        for(Author auth: authors){
            if(author.equals(auth.getName())){
                Day newDay = new Day(author, content, rating);
                auth.addDay(newDay);
                return;
            }
        }
        System.out.println("""
                    This author does not exist.
                    Please create new author or try again.
                    """); 
    } 

    public void printAllAuthors(){
        for(int i = 0; i<authors.size(); i++){
            int num = i+1;
            System.out.println("    "+num+". "+authors.get(i).getName());
        }
    }

    public void deleteAuthor(String auth) {
        authors.removeIf(author -> author.getName().equals(auth));
    }

    public String getAuthorName(int pos){
        return authors.get(pos).getName();
    }

    public Author getAuthorByName(String name){
        for (Author author : authors){
            if (name.equals(author.getName())){
                return author;
            }
        }
        return null;
    }

    public int getAuthorPos(String name){
        int pos = 0;
        for (Author author : authors){
            pos++;
            if (name.equals(author.getName())){
                return pos;
            }
        }
        return pos + 1;
    }

    public void editDay(String date, String entry, String name, int rating){
        for(Author author: authors){
            if(name.equals(author.getName())){
                Day day = author.getDayByDate(date);
                if (day != null) {
                    day.setEntry(entry);
                } else {
                    // Create new day if it doesn't exist
                    String ID = date + author.getName();
                    Day newDay = new Day(ID, date, entry, rating);
                    author.addDay(newDay);
                }
                return;
            }
        }
        System.out.println("Author not found: " + name);
    }

    public void printDaysAuthor(Author author){
        for (int i = 0; i < author.getDaysSize(); i++){
            System.out.println(author.getListDays().get(i).toString());
        }
    }

    
}
