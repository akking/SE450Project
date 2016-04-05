package myproject.main;

import myproject.model.MyModel;
import myproject.ui.*;

import javax.swing.*;

/**
 * Created by DLI on 11/11/15.
 */
class Control {
    public static final int SIMPLE = 0;
    public static final int ALTERNATE = 1;
    private static final int EXITED = 0;
    private static final int EXIT = 1;
    private static final int START = 2;
    private static final int NUMSTATES = 10;
    private static final int CONFIG = 3;
    MyModel model = new MyModel();
    private UIMenu[] menus;
    private int state;
    private UIForm getVideoForm;
    private UIFormTest numberTest;
    private UIFormTest stringTest;
    private UI ui;

    Control(UI ui) {
        this.ui = ui;

        menus = new UIMenu[NUMSTATES];
        state = START;
        addSTART(START);
        addEXIT(EXIT);
        addConfig(CONFIG);
    }

    private void addSTART(int stateNum) {
        UIMenuBuilder m = new UIMenuBuilder();

        m.add("Default", new UIMenuAction() {
            @Override
            public void run() {
                ui.displayError("what?");
            }
        });

        m.add("Run simulation", new UIMenuAction() {
            @Override
            public void run() {
                MyModel myModel = new MyModel();
                myModel.run();
                myModel.dispose();
            }
        });

        m.add("Change simulation parameters", new UIMenuAction() {
            @Override
            public void run() {
                state = CONFIG;
            }
        });

        m.add("EXIT", new UIMenuAction() {
            @Override
            public void run() {
                state = EXIT;
            }
        });
        menus[stateNum] = m.toUIMenu("Main Menu");
    }

    private void addEXIT(int stateNum) {
        UIMenuBuilder m = new UIMenuBuilder();

        m.add("Default", () -> {
        });
        m.add("Yes", () -> state = EXITED);
        m.add("No", () -> state = START);

        menus[stateNum] = m.toUIMenu("Are you sure you want to exit?");
    }

    private void addConfig(int stateNum) {
        UIMenuBuilder m = new UIMenuBuilder();

        m.add("Default", new UIMenuAction() {
            @Override
            public void run() {
                ui.displayError("What?");
            }
        });

        m.add("Show current values", () -> ui.displayError("doh!"));
        m.add("Simulation time step", () -> {
/*            String[] result1 = ui.processForm(getVideoForm);

            UIFormBuilder f = new UIFormBuilder();
            f.add("Number of copies to add/remove", numberTest);
            String[] result2 = ui.processForm(f.toUIForm(""));*/
            String timeStepString = JOptionPane.showInputDialog("Enter the time step: ");
            double timeStep = 0;
            timeStep = new Double(timeStepString);
            model.setSimulationTimeStep(timeStep);
        });
        m.add("Simulation run time", () -> {
            String timeString = JOptionPane.showInputDialog("Enter the simulation time: ");
            int simulationTime = new Integer(timeString);
            int runtime = simulationTime;
            model.setSimulationRunTime(runtime);
        });
        m.add("Grid size", new UIMenuAction() {
            @Override
            public void run() {
                String horizontalRoadNumString = JOptionPane.showInputDialog("Enter the number of horizontal road: ");
                int horizontalRoadNumber = new Integer(horizontalRoadNumString);
                String verticalRoadNumberString = JOptionPane.showInputDialog("Enter the number of vertical road: ");
                int verticalRoadNum = new Integer(verticalRoadNumberString);
                int horiRoadNum = horizontalRoadNumber;
                int vertiRoadNum = verticalRoadNum;
                model.setGridSize(horiRoadNum, vertiRoadNum);
            }
        });
        m.add("Traffic pattern", new UIMenuAction() {
            @Override
            public void run() {
                int trafficPattern;
                String pattern = JOptionPane.showInputDialog("Enter the traffic pattern(simple or alternate): ");
                if ("simple".equals(pattern)) {
                    model.setTrafficPattern(SIMPLE);
                } else if ("alternate".equals(pattern)) {
                    model.setTrafficPattern(ALTERNATE);
                } else {
                    ui.displayError("Input is wrong, please input simple or alternate");
                    state = EXIT;
                }
            }
        });
        m.add("Car entry rate", () -> {
            String entry = JOptionPane.showInputDialog("Enter entry rate: ");
            int entryRate = new Integer(entry);
            model.setCarEntryRate(entryRate);
        });
        m.add("Road segment length", () -> {
            String length = JOptionPane.showInputDialog("Enter road segment lenth: ");
            double roadLength = new Double(length);
            model.setRoadSegmentLength(roadLength);
        });

        m.add("Car length", () -> {
            String length = JOptionPane.showInputDialog("Enter car lenth: ");
            int carLength = new Integer(length);
            model.setCarLength(carLength);
        });

        m.add("Car maximum velocity", () -> {
            String v = JOptionPane.showInputDialog("Enter maximum car velocity: ");
            double maxVelocity = new Double(v);
            model.setCarMaxVelocity(maxVelocity);
        });


        m.add("Car stop distance", () -> {
            String distance = JOptionPane.showInputDialog("Enter car stop distance: ");
            double stopDistance = new Double(distance);
            model.setCarStopDistance(stopDistance);
        });

        m.add("Car brake distance", () -> {
            String distance = JOptionPane.showInputDialog("Enter car brake distance: ");
            double brakDistance = new Double(distance);
            model.setCarBrakeDistance(brakDistance);
        });

        m.add("Traffic light green time", () -> {
            String v = JOptionPane.showInputDialog("Enter maximum car velocity: ");
            int setValue = new Integer(v);
            model.setTrafficLightGreenTime(setValue);
        });

        m.add("Traffic light yellow time", () -> {
            String v = JOptionPane.showInputDialog("Enter maximum car velocity: ");
            int time = new Integer(v);
            model.setTrafficLightYellowTime(time);
        });
        m.add("Reset all the parameters", new UIMenuAction() {
            @Override
            public void run() {
                model.resetParameters();
            }
        });

        m.add("Return to the main menu", () -> {
            state = START;
        });

        menus[stateNum] = m.toUIMenu("Config");
    }

    void run() {
        try {
            while (state != EXITED) {
                ui.processMenu(menus[state]);
            }
        } catch (UIError e) {
            ui.displayError("UI closed");
        }
    }
}
