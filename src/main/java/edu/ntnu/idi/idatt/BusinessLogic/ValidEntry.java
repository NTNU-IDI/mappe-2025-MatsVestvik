package edu.ntnu.idi.idatt.BusinessLogic;

import java.util.Scanner;
import edu.ntnu.idi.idatt.util.ScannerManager;

public class ValidEntry {

    /**
     * Ensures entry variable does not contain '|' and is formatted to 35 characters per line
     * @param entry
     * @return
     */

    public static String isValidEntry(String entry) {
        if (entry.contains("|")) {
            return "-1";
        }
        else {
            if (entry.length() <= 35) {
                return entry;
            }
            
            StringBuilder formatted = new StringBuilder();
            for (int i = 0; i < entry.length(); i++) {
                formatted.append(entry.charAt(i));
                if ((i + 1) % 35 == 0 && i != entry.length() - 1) {
                    formatted.append("\n");
                }
            }
            
            return formatted.toString();
        }
    }
}
