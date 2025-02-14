package songbird.ui;

import java.util.Scanner;

import songbird.MainWindow;

/**
 * Handles the user interface of the Songbird chatbot, including input and output.
 *
 * @author Ashe Low
 * @version CS2103T AY24/25 Semester 2
 */
public class Ui {
    private static final String ASCII_ART_LOGO = """
               _____                   __    _          __
              / ___/____  ____  ____ _/ /_  (_)________/ /
              \\__ \\/ __ \\/ __ \\/ __ `/ __ \\/ / ___/ __  /
             ___/ / /_/ / / / / /_/ / /_/ / / /  / /_/ /
            /____/\\____/_/ /_/\\__, /_.___/_/_/   \\__,_/
                             /____/""";
    private static MainWindow mainWindow;
    private static String currentUserInput = null;
    private final Scanner input = new Scanner(System.in);

    /**
     * Greets the user with the Songbird logo and a welcome message.
     * This method should be called once at the start of
     * the program.
     */
    public void greet() {
        respond(ASCII_ART_LOGO,
                "Songbird(TM) AI by VariTech Heavy Industries, (C) 3025. All rights reserved.",
                "We know exactly what you're thinking. Don't worry, we won't judge.");
    }

    /**
     * Sets the MainWindow for the Ui to respond to.
     *
     * @param window The MainWindow to respond to.
     */
    public static void setMainWindow(MainWindow window) {
        mainWindow = window;
    }

    /**
     * Store's the user's input for later use.
     *
     * @param input The user's input.
     */
    public static void setCurrentUserInput(String input) {
        currentUserInput = input;
    }

    /**
     * Responds to the user with the given messages.
     * <p>
     * Calls the MainWindow to display the messages. This is an MVP solution and should be refactored
     * with a more elegant solution in the future.
     *
     * @param messages The messages to respond with.
     */
    public static void respond(String... messages) {
        // if there's stored user input, display it first
        if (currentUserInput != null) {
            mainWindow.handleUserDialog(currentUserInput);
            currentUserInput = null; // clear stored input
        }

        // display response messages
        for (String message : messages) {
            mainWindow.handleSongbirdResponse(message);
        }
    }

    /**
     * Responds to the user with an error message.
     *
     * @param messages The error messages to respond with.
     */
    public static void error(String... messages) {
        for (String message : messages) {
            respond("ERROR :: " + message);
        }
    }
}
