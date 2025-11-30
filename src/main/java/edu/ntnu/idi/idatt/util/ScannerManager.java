package edu.ntnu.idi.idatt.util;

import java.util.Scanner;

public class ScannerManager {
    private static Scanner scanner;
    
    private ScannerManager() {
        
    }
    
    public static Scanner getScanner() {
        if (scanner == null) {
            scanner = new Scanner(System.in);
        }
        return scanner;
    }
    
    public static void closeScanner() {
        if (scanner != null) {
            scanner.close();
        }
    }
}
