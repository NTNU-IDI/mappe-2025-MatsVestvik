package edu.ntnu.idi.idatt.BusinessLogic;

import java.util.Scanner;
import edu.ntnu.idi.idatt.util.ScannerManager;

public class ValidTitle {

    /**
     * Ensures title is not empty
     * @return title
     */
    public static String printValidTitle() {
        Scanner scanner = ScannerManager.getScanner();
        String title;
        do {
            System.out.println("    Please enter a title: ");
            System.out.print("    ");
            title = scanner.nextLine().trim();
        } while (title.isEmpty());
        return title;
    }
}