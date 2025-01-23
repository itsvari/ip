package songbird.ui;

import java.util.Scanner;

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
    private final Scanner input = new Scanner(System.in);

    /**
     * Greets the user with the Songbird logo and a welcome message.
     * This method should be called once at the start of
     * the program.
     */
    public void greet() {
        System.out.println(ASCII_ART_LOGO);
        System.out.println("Songbird(TM) AI by VariTech Heavy Industries, (C) 3025. All rights reserved.");
        System.out.println("We know exactly what you're thinking. Don't worry, we won't judge.");
        System.out.println("--------------------");
        respond("How can I help?");
    }

    /**
     * Reads the user's command from the console input.
     * <p>
     * Prints "U> " to prompt the user for input, then reads the user's input as a String.
     *
     * @return The user's command.
     */
    public String readCommand() {
        System.out.print("U> ");
        return input.nextLine();
    }

    /**
     * Responds to the user with the given messages.
     * <p>
     * Each message is printed on a new line and prefixed with "S> ".
     * Takes in a variable number of messages to respond with, which are printed in the order they are provided.
     *
     * @param messages The messages to respond with.
     */
    public static void respond(String... messages) {
        for (String message : messages) {
            System.out.println("S> " + message);
        }
    }

    /**
     * Responds to the user with an error message.
     *
     * @param messages The error messages to respond with.
     */
    public static void error(String... messages) {
        for (String message : messages) {
            respond("ERROR: " + message);
        }
    }
}
