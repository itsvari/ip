package songbird;

import songbird.ui.Ui;

/**
 * Represents the Songbird chatbot.
 */
public class Songbird {
    private final Ui ui;

    /**
     * Entry point of the Songbird chatbot.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        new Songbird();
    }

    /**
     * Constructor for the Songbird chatbot.
     */
    public Songbird() {
        this.ui = new Ui();
        init();
    }

    /**
     * Initializes the Songbird chatbot.
     * Songbird greets the user and reads the user's command.
     * This method should be called once at the start of the program.
     */
    private void init() {
        ui.greet();
        while (true) {
            String command = ui.readCommand();

            // check for "bye" command and handle it
            if (command.equalsIgnoreCase("bye")) {
                ui.respond("Goodbye.");
                break;
            }

            ui.respond(command);    // echo
        }
    }
}
