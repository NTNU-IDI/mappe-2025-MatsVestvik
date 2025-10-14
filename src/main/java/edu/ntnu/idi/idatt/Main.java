import java.util.Scanner;

public class Main{
    public static void main(String[] args){
        DiaryEntry diary = new DiaryEntry("Mats", "Dette er et innlegg",30,10,2025);

        diary.printDiary();

        Scanner scanner = new Scanner(System.in);

        System.out.println("""
                velg:
                1. skrive dagbok
                2. lese dagbok
                """);
        
        int choice = scanner.nextInt();

        switch (choice) {
            case 1 -> choice+=10;
            case 2 -> choice+=20;
            default -> scanner.close();
        }

        switch (choice) {
            case 11 -> System.out.println("""
                hvem er du:
                1. ny forfatter
                2. eksisterende forfatter
                """);
            case 22 -> System.out.println("""
                hvem er du:
                1. forfatter 1
                2. forfatter 2
                """);
            default -> scanner.close();
        }

        choice += 100 * scanner.nextInt();

        switch (choice) {
            case 111 -> ;
            case 211 -> ;
            case 112 -> ;
            case 212 -> ;
            
        }



        scanner.close();
    }
}