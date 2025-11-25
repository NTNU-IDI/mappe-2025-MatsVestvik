package edu.ntnu.idi.idatt.util;

public class TerminalUtils {

    /**
     * Clears the terminal/console for better display across platforms.
     */
    public static void clear() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            // Fallback: print many newlines if the system command failed
            System.out.println("\n".repeat(50));
        }
    }
}
