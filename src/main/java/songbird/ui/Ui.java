package songbird.ui;

import songbird.MainWindow;

/**
 * Handles the user interface of the Songbird chatbot, including input and output.
 *
 * @author Ashe Low
 * @version CS2103T AY24/25 Semester 2
 */
public class Ui {
    private static MainWindow mainWindow;
    private static String currentUserInput = null;
    private static String bufferedResponse = null;

    /**
     * Greets the user with the Songbird logo and a welcome message.
     * This method should be called once at the start of
     * the program.
     *
     * @return The greeting message.
     */
    public String getGreeting() {
        return "Welcome to Songbird(TM) AI by VariTech Heavy Industries, (C) 3025. All rights reserved.\n"
                + "We know exactly what you're thinking. Don't worry, we won't judge.";
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
        // if there's no MainWindow, buffer the messages until it's ready.
        if (mainWindow == null) {
            bufferedResponse = String.join("\n", messages);
            return;
        }

        // if there's stored user input, display it first.
        if (currentUserInput != null) {
            mainWindow.handleUserDialog(currentUserInput);
            currentUserInput = null; // clear stored input
        }

        // if there are buffered messages, display them first.
        if (bufferedResponse != null) {
            mainWindow.handleSongbirdResponse(bufferedResponse);
            bufferedResponse = null; // clear buffered messages
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
