package myproject.main;

import myproject.ui.UI;

/**
 * A static class to demonstrate the visualization aspect of
 * simulation.
 */
public class Main {
    private Main() {
    }

    public static void main(String[] args) {
        UI ui = null;

        if (args.length > 0) {
            if ("GUI".equalsIgnoreCase(args[0])) {
                ui = new myproject.ui.PopupUI();
            } else if ("TEXT".equalsIgnoreCase(args[0])) {
                ui = new myproject.ui.TextUI();
            } else {
                System.out.println("Argument must be GUI or TEXT");
                System.exit(1);
            }
        } else {
            ui = new myproject.ui.PopupUI();
        }
        Control control = new Control(ui);
        control.run();
        System.exit(0);
    }
}

