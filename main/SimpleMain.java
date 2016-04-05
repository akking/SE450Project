package myproject.main;

import myproject.model.MyModel;

/**
 * A static class to demonstrate the visualization aspect of
 * simulation.
 */
public class SimpleMain {
    private SimpleMain() {
    }

    public static void main(String[] args) {
        MyModel m = new MyModel();
//		m.run(500);
        m.dispose();
        System.exit(0);
    }
}

