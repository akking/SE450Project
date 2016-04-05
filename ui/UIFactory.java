package myproject.ui;

public class UIFactory {
    static private UI UI = new PopupUI();

    private UIFactory() {
    }

    //static private UI UI = new TextUI();
    static public UI ui() {
        return UI;
    }
}
