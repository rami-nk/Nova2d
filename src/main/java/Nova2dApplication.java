import io.nova.Nova2dWindow;

public class Nova2dApplication {

    public static void main(String[] args) {
        var window = Nova2dWindow.getInstance();
        window.setWidth(600);
        window.setHeight(400);
        window.run();
    }
}